package com.example.remarks.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remarks.R;
import com.example.remarks.models.Remark;
import com.example.remarks.models.RemarkFactory;
import com.example.remarks.net.Rest;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AddRemarkActivity extends AppCompatActivity {
    private static final String TAG = "AddRemarkActivity";

    @BindView(R.id.add_remark_text)
    TextView text;

    @BindView(R.id.add_remark_choices)
    Spinner choices;

    @OnClick(R.id.add_remark_button_send)
    void sendComment(View view) {
        String commentText = text.getText().toString();

        if (TextUtils.isEmpty(commentText)) {
            Log.e(TAG, "Empty text");
            return;
        }

        new PostRemarkTask().execute(commentText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_add);
        ButterKnife.bind(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.remarks_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        choices.setAdapter(adapter);
    }

    // Network calls cannot be made on main thread, use a separate thread
    private class PostRemarkTask extends AsyncTask<String, Void, Boolean> {
        String selectedType;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            selectedType = choices.getSelectedItem().toString();
        }

        @Override
        protected Boolean doInBackground(String... messages) {
            // TODO:
            // before starting a network request we should persist the remark,
            // then if network request fails it's possible to retry later

            boolean returnValue = false;
            String message = messages[0];

            Rest restHttpClient = Rest.getInstance();
            Call<ResponseBody> call;


            Remark remark = RemarkFactory.getRemark(selectedType.toLowerCase(),
                    message, System.currentTimeMillis());

            call = restHttpClient.sendRemark(remark);

            if (null == call) {
                return false;
            }

            try {
                Response<ResponseBody> response = call.execute();
                if (null != response && response.isSuccessful()) {
                    // TODO: remove the remark from the local storage
                    returnValue = true;
                }
            } catch (IOException e) {
                // TODO: properly handle exception: log if on debug build, and send exception to a crash reporting service
                e.printStackTrace();
            }

            return returnValue;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                Toast.makeText(AddRemarkActivity.this, R.string.add_remark_activity_toast_message_success, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddRemarkActivity.this, R.string.add_remark_activity_toast_message_error, Toast.LENGTH_LONG).show();
            }
        }
    }
}
