package com.example.myapplication.Fragment.Menu4.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Fragment.Menu4.Boards.Board;
import com.example.myapplication.Fragment.Menu4.Boards.Board2;
import com.example.myapplication.Fragment.Menu4.Boards.Board3;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public PageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                //Board tabFragment1 = new Board();
                //return tabFragment1;
                return Board.newInstance();
            case 1:
                Board2 tabFragment2 = new Board2();
                return tabFragment2;
            case 2:
                Board3 tabFragment3 = new Board3();
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }


}
