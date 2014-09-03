package com.eccuria.demos.hackademyandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity
                          implements ActionBar.TabListener,
                                     ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private Fragment[] fragments = new Fragment[]{new EventsListFragment(),
            new EventsMapFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(
                actionBar.newTab()
                        .setText(getString(R.string.title_list))
                        .setTabListener(this)
        );

        actionBar.addTab(
                actionBar.newTab()
                        .setText(getString(R.string.title_map))
                        .setTabListener(this)
        );
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction transaction) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction transaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
    }

    public void getQueryText(View v) {
        EventsListFragment listFragment = (EventsListFragment) fragments[0];
        if (listFragment.isVisible()) {
            listFragment.getQueryText(v);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
        getSupportActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }
        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}

