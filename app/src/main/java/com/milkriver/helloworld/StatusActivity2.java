package com.milkriver.helloworld;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import winterwell.jtwitter.Status;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

public class StatusActivity2 extends AppCompatActivity implements OnClickListener, TextWatcher, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "StatusActivity";
    EditText editText;
    Button updateButton;
    Twitter twitter;
    TextView textCount;
    SharedPreferences prefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemPrefs:
                startActivity(new Intent(this, PrefsActivity.class));
                break;
        }
        return true;
    }

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

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        twitter = null;
    }

    class PostToTwitter extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... statuses) {
            try{
                winterwell.jtwitter.Status status = getTwitter().updateStatus(statuses[0]);
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

    private Twitter getTwitter() {
        if (null == twitter) {
            String username, password, apiRoot;
            username = prefs.getString("username", "");
            password = prefs.getString("password", "");
            apiRoot = prefs.getString("apiRoot", "http://identi.ca/api");

            twitter = new Twitter(username, password);
            twitter.setAPIRootUrl(apiRoot);
        }
        return twitter;
    }
}

