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
import com.example.cardproject.model.Game;


public class GameFragment extends Fragment implements OnItemClickListener {

    // view
    private CardAdapter mCardAdapter;
    private ProgressBar mProgressBar;
    private TextView mGameTvTime, mGameTvScore;

    // timer
    private Timer mTimer;
    private TimerTask mTimerTask;

    int selectCount, totalCount, answerCount;
    int mGameTime, score = 0;

    private Game mGame;
    private int selectFirst, selectSecond;
    private boolean isFirst = true;

//    private ArrayList<ImageView> images = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        RecyclerView mGameRvCardList = view.findViewById(R.id.game_rv_card_list);
        mGameRvCardList.setLayoutManager(manager);
        mProgressBar = view.findViewById(R.id.progressBar);
        mGameTvTime =  view.findViewById(R.id.game_tv_time);
        mGameTvScore = view.findViewById(R.id.game_tv_score);

        mGame = new Game();
        mCardAdapter = new CardAdapter(mGame.getCards());
        mCardAdapter.setListener(this);
        mGameRvCardList.setAdapter(mCardAdapter);

        initValue();

        mGame.ready();

        start();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTimer();
    }

    private void start() {
        startTimer();
        flipThread(2000);
    }

    @Override
    public void onItemClick(View view, int pos) {

        Card card = mGame.getCardItem(pos);

        if(isFirst) { selectFirst = card.getFrontImgId(); }
        else { selectSecond = card.getFrontImgId(); }
        isFirst = !isFirst;

        ImageView ivCard = view.findViewById(R.id.iv_card);
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivCard, "rotationY", 0f, 180f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                ivCard.setEnabled(false);
                ivCard.setImageResource(card.getFrontImgId());

                selectCount++;
                totalCount++;

                // 기본 이미지 선택 시 무조건 다시
                if(selectFirst == R.drawable.card_default || selectSecond == R.drawable.card_default) {
                    initValue();
                    flipThread(1000);
                    return;
                }

                if(selectCount == 2) {
                    selectCount = 0;
                    if(selectFirst == selectSecond) {
                        answerCount++;
                        score += 123;
                        mGameTvScore.setText(numberFormat(score));
                    } else {
                        answerCount = 0;
                        totalCount = 0;
                        flipThread(1000);
                    }
                }

                if(totalCount == mGame.getTotalSelectCount()) {
                    if(answerCount == mGame.getAnswerCount()) {
                        score += 1000;
                        mGameTvScore.setText(numberFormat(score));

                        mGame.changeLevel(score);
                        mCardAdapter.setAllFront();
                        mCardAdapter.setCards(mGame.getCards());
                        mCardAdapter.notifyDataSetChanged();
                    }
                    initValue();
                    flipThread(1000);

                }
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    private void startTimer() {
        mGameTime = mGame.getGameTime();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mGameTime--;
                if(mGameTime == 0) { stopTimer(); }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mGameTvTime.setText(String.valueOf(mGameTime));
                        mProgressBar.incrementProgressBy(1);
                        if (mGameTime <= 10) {
                            mProgressBar.setProgressDrawable(getActivity().getDrawable(R.drawable.progress_red));
                        }
                        if(mGameTime <= 0) {
                            gameOver();
                        }
                    }
                });
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 1000, 1000);
    }

    private void gameOver() {
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

                mCardAdapter.setAllFront();
//                shuffleCards();
//                mCardAdapter.setCards(cards);

                mCardAdapter.notifyDataSetChanged();

                flipThread(1500);

                startTimer();

                mProgressBar.setProgress(0);
                mProgressBar.setProgressDrawable(getActivity().getDrawable(R.drawable.progress_custom));

                mGameTvScore.setText(String.valueOf(score));

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

    private void flipThread(int milli) {
        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(milli);
                    mCardAdapter.setAllBack(); // 뒤집기
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCardAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void valueSet() {
        score = 0;
        selectCount = totalCount = answerCount = 0;
    }

    private void initValue() {
        isFirst = true;
        selectCount = 0;
        totalCount = 0;
        answerCount = 0;
    }


    private void stopTimer() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }

        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
        }
    }

    private String numberFormat(int num) {
        String numStr = NumberFormat.getInstance().format(num);
        return numStr;
    }


}