package com.example.cardproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cardproject.MainActivity;
import com.example.cardproject.R;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private MainActivity mParentActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mParentActivity = (MainActivity) getActivity();

        Button btnStart = view.findViewById(R.id.btn_start);
        Button btnRecord = view.findViewById(R.id.btn_record);

        btnStart.setOnClickListener(this);
        btnRecord.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mParentActivity.nextFragment("GAME");
                break;
            case R.id.btn_record:
                mParentActivity.nextFragment("RECORD");
                break;
        }
    }
}