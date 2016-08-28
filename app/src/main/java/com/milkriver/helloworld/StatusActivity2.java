package com.milkriver.helloworld;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import winterwell.jtwitter.Status;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

public class StatusActivity2 extends Activity implements OnClickListener, TextWatcher {
    private static final String TAG = "StatusActivity";
    EditText editText;
    Button updateButton;
    Twitter twitter;
    TextView textCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status2);

        //findViews
        editText = (EditText) findViewById(R.id.editText);
        updateButton = (Button) findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(this);

        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GREEN);

        editText.addTextChangedListener(this);

        twitter = new Twitter("tangxukai@gmail.com", "txk901225");
        twitter.setAPIRootUrl("http://yamba.marakana.com/api");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable statusText) {
        int count = 140 - statusText.length();
        textCount.setText(Integer.toString(count));
        textCount.setTextColor(Color.GREEN);
        if (count < 10) {
            textCount.setTextColor(Color.YELLOW);
        }
        if (count < 0) {
            textCount.setTextColor(Color.RED);
        }
    }

    class PostToTwitter extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... statuses) {
            try{
                winterwell.jtwitter.Status status = twitter.updateStatus(statuses[0]);
                return status.text;
            } catch(TwitterException e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
                return "Failed to Post";
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(StatusActivity2.this, result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        String status = editText.getText().toString();
        new PostToTwitter().execute(status);
        Log.d(TAG, "onClicked");
    }
}

