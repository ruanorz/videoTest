package com.example.myvideos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.myvideos.dto.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvVideos;
    MyRecyclerViewAdapter adapter;

    ArrayList<News> newsData = new ArrayList<News>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvVideos = findViewById(R.id.rvVideos);


        generateData();

        newsData.get(0).setPlaying(true);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvVideos);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        adapter = new MyRecyclerViewAdapter(this, newsData);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstPos = llm.findFirstVisibleItemPosition();
                int lastPos = llm.findLastVisibleItemPosition();
                int middle = Math.abs(lastPos - firstPos) / 2 + firstPos;

                int selectedPos = -1;
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    if (i == middle) {
                        if (!adapter.getItem(i).getPlaying()) {
                            adapter.getItem(i).setPlaying(true);
                            adapter.notifyItemChanged(i);
                        }

                        selectedPos = i;
                    } else {
                        if (adapter.getItem(i).getPlaying()) {
                            adapter.getItem(i).setPlaying(false);
                            adapter.notifyItemChanged(i);
                        }
                    }
                }

               // adapter.notifyDataSetChanged();
            }
        });

    }


    private void generateData(){

        for (int i=0; i<10; i++) {
            News newsAux = new News();
            newsAux.setTitle("News "+i);
            newsAux.setPlaying(false);
            newsAux.setUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
            newsData.add(newsAux);
        }
    }

}
