package com.example.cardproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardproject.R;
import com.example.cardproject.adapter.RecordAdapter;
import com.example.cardproject.model.Record;

public class RecordFragment extends Fragment {

    ArrayList<Record> recordList;
    public static RecordFragment newInstance(ArrayList<Record> records) {

        Bundle args = new Bundle();

        RecordFragment fragment = new RecordFragment();
        args.putSerializable("recordList", records);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        recordList = (ArrayList<Record>) getArguments().getSerializable("recordList");

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        RecyclerView rvRecordList = view.findViewById(R.id.rv_record_list);
        rvRecordList.setLayoutManager(manager);

        RecordAdapter recordAdapter = new RecordAdapter(recordList);
        rvRecordList.setAdapter(recordAdapter);

        return view;
    }

//    private ArrayList<RecordDto> getRecordList() {
//        ArrayList<RecordDto> records = new ArrayList<>();
//        records.add(new RecordDto(1000,"2020-10-12","안녕하세요"));
//        records.add(new RecordDto(2000,"2020-10-10","안알랴줌"));
//        records.add(new RecordDto(3000,"2020-10-09","나다"));
//        records.add(new RecordDto(4000,"2020-10-08","카드왕"));
//        records.add(new RecordDto(5000,"2020-10-05","네고왕"));
//        records.add(new RecordDto(0,"2020-10-03","발명왕"));
//
//        return records;
//    }
}