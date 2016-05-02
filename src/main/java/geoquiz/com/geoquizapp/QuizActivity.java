package geoquiz.com.geoquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    private static TrueFalse [] mQuestions;
    private int mCurrentIndex = 0;

    /**
     * Setting up the question to display
     */
    static {
        mQuestions = new TrueFalse[] {
            new TrueFalse(R.string.question_africa, true),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.question_mideast, true),
            new TrueFalse(R.string.question_oceans, true)
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() method called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        mQuestionTextView = ((TextView) findViewById(R.id.question_text));
        updateQuestion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });

        mTrueButton = ((Button) findViewById(R.id.true_button));
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(true);
            }
        });


        mFalseButton = ((Button) findViewById(R.id.false_button));
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = ((Button) findViewById(R.id.next_button));
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });

        mPrevButton = ((Button) findViewById(R.id.prev_button));
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex--;
                if(mCurrentIndex < 0)
                    mCurrentIndex = mQuestions.length - 1;
                updateQuestion();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() method called");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstance){
        super.onSaveInstanceState(saveInstance);
        Log.d(TAG, "Saving activity state");
        saveInstance.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() method called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() method called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() method called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroyed() method called");
    }

    /**
     * Updating the init question or the next.
     */
    private void updateQuestion() {
        mQuestionTextView.setText(mQuestions[mCurrentIndex].getQuestion());
    }

    /**
     * Checking the pressed answer is correct or not.
     * @param userPressedTrue - arg
     */
    private void checkAnswer(final boolean userPressedTrue){
        final boolean answerIsTrue = mQuestions[mCurrentIndex].isTrueQuestion();

        int messageId;
        if(answerIsTrue == userPressedTrue){
            messageId = R.string.correct;
        } else {
            messageId = R.string.incorrect;
        }
        Toast.makeText(QuizActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }



}
