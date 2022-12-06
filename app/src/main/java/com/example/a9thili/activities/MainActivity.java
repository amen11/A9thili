package com.example.a9thili.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.a9thili.R;
import com.example.a9thili.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        homeFragment=new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }
}