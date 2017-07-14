package com.atmshang.procrastination.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by atmshang on 2017/3/24.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {

    private final TabItem[] items;

    public TabFragmentAdapter(FragmentManager fm, TabItem[] items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return items[position].fragment;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items[position].title;
    }

    public static class TabItem {
        final String title;
        final Fragment fragment;

        public TabItem(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }
}