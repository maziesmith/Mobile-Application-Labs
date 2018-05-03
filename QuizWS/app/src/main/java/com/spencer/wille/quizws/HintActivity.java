package com.spencer.wille.quizws;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;

public class HintActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.spencer.wille.quizws.answer_is_true";
    private boolean mAnswerIsTrue;
    private boolean mClicked = false;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, HintActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        if(mClicked == true){
            intent.putExtra("clickedBool", "1");
        }
        else{
            intent.putExtra("clickedBool", "0");
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);

        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAnswerIsTrue == true){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                int text =  R.string.hint_toast;
                Toast.makeText(HintActivity.this, text, Toast.LENGTH_SHORT).show();
                mClicked = true;


            }
        });
    }

}
