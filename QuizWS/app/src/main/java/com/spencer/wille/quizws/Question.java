package com.spencer.wille.quizws;

/**
 * Created by wille on 2/15/2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswer;
    private boolean mCompleted;
    private int mDraw;

    public Question(int textResId, boolean answer, boolean completed, int drawStr){
        mTextResId = textResId;
        mAnswer = answer;
        mCompleted = completed;
        mDraw = drawStr;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public int getDraw() {
        return mDraw;
    }

    public void setDraw(int draw) {
        mDraw = draw;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
