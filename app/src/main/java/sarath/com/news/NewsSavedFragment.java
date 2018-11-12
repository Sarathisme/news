package sarath.com.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsSavedFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private NewsDatabase database;
    private ArrayList<News> news;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment, container, false);

        getActivity().setTitle("Saved Articles");

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        news = new ArrayList<>();
        adapter = new NewsAdapter(news, getContext(), true);
        recyclerView.setAdapter(adapter);

        getDataFromRoom();

        return view;
    }

    public void getDataFromRoom(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                database = NewsDatabase.getNewsDatabase(getContext());
                final List<News> data = database.newsDao().getAll();

                try {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            updateUI(data);
                        }
                    });
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateUI(List<News> data) throws NullPointerException{
        if(data.size() == 0){
            Toast.makeText(getContext(), "No saved articles found!", Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
        news.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
