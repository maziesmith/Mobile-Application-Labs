package com.spencer.wille.quizws;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private static final String EXTRA_SCORE = "com.spencer.wille.quizws.score";
    private static final String EXTRA_HIGH_SCORE = "com.spencer.wille.quizws.high_score";
    private static final String EXTRA_SHARED_SCORE = "com.spencer.wille.quizws.shared_score";
    private TextView mScoreView;
    private TextView mHighScoreView;
    private TextView mSharedScoreView;
    private double mScore;
    private double mHighScore;
    private double mSharedScore;

    public static Intent newIntent(Context packageContext, double mScore, double mHighScore, double mSharedScore){
        Intent i = new Intent(packageContext, ScoreActivity.class);
        i.putExtra(EXTRA_SCORE, mScore);
        i.putExtra(EXTRA_HIGH_SCORE, mHighScore);
        i.putExtra(EXTRA_SHARED_SCORE, mSharedScore);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mScore = getIntent().getDoubleExtra(EXTRA_SCORE, 0);
        mHighScore = getIntent().getDoubleExtra(EXTRA_HIGH_SCORE, 0);
        mSharedScore = getIntent().getDoubleExtra(EXTRA_SHARED_SCORE, 0);

        mScoreView = (TextView)findViewById(R.id.score_text_view);
        mHighScoreView = (TextView)findViewById(R.id.high_score_text_view);
        mSharedScoreView = (TextView)findViewById(R.id.shared_score_text_view);
        mScoreView.setText("Final Score: " + mScore + "/10");
        mHighScoreView.setText("HighScore (Firebase): " + mHighScore + "/10");
        mSharedScoreView.setText("HighScore (Shared Preferences): " + mSharedScore + "/10");

    }
}
