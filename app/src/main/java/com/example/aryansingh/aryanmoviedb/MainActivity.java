package com.example.aryansingh.aryanmoviedb;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.aryansingh.aryanmoviedb.Movies.MoviesFragment;
import com.example.aryansingh.aryanmoviedb.Search.ItemAdapter;
import com.example.aryansingh.aryanmoviedb.Search.Result;
import com.example.aryansingh.aryanmoviedb.Search.SearchActivity;
import com.example.aryansingh.aryanmoviedb.Search.SearchInterface;
import com.example.aryansingh.aryanmoviedb.Search.SearchResponse;
import com.example.aryansingh.aryanmoviedb.TvShows.TvShowFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnFragmentInteractionListener,NetflixFragment.OnFragmentInteractionListener,TvShowFragment.OnFragmentInteractionListener{

    SearchView searchView;
    ItemAdapter itemAdapter;
    RecyclerView recyclerView;
    List<Result> items;
    ProgressBar progressBar;

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

//        items = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclerView);
//        progressBar = findViewById(R.id.progressBar);
//        itemAdapter = new ItemAdapter(this, items, new ItemAdapter.MoviesClickListener() {
//            @Override
//            public void onMovieClick(View view, int position) {
//
//            }
//        });
//        recyclerView.setAdapter(itemAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

    }

    // create a search button which can search movies and cast
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        MenuItem menuItem = menu.findItem(R.id.searchItem);
//        searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setQueryHint("Type your query");
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                if(!s.isEmpty()) {
//                    updateListOfResults(s);
//                }
//                return true;
//            }
//        });
//
//        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        });
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
//    private void updateListOfResults(String s) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/3/search/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        SearchInterface searchInterface = retrofit.create(SearchInterface.class);
//        Call<SearchResponse> call = searchInterface.search(MovieConstants.API_KEY,s);
//        call.enqueue(new Callback<SearchResponse>() {
//            @Override
//            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
//                SearchResponse searchResponse = response.body();
//                if(searchResponse != null){
//                    List<Result> searchResultsApi = searchResponse.getResults();
//
//                    items.clear();
//                    items.addAll(searchResultsApi);
//                    itemAdapter.notifyDataSetChanged();
//
//                    progressBar.setVisibility(View.GONE);
//
//                }else{
//                    Toast.makeText(MainActivity.this, "No results to show", Toast.LENGTH_LONG).show();
//                    items.clear();
//                    itemAdapter.notifyDataSetChanged();
//                    progressBar.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SearchResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//
//    }

}
