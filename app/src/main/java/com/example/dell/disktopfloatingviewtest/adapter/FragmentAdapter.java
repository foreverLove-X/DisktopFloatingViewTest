package com.example.dell.disktopfloatingviewtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/*
 * viewpager的adapter
 * */
public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment> ();

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super (fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get (position);
    }

    @Override
    public int getCount() {
        return fragmentList.size ();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem (container, position, object);
    }
}
