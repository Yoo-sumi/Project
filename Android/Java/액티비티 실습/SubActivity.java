package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lab.databinding.ActivitySubBinding;

import java.util.Locale;

public class SubActivity extends AppCompatActivity {
    private ActivitySubBinding binding;
    private int num1,num2,ex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySubBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        Intent intent=getIntent();
        num1=intent.getIntExtra("num1",-1);
        num2=intent.getIntExtra("num2",-1);
        ex=intent.getIntExtra("ex",-1);

        if(num1!=-1 && num2!=-1){
            binding.textView.setText(String.format(Locale.KOREAN,"숫자1: %d",num1));
            binding.textView2.setText(String.format(Locale.KOREAN,"숫자2: %d",num2));
        }
    }
    public void mOnClick(View v){
        Intent data=new Intent();
        if(ex==1){
            data.putExtra("resultN",num1+num2);
            data.putExtra("resultS","두 숫자의 합: ");
        }
        else if(ex==2){
            data.putExtra("resultN",num1-num2);
            data.putExtra("resultS","두 숫자의 차: ");
        }
        else if(ex==3){
            data.putExtra("resultN",num1*num2);
            data.putExtra("resultS","두 숫자의 곱: ");
        }
        else if(ex==4){
            double div=(double)num1/num2;
            String divS=String.format("%.1f",div);
            data.putExtra("resultN",divS);
            data.putExtra("resultS","두 숫자의 나누기: ");
        }
        setResult(RESULT_OK,data);
        finish();
    }
}
