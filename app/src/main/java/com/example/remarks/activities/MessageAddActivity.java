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

public class MessageAddActivity extends AppCompatActivity {
    private static final String TAG = " MessageAddActivity";

    @BindView(R.id.message_add_text)
    TextView message;

    @OnClick(R.id.message_add_button_send)
    void sendMessage(View view) {
        String messageText = message.getText().toString();

        if (TextUtils.isEmpty(messageText)) {
            Log.e(TAG, "Empty message");
            return;
        }

        Comment newMessage = Comment.create(messageText, System.currentTimeMillis());

        // TODO: send message
        Log.d(TAG, "New message: " + newMessage.toString());

        Toast.makeText(this, R.string.message_add_activity_toast_message_sent, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_add);
        ButterKnife.bind(this);
    }
}
