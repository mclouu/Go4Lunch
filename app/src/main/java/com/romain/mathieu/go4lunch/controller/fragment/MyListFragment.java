package com.romain.mathieu.go4lunch.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.romain.mathieu.go4lunch.R;
import com.romain.mathieu.go4lunch.model.CardData;
import com.romain.mathieu.go4lunch.model.api.placeSearch.ResponseRestaurant;
import com.romain.mathieu.go4lunch.model.request.MapStreams;
import com.romain.mathieu.go4lunch.view.MyAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class MyListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static ArrayList<CardData> listRestaurant = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MyAdapter adapter;
    private Context context;
    private Disposable disposable;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public MyListFragment() {
        // Required empty public constructor
    }


    public static MyListFragment newInstance() {
        return (new MyListFragment());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_restaurants, container, false);
        context = container.getContext();

        ButterKnife.bind(this, view);
        Stetho.initializeWithDefaults(context);

        swipeRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);

        adapter = new MyAdapter(listRestaurant);
        recyclerView.setAdapter(adapter);

        // 2 - Call the stream
        this.executeHttpRequestWithRetrofit();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    public void onRefresh() {
        mProgressBar.setVisibility(View.VISIBLE);
        this.executeHttpRequestWithRetrofit();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    //-----------------------------------||
    //                                   ||
    //          HTTP (RxJava)            ||
    //                                   ||
    //-----------------------------------||


    // 1 - Execute our Stream
    private void executeHttpRequestWithRetrofit() {


        String userLocation = MyMapFragment.latlng;
        String radius = "5500";
        String type = "restaurant";
        String keyword = "";
//        String API_KEY = MyConstant.API_KEY;
        String API_KEY = "AIzaSyBW10_Ie5wh-vwbEXEfWzk2zOFOQ_xfDWk";
        this.disposable = MapStreams.streamFetchRestaurant(userLocation, radius, type, keyword, API_KEY).subscribeWith(
                new DisposableObserver<ResponseRestaurant>() {
                    @Override
                    public void onNext(ResponseRestaurant section) {
//                         1.3 - Update UI with topstories
                        updateUIWithListOfArticle(section);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
        );
    }

    private void updateUIWithListOfArticle(ResponseRestaurant response) {

        if (listRestaurant != null) {
            listRestaurant.clear();
        }

        int num_results = response.getResults().size();
        for (int i = 0; i < num_results; i++) {

            String name = response.getResults().get(i).getName();
            String adresse = response.getResults().get(i).getVicinity();
            String horary = "Ferm??";
//            if (response.getResults().get(i).getOpeningHours().getOpenNow()){
//                horary = "Ouvert";
//            }
            String distance = "200m";
            String numberWorkmates = "5";
            String placeID = response.getResults().get(i).getPlaceId();

            double rating = 0.0;
            if (response.getResults().get(i).getRating() != null) {
                rating = response.getResults().get(i).getRating();
            }
            String photoRef = "https://image.noelshack.com/fichiers/2018/17/7/1524955130-empty-image-thumb2.png";
            if (response.getResults().get(i).getPhotos() != null) {
                photoRef = response.getResults().get(i).getPhotos().get(0).getPhotoReference();
            }

            listRestaurant.add(new CardData(
                    name + " ",
                    adresse + " ",
                    horary + " ",
                    distance + " ",
                    numberWorkmates + " ",
                    rating,
                    photoRef,
                    placeID));
        }
        adapter.notifyDataSetChanged();
    }


    // 5 - Dispose subscription
    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

}
