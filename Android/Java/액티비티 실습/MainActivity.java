package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lab.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
    }
    public void mOnClick(View v){
        Intent intent=new Intent(this,SubActivity.class);
        intent.putExtra("num1",Integer.parseInt(binding.editText.getText().toString()));
        intent.putExtra("num2",Integer.parseInt(binding.editText2.getText().toString()));

        if(binding.radioButton.isChecked()){
            intent.putExtra("ex",1);

        }
        else if(binding.radioButton2.isChecked()){
            intent.putExtra("ex",2);
        }
        else if(binding.radioButton3.isChecked()){
            intent.putExtra("ex",3);
        }
        else if(binding.radioButton4.isChecked()){
            intent.putExtra("ex",4);
        }
        startActivityForResult(intent,0);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==0 && resultCode==RESULT_OK){

            String resultS=data.getStringExtra("resultS");
            if(binding.radioButton4.isChecked()){
                String resultN=data.getStringExtra("resultN");
                Toast.makeText(this,resultS+resultN,Toast.LENGTH_SHORT).show();
            }
            else {
                int resultN = data.getIntExtra("resultN", 0);
                Toast.makeText(this, resultS + resultN, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}
