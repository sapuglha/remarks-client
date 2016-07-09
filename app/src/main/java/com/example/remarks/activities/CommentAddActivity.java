package com.example.remarks.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remarks.R;
import com.example.remarks.models.Comment;
import com.example.remarks.net.Rest;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CommentAddActivity extends AppCompatActivity {
    private static final String TAG = "CommentAddActivity";

    @BindView(R.id.comment_add_text)
    TextView comment;

    @OnClick(R.id.comment_add_button_send)
    void sendComment(View view) {
        String commentText = comment.getText().toString();

        if (TextUtils.isEmpty(commentText)) {
            Log.e(TAG, "Empty comment");
            return;
        }

        Comment newComment = Comment.builder()
                .comment(commentText)
                .timestamp(System.currentTimeMillis())
                .build();

        new PostCommentTask().execute(newComment);

        Toast.makeText(this, R.string.comment_add_activity_toast_message_sent, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_add);
        ButterKnife.bind(this);
    }

    // Network calls cannot be made on main thread, use a separate thread
    private class PostCommentTask extends AsyncTask<Comment, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Comment... comments) {
            // TODO:
            // before starting a network request we should persist the comment,
            // then if network request fails it's possible to retry later

            boolean returnValue = false;

            Rest restHttpClient = new Rest();
            for (Comment comment : comments) {
                Call<ResponseBody> call = restHttpClient.sendComment(comment);
                try {
                    Response<ResponseBody> response = call.execute();
                    if (null == response) {
                        Log.e(TAG, "Unknown error sending comment");
                    } else if (!response.isSuccessful()) {
                        Log.e(TAG, String.format("Response http status: %s, body: %s", response.code(), response.body().string()));
                    } else {
                        // TODO: remove the comment from the local storage
                        Log.d(TAG, String.format("Response http status: %s, body: %s", response.code(), response.body().string()));
                        returnValue = true;
                    }
                } catch (IOException e) {
                    // TODO: properly handle exception: log if on debug build, and sendComment to a crash reporting service
                    e.printStackTrace();
                }
            }

            return returnValue;
        }
    }
}
