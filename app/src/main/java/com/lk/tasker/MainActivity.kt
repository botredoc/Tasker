package com.lk.tasker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class MainActivity : AppCompatActivity() {
    lateinit var mAdView : AdView
    lateinit var button:Button
    lateinit var adLoader: AdLoader
    private lateinit var rewardedAd: RewardedAd


    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rewardedAd = RewardedAd(this,
            "ca-app-pub-3940256099942544/5224354917")
        button=findViewById(R.id.ads)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

//        val adLoadCallback = object: RewardedAdLoadCallback() {
//            override fun onRewardedAdLoaded() {
//                // Ad successfully loaded.
//            }
//            override fun onRewardedAdFailedToLoad(errorCode: Int) {
//                // Ad failed to load.
//            }
//        }

        fun createAndLoadRewardedAd(): RewardedAd {
             rewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917")
            val adLoadCallback = object: RewardedAdLoadCallback() {
                override fun onRewardedAdLoaded() {
                    // Ad successfully loaded.
                }
                override fun onRewardedAdFailedToLoad(errorCode: Int) {
                    // Ad failed to load.
                }
            }
            rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
            return rewardedAd
        }

        createAndLoadRewardedAd()

        button.setOnClickListener{
            if (rewardedAd.isLoaded) {
                val activityContext: Activity = this
                val adCallback = object: RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        // Ad opened.
                    }
                    override fun onRewardedAdClosed() {
                        rewardedAd = createAndLoadRewardedAd()

                    }
                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        // User earned reward.
                    }
                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        // Ad failed to display.
                    }
                }
                rewardedAd.show(activityContext, adCallback)
            }
            else {
                Log.d("TAG", "The rewarded ad wasn't loaded yet.")
            }
//            adLoader.loadAd(AdRequest.Builder().build())

//            if (mInterstitialAd.isLoaded) {
//                mInterstitialAd.show()
//            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.")
//            }
        }
        MobileAds.initialize(this) {

        }
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

//        var x:Int
//        x=34
//        var y=x/0
//        Log.e("tesr",y.toString())

    }
}
