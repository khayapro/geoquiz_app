package geoquiz.com.geoquizapp;

/**
 * Created by khayapro on 2016/04/21.
 */
public class TrueFalse {

    private int mQuestion;
    private boolean mTrueQuestion;

    public TrueFalse(final int question, final boolean answer){
        this.mQuestion = question;
        this.mTrueQuestion = answer;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
