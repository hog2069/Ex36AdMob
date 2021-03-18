package com.mrhi2020.ex36admob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity {

    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-1614211254887177~3509353645");

        adView= findViewById(R.id.adview);

        //광고 로드하기
        AdRequest adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    //전면광고 객체 멤버변수
    InterstitialAd interstitialAd;

    public void clickBtn(View view) {
        //객체 생성
        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1614211254887177/3481433487");

        //광고 로드하기
        AdRequest adRequest= new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);

        //광고 보여주기
        //전면광고는 사이즈가 커서 로드하는데 오래걸림
        //바로 위에서 loadAd()하자마자 show()하면 읽어오기 전 이어서 보이지 않을 수 있음.
        //interstitialAd.show();

        //그래서 광고가 load 완료 된 후 show()명령을 하도록..
        //하기 위해 광로 로드상태를 듣는 리스너 추가
        interstitialAd.setAdListener( new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(MainActivity.this, "loaded", Toast.LENGTH_SHORT).show();
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //보상형 동영상광고 객체 참조변수
    RewardedVideoAd rewardedVideoAd;

    public void clickBtn2(View view) {
        //객체 생성
        rewardedVideoAd= MobileAds.getRewardedVideoAdInstance(this);
        //리스너 설정
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                rewardedVideoAd.show();
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                //기준 시간 이상 비디오를 시청하면 보상 아이템객체가 주어짐.
                String type= rewardItem.getType(); //상품명
                int num= rewardItem.getAmount();   //수량

                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(type+" : "+num);
                AlertDialog dialog= builder.create();
                dialog.show();

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(MainActivity.this, "failed!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });

        //광고 로드하기
        AdRequest request= new AdRequest.Builder().build();
        rewardedVideoAd.loadAd("ca-app-pub-1614211254887177/6818043269", request);

    }
}