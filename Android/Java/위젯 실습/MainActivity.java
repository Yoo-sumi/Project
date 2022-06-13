package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.lab.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        binding.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int a=Integer.parseInt(binding.editText.getText().toString());
                int b=Integer.parseInt(binding.editText2.getText().toString());

                int sum=a+b;
                int sub=a-b;
                int mul=a*b;
                double div=(double)a/b;

                String c=String.format("결과: %d, %d, %d, %.1f",sum,sub,mul,div);
                binding.textView.setText(c);
            }
        });
        //setContentView(R.layout.activity_main);
        /*mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, mButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
