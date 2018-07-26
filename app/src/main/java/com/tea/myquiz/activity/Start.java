package com.tea.myquiz.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tea.myquiz.QuizDbHelper;
import com.tea.myquiz.R;
import com.tea.myquiz.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Start extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 20000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFS = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView tvScore, tvQuestion, tv_total_question, tvCountDown;
    private RadioGroup rdGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnConfirm;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tvScore = findViewById(R.id.tvScore);
        tv_total_question = findViewById(R.id.tv_total_question);
        tvCountDown = findViewById(R.id.tvCountDown);
        tvQuestion = findViewById(R.id.tvQuestion);
        rdGroup = findViewById(R.id.rdGroup);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        btnConfirm = findViewById(R.id.btn_confirm);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = tvCountDown.getTextColors();

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
            questionList = dbHelper.getAllQuestion();
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFS);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startCountDown();
            } else {
                updateCountDownText();
                showSolution();
            }
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(Start.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    showNextQuestion();
                }
            }
        });

    }

    private void checkAnswer() {
        answered = true;

        //
        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rdGroup.getCheckedRadioButtonId());
        int answerNr = rdGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            tvScore.setText("Score: " + score);
        }
        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                tvQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                tvQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                tvQuestion.setText("Answer 3 is correct");
                break;

        }

        if (questionCounter < questionCountTotal) {
            btnConfirm.setText("Next");

        } else {
            btnConfirm.setText("Finish");
        }
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rdGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            tv_total_question.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            btnConfirm.setText("confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

        } else {
            finishQuiz();
        }

    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();

            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) ((timeLeftInMillis / 1000) / 60);
        int seconds = (int) ((timeLeftInMillis / 1000) % 60);

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            tvCountDown.setTextColor(Color.RED);
        } else {
            tvCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void finishQuiz() {
        Intent resultInten = new Intent();
        resultInten.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultInten);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFS, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}