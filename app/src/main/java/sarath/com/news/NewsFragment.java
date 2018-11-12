package sarath.com.news;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Context context;
    private ProgressBar progressBar;

    private final String URL = "https://newsapi.org/";
    private String path2 = "top-headlines";
    private String path1 = "v2";
    private final String APIKEY = "00f85cd5105d43a384a947cadfd21525";
    private final String pageSize = "100";
    private String country, category, sources;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        if(getArguments() != null){
            country = getArguments().getString("country");
            category = getArguments().getString("category");
            sources = getArguments().getString("sources");
        }

        view = inflater.inflate(R.layout.layout_fragment, container, false);

        context = view.getContext();

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NewsAdapter(new ArrayList<News>(), context);
        recyclerView.setAdapter(adapter);

        if(sources.equals("none")){
            if(category.equals("none")){
                setTitle();
                getDataFromApi(country);
            }else{
                setTitle(country, category);
                getDataFromApi(country, category);
            }
        }else{
            setTitle(sources);
            getDataFromApi(country, category, sources);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateUI(ArrayList<News> data){
        progressBar.setVisibility(View.INVISIBLE);
        adapter = new NewsAdapter(data, context);
        recyclerView.setAdapter(adapter);
    }

    public String buildURL(String country){

        Uri builder = Uri.parse(URL);
        String url;

        url = builder.buildUpon()
                .appendPath(path1)
                .appendPath(path2)
                .appendQueryParameter("country", country)
                .appendQueryParameter("pageSize", pageSize)
                .appendQueryParameter("apiKey", APIKEY)
                .build().toString();

        return url;
    }

    public String buildURL(String country, String category, String sources){

        Uri builder = Uri.parse(URL);
        String url;

        url = builder.buildUpon()
                .appendPath(path1)
                .appendPath(path2)
                .appendQueryParameter("sources", sources)
                .appendQueryParameter("pageSize", pageSize)
                .appendQueryParameter("apiKey", APIKEY)
                .build().toString();

        return url;
    }

    public String buildURL(String country, String category){

        Uri builder = Uri.parse(URL);
        String url;

        url = builder.buildUpon()
                .appendPath(path1)
                .appendPath(path2)
                .appendQueryParameter("country", country)
                .appendQueryParameter("category", category)
                .appendQueryParameter("pageSize", pageSize)
                .appendQueryParameter("apiKey", APIKEY)
                .build().toString();

        return url;
    }

    public void getDataFromApi(String country, String category, String sources){
        getData(buildURL(country, category, sources));
    }

    public void getDataFromApi(String country, String category){
        getData(buildURL(country, category));
    }

    public void getDataFromApi(String country){
        getData(buildURL(country));
    }

    public void getData(String finalUrl){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, finalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateUI(new NewsDataParser(response).getArrayList());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something's wrong!", Toast.LENGTH_LONG).show();
                Log.e("Error", String.valueOf(error));
            }
        });

        queue.add(request);
    }

    public void setTitle() throws NullPointerException{
        getActivity().setTitle("Home");
    }

    public void setTitle(String sources) throws NullPointerException{
        if(sources.equals("the-hindu")){
            getActivity().setTitle("The Hindu");
        }else if(sources.equals("the-times-of-india")){
            getActivity().setTitle("The Times of India");
        }else if(sources.equals("google-news-in")){
            getActivity().setTitle("Google News (India)");
        }
    }

    public void setTitle(String country, String name) throws NullPointerException{
        String temp = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1, name.length());
        getActivity().setTitle(temp);
    }

    @Override
    public void onClick(View v) {

    }
}
