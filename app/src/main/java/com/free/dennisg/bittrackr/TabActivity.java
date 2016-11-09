package com.free.dennisg.bittrackr;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class TabActivity extends AppCompatActivity{

    AHBottomNavigation mBottomNavigation;
    ViewPager mViewPager;
    MainFragmentPagerAdapter mMainFragmentPageradapter;
    AHBottomNavigationItem mTransactionsItem, mMiningItem, mStatsItem, mUnconfirmedTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*ViewPager to enable swipeable tabs*/
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        /*The bottom navigation bar*/
        mBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        /*Create items with Title and Icon*/
        mTransactionsItem = new AHBottomNavigationItem("Transactions", R.drawable.ic_menu_camera);
        mMiningItem = new AHBottomNavigationItem("Mining", R.drawable.ic_menu_gallery);
        mStatsItem = new AHBottomNavigationItem("Stats", R.drawable.ic_menu_manage);
        mUnconfirmedTransactions = new AHBottomNavigationItem("Mempool", R.drawable.ic_menu_share);

        /*Add items to bottom navigation bar*/
        mBottomNavigation.addItem(mTransactionsItem);
        mBottomNavigation.addItem(mMiningItem);
        mBottomNavigation.addItem(mStatsItem);
        mBottomNavigation.addItem(mUnconfirmedTransactions);

        /*Set Accent and Inactive colors on the tabs*/
        mBottomNavigation.setAccentColor(Color.parseColor("#444444"));
        mBottomNavigation.setInactiveColor(Color.parseColor("#9b9b9b"));

        /*Changes the ViewPage item to display the correct page when a tab is tapped*/
        mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                mViewPager.setCurrentItem(position);
                return true;
            }
        });

        /*Change tab when ViewPager page has changed*/
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
                mBottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*How many pages should be kept in memory*/
        mViewPager.setOffscreenPageLimit(5);

        mMainFragmentPageradapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainFragmentPageradapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
