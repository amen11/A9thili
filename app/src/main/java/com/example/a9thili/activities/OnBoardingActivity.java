package com.example.a9thili.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a9thili.R;
import com.example.a9thili.adapters.SliderAdapter;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager viewpager;
    LinearLayout dotslayout;
    Button btn;
    SliderAdapter sliderAdapter;
    Animation animation;
    TextView[] dots ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);
        //hide action bar
        getSupportActionBar().hide();

        viewpager=findViewById(R.id.slider);
        dotslayout=findViewById(R.id.dots);
        btn=findViewById(R.id.get_started_btn);

        addDots(0);
        viewpager.addOnPageChangeListener(changeListener);

        //Adapter
        sliderAdapter=new SliderAdapter(this);
        viewpager.setAdapter(sliderAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnBoardingActivity.this,MainActivity.class));
                //finish();
            }
        });




    }

    private void addDots(int position) {
        dots=new TextView[3];
        dotslayout.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(25);
            dotslayout.addView(dots[i]);
        }
        if(dots.length==0){
            dots[position].setTextColor(getResources().getColor(R.color.pink));
        }

    }
    ViewPager.OnPageChangeListener changeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
                addDots(position);
                if(position==0){
                    btn.setVisibility(View.INVISIBLE);

                }else if(position==1){
                btn.setVisibility(View.INVISIBLE);
            }else {
                    animation= AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_animation);
                    btn.setAnimation(animation);
                    btn.setVisibility(View.VISIBLE);
                }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}