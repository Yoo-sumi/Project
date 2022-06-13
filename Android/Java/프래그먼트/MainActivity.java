package com.example.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fragmentexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        if(item.getItemId()==R.id.listview){
            ft.replace(R.id.fragment1,new AFragment());
        }
        else if(item.getItemId()==R.id.buttons){
            ft.replace(R.id.fragment1,new CFragment());
        }
        ft.commit();
        return super.onOptionsItemSelected(item);
    }

}

