package com.nas.ns100;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar navigationBar;

    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//        navigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        navigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp, "Movie&Tv").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.mipmap.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey))
                .setFirstSelectedPosition(0).initialise();

        fragments = getFragments();
        fragmentManager = getSupportFragmentManager();
        setDefaultFragment();
        navigationBar.setTabSelectedListener(this);
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, HomeFragment.newInstant("home"));
        transaction.commit();
    }

    public ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(HomeFragment.newInstant("home"));
        fragmentArrayList.add(BookFragment.newInstant("book"));
        fragmentArrayList.add(MusicFragment.newInstant("music"));
        fragmentArrayList.add(MovieFragment.newInstant("movie&tv"));
        fragmentArrayList.add(GameFragment.newInstant("games"));
        return fragmentArrayList;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = fragments.get(position);
//                if (fragment.isAdded()) {
                transaction.replace(R.id.fl_content, fragment);
//
//                } else {
//                    transaction.add(R.id.fl_content, fragment);
//                }
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = fragments.get(position);
                transaction.remove(fragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}