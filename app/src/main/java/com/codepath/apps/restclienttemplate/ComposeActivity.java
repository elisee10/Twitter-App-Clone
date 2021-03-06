package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";
    public static final int MAX_TWEET_LENGTH = 140;
    public static final int TWEET_COUNT = 280;

    TextView tweetCount;
    EditText etCompose;
    Button btnTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        etCompose    = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        tweetCount = findViewById(R.id.tweetcounting);


//Not used
//        tweetCount.addTextChangedListener(new TextWatcher() {
//            @Override
//            // Fires right as the text is being changed (even supplies the range of text)
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //tweetCount.setText(String.valueOf(start + 1));
//                //if (start > TWEET_COUNT){
//                   // btnTweet.setEnabled(false);
//                }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                btnTweet.setEnabled(true);
//                // Fires right before text is changing
//            }
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void afterTextChanged(Editable s) {
//              //  tweetCount.setText(s.toString().length() + "/280");
//                tweetCount.setText(TWEET_COUNT - s.toString().length() + "");
//                if (TWEET_COUNT - s.toString().length() < 0) {
//                    btnTweet.setEnabled(false);
//                }
//            }
//        });




        //set click listener on the button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Sorry your tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tweetContent.length()> MAX_TWEET_LENGTH ){
                    Toast.makeText(ComposeActivity.this, "Keep tweet between 140 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_SHORT).show();
                //Make an API call to twitter to publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "ON  Success to publish the tweet ");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published tweet successfully, and tweet says:" + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            //set result code and create bundle data for responses
                            setResult(RESULT_OK, intent);
                            //closes thE activity
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "ON Failure to publish the tweet ", throwable);

                    }
                });



            }
        });


    }
}