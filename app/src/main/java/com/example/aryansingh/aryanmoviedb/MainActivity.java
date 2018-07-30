package com.example.aryansingh.aryanmoviedb;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aryansingh.aryanmoviedb.Movies.MoviesFragment;
import com.example.aryansingh.aryanmoviedb.TvShows.TvShowFragment;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnFragmentInteractionListener,NetflixFragment.OnFragmentInteractionListener,TvShowFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("MOVIES"));
        tabLayout.addTab(tabLayout.newTab().setText("TV SHOWS"));
        tabLayout.addTab(tabLayout.newTab().setText("NETFLIX"));

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdaptor adaptor = new PagerAdaptor(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adaptor);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // create a search button which can search movies and cast
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class PagerAdaptor extends FragmentStatePagerAdapter {

        int nTabs;

        public PagerAdaptor(FragmentManager fm, int nTabs) {
            super(fm);
            this.nTabs = nTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    MoviesFragment moviesFragment = new MoviesFragment();
                    return moviesFragment;
                case 1:
                    TvShowFragment tvShowFragment = new TvShowFragment();
                    return tvShowFragment;
                case 2:
                    NetflixFragment netflixFragment = new NetflixFragment();
                    return netflixFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return nTabs;
        }
    }

}
