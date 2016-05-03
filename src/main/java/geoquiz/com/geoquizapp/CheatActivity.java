package geoquiz.com.geoquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_SHOWN = "geoquiz.com.geoquizapp.answer_shown";
    private Button mShowAnswerButton;
    private boolean mAnswerTrue;
    private TextView mAnswerTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //getting passed in answer from intent
        mAnswerTrue = getIntent().getBooleanExtra(QuizActivity.EXTRA_ANSWER, false);

        mAnswerTextView = ((TextView) findViewById(R.id.answer_text_view));

        mShowAnswerButton = ((Button) findViewById(R.id.show_answer_button));
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerTrue){
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    /**
     * Setting the data and result to be shown to parent.
     * @param isAnswerShown
     */
    private void setAnswerShownResult(final boolean isAnswerShown){
        final Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
