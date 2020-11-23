package com.example.cardproject.dummy;

import com.example.cardproject.model.Record;

import java.util.ArrayList;

public class DummyData {
    private ArrayList<Record> mRecordList;

    public ArrayList<Record> getRecordList() {
        mRecordList = new ArrayList<>();
        mRecordList.add(new Record(1000,"2020-10-12","안녕하세요"));
        mRecordList.add(new Record(2000,"2020-10-10","안알랴줌"));
        mRecordList.add(new Record(3000,"2020-10-09","나다"));
        mRecordList.add(new Record(4000,"2020-10-08","카드왕"));
        mRecordList.add(new Record(5000,"2020-10-05","네고왕"));
        mRecordList.add(new Record(0,"2020-10-03","발명왕"));

        return mRecordList;
    }
}
