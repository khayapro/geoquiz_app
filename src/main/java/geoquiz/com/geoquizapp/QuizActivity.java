package geoquiz.com.geoquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {


    private static final String TAG = "QuizActivity";
    private static final String CHEATED_QUESTIONS = "cheated_questions";
    private static final String KEY_INDEX = "index";
    public static final String EXTRA_ANSWER = "geoquiz.com.geoquizapp.answer";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private static TrueFalse [] mQuestions;
    private int mCurrentIndex = 0;
    private boolean mIsACheater;
    private static ArrayList<Integer> mCheatedQuestions;

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

        mCheatedQuestions = new ArrayList();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() method called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsACheater = savedInstanceState.getBoolean(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        }

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
                mIsACheater =  false;
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

        mCheatButton = ((Button) findViewById(R.id.cheat_button));
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting a new activity i.e CheatActivity
                final Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                //explicitly passing answer to cheat activity
                intent.putExtra(EXTRA_ANSWER, mQuestions[mCurrentIndex].isTrueQuestion());
                //expecting a feedback/result
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null)
            return;
        mIsACheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        if(mIsACheater){
            //saving the question user cheated
            mCheatedQuestions.add(mQuestions[mCurrentIndex].getQuestion());
        }
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
        saveInstance.putBoolean(CheatActivity.EXTRA_ANSWER_SHOWN, mIsACheater);
        saveInstance.putIntegerArrayList(CHEATED_QUESTIONS, mCheatedQuestions);
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

        for(final int question : mCheatedQuestions){
            if(question == mQuestions[mCurrentIndex].getQuestion()){
                mIsACheater = true;
            }
        }

        int messageId;
        if(mIsACheater){
            messageId = R.string.cheat_message;
        } else {
            if (answerIsTrue == userPressedTrue) {
                messageId = R.string.correct;
            } else {
                messageId = R.string.incorrect;
            }
        }
        Toast.makeText(QuizActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }



}
