package com.example.cardproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.cardproject.dummy.DummyData;
import com.example.cardproject.fragment.GameFragment;
import com.example.cardproject.fragment.HomeFragment;
import com.example.cardproject.fragment.RecordFragment;
import com.example.cardproject.model.Record;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ArrayList<Record> mRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        mRecords = new DummyData().getRecordList();

    }

    public void nextFragment(String type) {
        FragmentTransaction transaction;
        if(type.equals("GAME")) {
            transaction = getSupportFragmentManager().beginTransaction().add(R.id.container, new GameFragment());
        } else {
            transaction = getSupportFragmentManager().beginTransaction().add(R.id.container, RecordFragment.newInstance(mRecords));
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addRecord(int score, String nickname) {
        mRecords.add(0, new Record(score, "2020-10-17", nickname));
    }
}