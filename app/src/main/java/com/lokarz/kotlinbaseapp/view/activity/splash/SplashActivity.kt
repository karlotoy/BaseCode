package com.lokarz.kotlinbaseapp.view.activity.splash

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdView
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.databinding.ActivitySplashBinding
import com.lokarz.kotlinbaseapp.pedometer.Pedometer
import com.lokarz.kotlinbaseapp.util.GsonUtil
import com.lokarz.kotlinbaseapp.view.base.BaseActivity
import com.lokarz.kotlinbaseapp.viewmodel.AdMobViewModel
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : BaseActivity() {


    @Inject
    lateinit var splashViewModel: SplashViewModel

    @Inject
    lateinit var adMobViewModel: AdMobViewModel

    lateinit var et: AppCompatEditText;
    lateinit var adView: AdView;

    lateinit var asdf : TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activitySplashBinding = DataBindingUtil.setContentView<ActivitySplashBinding>(
            this,
            R.layout.activity_splash
        )
        activitySplashBinding.splashViewModel = splashViewModel
        activitySplashBinding.lifecycleOwner = this

        initViewModels()
        initTestEditText()
        initAds()

        Pedometer.start(this)
        splashViewModel.title?.value = GsonUtil.getGsonString(Pedometer.getData(this))


//        replaceFragment(SplashFragment.newInstance())
    }

    fun initViewModels() {
        splashViewModel.init()
    }

    fun initTestEditText() {
        et = findViewById(R.id.et_text)
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                splashViewModel.title?.value = s.toString()
//                showReward()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    fun initAds() {
        adView = findViewById(R.id.adView)
        adView.loadAd(adMobViewModel.getAdRequest())

    }

    fun showReward() {
        adMobViewModel.showReward(this) { rewardItem -> Log.w("rewardItem", rewardItem.toString()) }
    }


}