package com.milkriver.helloworld;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import winterwell.jtwitter.Twitter;

public class StatusActivity1 extends Activity implements OnClickListener {
    private static final String TAG = "StatusActivity";
    EditText editText;
    Button updateButton;
    Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //findViews
        editText = (EditText) findViewById(R.id.editText);
        updateButton = (Button) findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(this);

        twitter = new Twitter("student", "password");
        twitter.setAPIRootUrl("http://yamba.marakana.com/api");
    }

    @Override
    public void onClick(View view) {
        twitter.setStatus(editText.getText().toString());
        Log.d(TAG, "onClicked");
    }
}
