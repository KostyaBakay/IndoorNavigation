package com.example.fooandbar.ibeacon.activity;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.adapter.IntroPageTransformer;
import com.example.fooandbar.ibeacon.adapter.IntroPagerAdapter;
import com.example.fooandbar.ibeacon.fragment.FragmentIntro;
import com.example.fooandbar.ibeacon.listeners.OnIntroSwipeListener;
import com.example.fooandbar.ibeacon.utils.PreferencesUtil;
import com.example.fooandbar.ibeacon.utils.UI;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView mTitle;
    private Button mSkipButton, mNextButton, mStartButton;
    private ImageView[] mIndicators;
    private IntroPagerAdapter mAdapter;
    private ArgbEvaluator mEvaluator;
    private IntroPageTransformer mTransformer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        if (mEvaluator == null) mEvaluator = new ArgbEvaluator();
        if (mTransformer == null) mTransformer = new IntroPageTransformer();
        setupUI();
        setupAdapter();
        updateTitle((String) mAdapter.getPageTitle(0), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTitle != null) mTitle = null;
        if (mViewPager != null) mViewPager = null;
        if (mSkipButton != null) mSkipButton = null;
        if (mStartButton != null) mStartButton = null;
        if (mNextButton != null) mNextButton = null;
        if (mAdapter != null) mAdapter = null;
        if (mEvaluator != null) mEvaluator = null;
        if (mTransformer != null) mTransformer = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_skip:
                PreferencesUtil.setVerified(this, true);
                onBackPressed();
                break;

            case R.id.button_next:
                int current_item = mViewPager.getCurrentItem();
                int count = mViewPager.getChildCount();
                if (current_item < count)
                    mViewPager.setCurrentItem(current_item + 1, true);
                break;

            case R.id.button_finish:
                PreferencesUtil.setVerified(this, true);
                onBackPressed();
                break;

            default:

        }
    }

    private void setupUI() {
        mTitle = (TextView) findViewById(R.id.title);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mSkipButton = (Button) findViewById(R.id.button_skip);
        mNextButton = (Button) findViewById(R.id.button_next);
        mStartButton = (Button) findViewById(R.id.button_finish);
        if (mNextButton != null) mNextButton.setOnClickListener(this);
        if (mStartButton != null) mStartButton.setOnClickListener(this);
        if (mSkipButton != null) mSkipButton.setOnClickListener(this);
    }

    private void setupAdapter() {
        if (mAdapter == null) {
            mAdapter = new IntroPagerAdapter(getSupportFragmentManager());
            mAdapter.addFragment(
                    getString(R.string.tutorial_title1),
                    R.color.tutorial_page1,
                    FragmentIntro.getInstance(
                            0, getString(R.string.tutorial_subtitle1),
                            R.drawable.im_page1)
            );
            mAdapter.addFragment(
                    getString(R.string.tutorial_title2),
                    R.color.tutorial_page2,
                    FragmentIntro.getInstance(
                            1, getString(R.string.tutorial_subtitle2),
                            R.drawable.im_page2)
            );
            mAdapter.addFragment(
                    getString(R.string.tutorial_title3),
                    R.color.tutorial_page3,
                    FragmentIntro.getInstance(
                            2, null,
                            R.drawable.im_page3)
            );
        }
        mViewPager.setAdapter(mAdapter);
    }

    private void setupViewPager() {
        final float scale = getResources().getDisplayMetrics().density;
        final int size = (int) (8 * scale + 0.5f);
        final int margin = (int) (2 * scale + 0.5f);
        LinearLayout layoutIndicators = (LinearLayout) findViewById(R.id.layout_indicators);
        int count = mAdapter.getCount();
        if (count > 0 && layoutIndicators != null) {
            mIndicators = new ImageView[count];
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(margin, 0, margin, 0);
            for (int i = 0; i < count; i++) {
                ImageView indicator = new ImageView(this);
                indicator.setLayoutParams(params);
                if (i == 0) indicator.setBackgroundResource(R.drawable.indicator_selected);
                else indicator.setBackgroundResource(R.drawable.indicator_unselected);
                mIndicators[i] = indicator;
                layoutIndicators.addView(indicator);
            }
        }
        mViewPager.addOnPageChangeListener(new OnIntroSwipeListener(
                this,
                mViewPager,
                mAdapter,
                mIndicators) {
            @Override
            public void pageChanged(int position, int count) {
                String title = (String) mAdapter.getPageTitle(position);
                updateTitle(title, position);
                mSkipButton.setVisibility(position == count - 1 ? View.GONE : View.VISIBLE);
                mNextButton.setVisibility(position == count - 1 ? View.GONE : View.VISIBLE);
                mStartButton.setVisibility(position == count - 1 ? View.VISIBLE : View.GONE);
            }
        });
        mViewPager.setPageTransformer(false, mTransformer);
    }

    private void updateTitle(String title, int position) {
        if (title == null) UI.hide(mTitle);
        else {
            mTitle.setText(title);
            UI.show(mTitle);
        }
    }

}
