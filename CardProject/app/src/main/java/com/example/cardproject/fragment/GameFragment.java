package com.example.cardproject.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardproject.MainActivity;
import com.example.cardproject.interfaces.OnItemClickListener;
import com.example.cardproject.R;
import com.example.cardproject.adapter.CardAdapter;
import com.example.cardproject.model.Card;


public class GameFragment extends Fragment implements OnItemClickListener {

    ArrayList<Card> cards;
    ArrayList<Integer> images;

    CardAdapter adapter;

    ProgressBar progressBar;
    TextView tvTime, tvScore;
    RecyclerView rvCardList;

    TimerTask tt;
    Timer timer;

    int selected1, selected2;
    int count, totalCount, answerCount;
    int counter, score;
    int TOTAL, ANSWER, RANDOM_SIZE;

//    Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        tvTime =  view.findViewById(R.id.tv_time);
        tvScore = view.findViewById(R.id.tv_score);
        rvCardList = view.findViewById(R.id.rv_card_list);

        valueSet();

        cardAndImageSetting();

        shuffleCards();

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        rvCardList.setLayoutManager(manager);
        adapter = new CardAdapter(cards);
        adapter.setListener(this);
        rvCardList.setAdapter(adapter);

        startTimer();

        resetThread(2000);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTimer();
    }

    @Override
    public void onItemClick(View view, int pos) {

        Card card = cards.get(pos);
        ImageView ivCard = view.findViewById(R.id.iv_card);
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivCard, "rotationY", 0f, 180f);

        if(selected1 == 0) {
            selected1 = card.getDefaultImgId();
        } else {
            selected2 = card.getDefaultImgId();
        }

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ivCard.setEnabled(false);
                ivCard.setImageResource(card.getDefaultImgId());
                count += 1;
                totalCount += 1;

                // 기본 이미지 선택 시 무조건 다시
                if(selected1 == R.drawable.image_default || selected2 == R.drawable.image_default) {
                    cardValueReset();

                    resetThread(1000);

                    return;
                }

                if(count == 2) {
                    count = 0;
                    if(selected1 == selected2) {
                        answerCount += 1;
                        score += 123;
                        tvScore.setText(numberFormat(score));
                    } else {
                        answerCount = 0;
                        totalCount = 0;
                        resetThread(1000);
                    }
                    selected1 = 0;
                    selected2 = 0;
                }

                if(totalCount == TOTAL) {
                    if(answerCount == ANSWER) {
                        score += 1000;
                        tvScore.setText(numberFormat(score));
                        adapter.start();
                        changeLevel();
                        adapter.setCards(cards);
                        adapter.notifyDataSetChanged();
                    }
                    cardValueReset();
                    resetThread(1000);
                }
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    private void changeLevel() {
        if(score < 2000) {
            shuffleCards();
        } else if(score < 4000) {
            RANDOM_SIZE = 3;
            TOTAL = RANDOM_SIZE * 2;
            ANSWER = RANDOM_SIZE;
            shuffleCards();
        } else {
            RANDOM_SIZE = 4;
            TOTAL = RANDOM_SIZE * 2;
            ANSWER = RANDOM_SIZE;
            shuffleCards();
        }
    }

    private void cardAndImageSetting() {
        images = new ArrayList<>();
        images.add(R.drawable.image1);
//        images.add(R.drawable.image2);
        images.add(R.drawable.image3);
        images.add(R.drawable.image4);
        images.add(R.drawable.image5);
        images.add(R.drawable.image6);
        images.add(R.drawable.image7);

        cards = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            cards.add(new Card());
        }
    }

    private void shuffleCards() {
        // 이미지 뽑기
        ArrayList<Integer> randomImages = new ArrayList<>();
        for(int i = 0; i < RANDOM_SIZE; i++) {
            int ranNum = (int)(Math.random() * 6); // 이미지 개수
            if(randomImages.contains(ranNum)) {
                i--;
            } else {
                randomImages.add(ranNum);
            }
        }
        randomImages.addAll(randomImages);

        // 실제 12개 중에 이미지가 변경될 index
        ArrayList<Integer> setImageIndexes = new ArrayList<>();
        for(int i = 0; i < RANDOM_SIZE * 2; i++) {
            int ranNum = (int)(Math.random() * 12);
            if(setImageIndexes.contains(ranNum)) {
                i--;
            } else {
                setImageIndexes.add(ranNum);
            }
        }

        for(int i = 0; i < setImageIndexes.size(); i++) {
            cards.get(setImageIndexes.get(i)).setDefaultImgId(images.get(randomImages.get(i)));
        }
    }

    private void resetThread(int milli) {
        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(milli);
                    adapter.reset();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    private void startTimer() {
        tt = new TimerTask() {
            @Override
            public void run() {
                counter--;
                if(counter == 0) { stopTimer(); }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText(String.valueOf(counter));
                        progressBar.incrementProgressBy(1);
                        if (counter <= 10) {
                            progressBar.setProgressDrawable(getActivity().getDrawable(R.drawable.progress_red));
                        }
                        if(counter <= 0) {
                            showDialog();
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(tt, 1000, 1000);
    }

    private void stopTimer() {
        if (tt != null) {
            tt.cancel();
        }

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void showDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_end, null);
        Button btnRestart = view.findViewById(R.id.btn_restart);
        Button btnOk = view.findViewById(R.id.btn_ok);
        EditText editNickname = view.findViewById(R.id.edit_nickname);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueSet();

                adapter.start();
                shuffleCards();
                adapter.setCards(cards);
                adapter.notifyDataSetChanged();

                resetThread(1500);

                startTimer();

                progressBar.setProgress(0);
                progressBar.setProgressDrawable(getActivity().getDrawable(R.drawable.progress_custom));

                tvScore.setText(String.valueOf(score));

                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String nickname = editNickname.getText().toString();
                ((MainActivity)getActivity()).addRecord(score, nickname);
                getActivity().onBackPressed();
            }
        });
    }

    private String numberFormat(int num) {
        String numStr = NumberFormat.getInstance().format(num);
        return numStr;
    }



    private void valueSet() {
        counter = 60;
        score = 0;
        selected1 = selected2 = 0;
        count = totalCount = answerCount = 0;

        TOTAL = 4;
        ANSWER = 2;
        RANDOM_SIZE = 2;
    }

    private void cardValueReset() {
        count = 0;
        totalCount = 0;
        answerCount = 0;
        selected1 = 0;
        selected2 = 0;
    }
}