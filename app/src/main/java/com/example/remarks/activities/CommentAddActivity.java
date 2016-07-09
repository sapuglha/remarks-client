package com.example.remarks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remarks.R;
import com.example.remarks.models.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentAddActivity extends AppCompatActivity {
    private static final String TAG = " CommentAddActivity";

    @BindView(R.id.comment_add_text)
    TextView comment;

    @OnClick(R.id.comment_add_button_send)
    void sendComment(View view) {
        String commentText = comment.getText().toString();

        if (TextUtils.isEmpty(commentText)) {
            Log.e(TAG, "Empty comment");
            return;
        }

        Comment newComment = Comment.create(commentText, System.currentTimeMillis());

        // TODO: send comment
        Log.d(TAG, "New comment: " + newComment.toString());

        Toast.makeText(this, R.string.comment_add_activity_toast_message_sent, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_add);
        ButterKnife.bind(this);
    }
}
