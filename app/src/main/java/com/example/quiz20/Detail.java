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


        list = new ArrayList<>();
        list.add(new QuestionModel("question 1","A","B","C","D","C"));
        list.add(new QuestionModel("question 2","A","B","C","D","C"));
        list.add(new QuestionModel("question 3","A","B","C","D","B"));
        list.add(new QuestionModel("question 4","A","B","C","D","A"));


        for (int i = 0; i<4; i++){
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
                    //score activity
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
        //to-be cont'd  youtube 5-B
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void checkAns(Button selectedOption) {
        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1);
        if(selectedOption.getText().toString().equals(list.get(position).getAns())) {
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
        }else {
            //incorrect
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
               Button correctAns = (Button) optionsContainer.findViewWithTag(list.get(position).getAns());
               correctAns.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
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

