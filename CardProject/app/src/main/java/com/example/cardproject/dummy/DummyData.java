package com.example.cardproject.dummy;

import com.example.cardproject.R;
import com.example.cardproject.model.Record;

import java.util.ArrayList;

public class DummyData {
    private ArrayList<Record> mRecords;
    private ArrayList<Integer> mImageDrawables;

    public ArrayList<Record> getRecordList() {
        mRecords = new ArrayList<>();
        mRecords.add(new Record(1000,"2020-10-12","안녕하세요"));
        mRecords.add(new Record(2000,"2020-10-10","안알랴줌"));
        mRecords.add(new Record(3000,"2020-10-09","나다"));
        mRecords.add(new Record(4000,"2020-10-08","카드왕"));
        mRecords.add(new Record(5000,"2020-10-05","네고왕"));
        mRecords.add(new Record(0,"2020-10-03","발명왕"));

        return mRecords;
    }

    public ArrayList<Integer> getImageDrawables() {
        mImageDrawables = new ArrayList<>();
        mImageDrawables.add(R.drawable.image1);
//        mImageDrawables.add(R.drawable.image2);
        mImageDrawables.add(R.drawable.image3);
        mImageDrawables.add(R.drawable.image4);
        mImageDrawables.add(R.drawable.image5);
        mImageDrawables.add(R.drawable.image6);
        mImageDrawables.add(R.drawable.image7);

        return mImageDrawables;
    }
}
