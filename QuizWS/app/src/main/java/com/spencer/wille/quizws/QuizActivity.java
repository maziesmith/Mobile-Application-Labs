package com.spencer.wille.quizws;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity{
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mHintButton;
    private TextView mQuestionTextView;
    private TextView mLostPoints;
    private TextView mScoreView;
    private int mCounter = 0;
    private double mScore = 0;
    private double oldScore1;
    private String sharedKey = "score";
    DatabaseReference myRef;
    FirebaseDatabase database;
    ImageView img;
    public static final String PREFS_NAME = "MyPrefsFile";

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.q1, true, false, R.mipmap.tagalong),
            new Question(R.string.q2, false, false, R.mipmap.computer),
            new Question(R.string.q3, true, false, R.mipmap.bee),
            new Question(R.string.q4, false, false, R.mipmap.addition),
            new Question(R.string.q5, false, false, R.mipmap.mihir),
            new Question(R.string.q6, true, false, R.mipmap.laptop),
            new Question(R.string.q7, false, false, R.mipmap.office),
            new Question(R.string.q8, false, false, R.mipmap.calc),
            new Question(R.string.q9, true, false, R.mipmap.potter),
            new Question(R.string.q10, true, false, R.mipmap.emoji)
    };
    private void updateQuestion(){
        int question = mQuestionBank[mCounter].getTextResId();
        mQuestionTextView.setText(question);
        mLostPoints = (TextView)findViewById(R.id.lost_points);
        mLostPoints.setText("");

        mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);
        mHintButton=(Button)findViewById(R.id.hint_button);
        mTrueButton.setEnabled(!mQuestionBank[mCounter].isCompleted());
        mFalseButton.setEnabled(!mQuestionBank[mCounter].isCompleted());
        mHintButton.setEnabled(!mQuestionBank[mCounter].isCompleted());

        img = (ImageView) findViewById(R.id.picture_view);
        int imageInt = mQuestionBank[mCounter].getDraw();
        img.setImageResource(imageInt);



    }
    private void openScoreActivity(){

        double highScore1 = oldScore1;
        if(oldScore1 < mScore){
            myRef.setValue(mScore);
            highScore1 = mScore;

        }

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        double oldScore2 = (double)settings.getFloat(sharedKey, 0);
        SharedPreferences.Editor editor = settings.edit();
        double highScore2 = oldScore2;
        if(mScore > oldScore2){
            editor.putFloat(sharedKey, (float)mScore);
            highScore2 = mScore;
        }
        editor.commit();

        Intent intent2 = ScoreActivity.newIntent(QuizActivity.this, mScore, highScore1, highScore2);
        startActivity(intent2);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCounter].isAnswer();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mScoreView = (TextView)findViewById(R.id.score_view);
            mScore++;
            mScoreView.setText("Score: " + mScore + "/10");
            if(oldScore1 < mScore){
                myRef.setValue(mScore);
            }

        }else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);
        mHintButton=(Button)findViewById(R.id.hint_button);
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        mHintButton.setEnabled(false);
        mQuestionBank[mCounter].setCompleted(true);
    }
    private void createNotification(int mId, double scoreOutput){

        Intent intent = new Intent(this, QuizActivity.class);
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);
// Now we can attach the pendingIntent to a new notification using setContentIntent
        Notification noti = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notify)
                .setContentTitle("Will Spencer's Quiz")
                .setContentText("New Firebase High Score: " + scoreOutput)
                .setContentIntent(pIntent)
                .setAutoCancel(true) // Hides the notification after its been selected
                .build();
// Get the notification manager system service
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, noti);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //img = (ImageView) findViewById(R.id.picture_view);
        //img.setImageResource(imageInt);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("high_score");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                oldScore1 = dataSnapshot.getValue(Double.class);

                createNotification(1, oldScore1);


                /*Log.d(TAG, "Value is: " + value);*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                /*Log.w(TAG, "Failed to read value.", error.toException());*/
            }
        });

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        /*int question = mQuestionBank[mCounter].getTextResId();
        mQuestionTextView.setText(question);*/

        mTrueButton=(Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(QuizActivity.this,R.string.incorrect_toast, Toast.LENGTH_SHORT).show();*/
                checkAnswer(true);
            }
        });

        mFalseButton=(Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(QuizActivity.this,R.string.correct_toast, Toast.LENGTH_SHORT).show();*/
                checkAnswer(false);
            }
        });

        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrevButton.setEnabled(true);
                if (mCounter == 9) {
                    openScoreActivity();
                }
                else{
                    mCounter=(mCounter+1)% mQuestionBank.length;
                    /*int question = mQuestionBank[mCounter].getTextResId();
                    mQuestionTextView.setText(question);*/
                    updateQuestion();
                }


            }
        });
        mPrevButton=(Button)findViewById(R.id.prev_button);
        if(mCounter == 0){
            mPrevButton.setEnabled(false);
        }

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCounter > 0){
                    mCounter=(mCounter-1)% mQuestionBank.length;
                    if(mCounter == 0){
                        mPrevButton.setEnabled(false);
                    }
                }
                else{
                    mCounter = 9;
                }
                updateQuestion();
            }
        });
        mHintButton=(Button)findViewById(R.id.hint_button);
        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answer = mQuestionBank[mCounter].isAnswer();
                Intent i = HintActivity.newIntent(QuizActivity.this, answer);
                /*startActivity(i);*/
                startActivityForResult(i, 1);
            }
        });

        updateQuestion();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        mLostPoints = (TextView)findViewById(R.id.lost_points);
        mScoreView = (TextView)findViewById(R.id.score_view);
        if (requestCode == 1) {
            // Make sure the request was successful
            String clickedHint =data.getStringExtra("clickedBool");
            if (clickedHint.equals("0")) {
                mLostPoints.setText("Did not Lose Points");
            }
            else{
                mScore = mScore - .5;
                mLostPoints.setText("Lost Half a Point");
                mScoreView.setText("Score: " + mScore + "/10");
            }
        }
    }
}
