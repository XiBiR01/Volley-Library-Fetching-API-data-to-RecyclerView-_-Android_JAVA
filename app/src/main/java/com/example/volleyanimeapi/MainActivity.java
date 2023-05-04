package com.example.volleyanimeapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public int param_pages = 1;
    boolean isScrolling = false;
    int currentItemsVisible, totalItems, scrolledOutItems;
    String url = "https://api.consumet.org/anime/gogoanime/top-airing?page=" + param_pages;
    public int a=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        RecyclerView rv = findViewById(R.id.rv);
        ArrayList<AnimeModel> animeList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonRequest(animeList, requestQueue, rv, param_pages);


    }

    private void jsonRequest(ArrayList<AnimeModel> animeList, RequestQueue requestQueue, RecyclerView rv, int param_pages) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");
                    for (int i = 0; i <= array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        AnimeModel modelObj = new AnimeModel(obj.getString("image"), obj.getString("title"));
                        animeList.add(modelObj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RvAdapter adapter = new RvAdapter(MainActivity.this, animeList);
                rv.setAdapter(adapter);
                LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this);
                rv.setLayoutManager(lm);
                rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            isScrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        currentItemsVisible = lm.getChildCount();
                        totalItems = lm.getItemCount();
                        scrolledOutItems = lm.findFirstVisibleItemPosition();
                        if (isScrolling && currentItemsVisible + scrolledOutItems == totalItems) {
                            fetchMoreData(animeList, requestQueue, recyclerView, a);
                            a++;
                        }
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("status", "Response Error");
            }
        });
        requestQueue.add(req);
    }

    private void fetchMoreData(ArrayList<AnimeModel> animeList, RequestQueue requestQueue, RecyclerView recyclerView, int pageToLoad) {
        String url = "https://api.consumet.org/anime/gogoanime/top-airing?page=" + pageToLoad;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean nextPage=response.getBoolean("hasNextPage");
                    if (nextPage){
                        JSONArray array = response.getJSONArray("results");
                        for (int i=0;i<=array.length();i++){
                            JSONObject obj=array.getJSONObject(i);
                            AnimeModel modelObj=new AnimeModel(obj.getString("image"),obj.getString("title"));
                            animeList.add(modelObj);
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "No More Data to Load", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.getAdapter().notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("status","Error getting More Data");
            }
        });
        requestQueue.add(req);
    }
}