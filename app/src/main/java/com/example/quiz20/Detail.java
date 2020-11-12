package com.example.quiz20;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Detail extends AppCompatActivity {
    private TextView question,indicator;
    private LinearLayout optionsContainer;
    private Button previous, next;
    private FloatingActionButton bookmark;
    private int count = 0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0;
   // private int setNo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        question = findViewById(R.id.tv_qs);
        indicator = findViewById(R.id.tv_indicator);
        bookmark = findViewById(R.id.btn_bookmark);
        optionsContainer = findViewById(R.id.options_container);
        previous = findViewById(R.id.btn_previous);
        next = findViewById(R.id.btn_next);

        //test
      //setNo = getIntent().getIntExtra("setNo",1);


        list = new ArrayList<>();
        list.add(new QuestionModel("Which of the following is one of the primary benefits of eating carbs and fibre?","a) Helps with acne prevention","b) They are the body’s main source of energy","c) Promotes stronger hair and nails","d) Increase your life span","b) They are the body’s main source of energy"));
        list.add(new QuestionModel("Which of the following is a good source of carbs and fibre?","a) Strawberries","b) Oats","c) Spinach","d) Pita bread","b) Oats"));
        list.add(new QuestionModel("Which of the following is a good source of carbs and fibre?","a) Oranges","b) Fries","c) Chicken breast","d) Quinoa","d) Quinoa"));
        list.add(new QuestionModel("Which of the following is not a source of carbs and fibre?","a) Brown rice","b) Bananas","c) Mushroom","d) Peas","c) Mushroom"));
        list.add(new QuestionModel("Which of the following statements about carbs and fibre is incorrect?","a) Brown rice is a good source of carbs and fibre","b) One of primary benefits of consuming carbs and fibre is that they support healthy blood cholesterol levels","c) One of primary benefits of consuming carbs and fibre is that they boost your immunity","d) Black beans are a good source of carbs and fibre","c) One of primary benefits of consuming carbs and fibre is that they boost your immunity"));
        list.add(new QuestionModel("Which of the following statements about carbs and fibre is correct?","a) You do not need to eat carbs and fibre everyday","b) Some of the best sources of carbs and fibre include brown rice, barley and wholewheat pasta","c) Some of the best sources of carbs and fibre include brown rice, eggs and oats","d) Carbs and fibre are not the body’s main source of energy","b) Some of the best sources of carbs and fibre include brown rice, barley and wholewheat pasta"));
        list.add(new QuestionModel("What is not a type of carbohydrate?","a) Fat","b) Fiber","c) Starch","d) Sugar","a) Fat"));



        for (int i = 0; i < 4; i++) {
                optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        checkAns((Button) v);

                    }
                });

            }


        playAnim(question, 0, list.get(position).getQuestion());
        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                next.setEnabled(false);
                next.setAlpha(0.7f);
                enableOption(true);
                position++;
                if(position == list.size()){
                    //Toast.makeText(this,"you have finished the quiz!", Toast.LENGTH_SHORT).show();
                    Intent scoreIntent = new Intent(Detail.this, ScoreActivity.class);
                    //display scores on score activity
                    scoreIntent.putExtra("score", score);
                    scoreIntent.putExtra("total", list.size());
                    startActivity(scoreIntent);
                    finish();

                    return;
                }

                count =0;
                playAnim(question, 0, list.get(position).getQuestion());
            }
        });

    }

    //ref: https://www.youtube.com/watch?v=u2K4ns06auk

    private void playAnim (final View view, final int value, final String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (value == 0 && count < 4) {
                            String option = "";
                            if (count == 0) {
                                option = list.get(position).getoA();
                            } else if (count == 1) {
                                option = list.get(position).getoB();
                            } else if (count == 2) {
                                option = list.get(position).getoC();
                            } else if (count == 3) {
                                option = list.get(position).getoD();

                            }
                            playAnim(optionsContainer.getChildAt(count), 0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (value == 0) {
                            try{
                                ((TextView) view).setText(data);
                                //indicates the question number
                                indicator.setText(position+1+"/"+list.size());
                            }catch (ClassCastException ex){
                                ((Button) view).setText(data);
                            }
                            view.setTag(data);
                            playAnim(view, 1, data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {


                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void checkAns(Button selectedOption) {
        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1);
        //compare ans
        if(selectedOption.getText().toString().equals(list.get(position).getAns())) {
            //calculate scores (correct ans)
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7bd47e")));
        }else {
            //incorrect
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //wrong ANS
                selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff5252")));
               Button correctAns = (Button) optionsContainer.findViewWithTag(list.get(position).getAns());
               correctAns.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7bd47e")));
            }

            }
        }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableOption(boolean enable){

        for (int i = 0; i<4; i++){
           optionsContainer.getChildAt(i).setEnabled(enable);
           if(enable){
               optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
           }

                }
            }

        }

