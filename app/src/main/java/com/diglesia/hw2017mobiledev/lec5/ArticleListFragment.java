package com.diglesia.hw2017mobiledev.lec5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends Fragment {
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_articlelist, container, false);

        mListView = (ListView) v.findViewById(R.id.list_view);

        getArticles();
        return v;
    }

    private void getArticles() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://newsapi.org/v1/articles?source=bbc-news&apiKey="; //<APPEND YOUR API KEY HERE>
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("DEI", response.toString());
                        //String[] foo = new String[5];
                        List<String> articleTitleList = new ArrayList<>();
                        try {
                            JSONArray articleObjArray = response.getJSONArray("articles");
                            for (int i = 0; i< articleObjArray.length(); i++) {
                                JSONObject articleObj = articleObjArray.getJSONObject(i);
                                String title = articleObj.getString("title");
                                //Log.i("DEI", "found article title:"+title);
                                articleTitleList.add(title);
                            }
                            // End of loop, list of title strings ready to display
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, articleTitleList);
                            mListView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("DEI", error.toString());
                    }
                });

        queue.add(jsonObjRequest);
    }
}
