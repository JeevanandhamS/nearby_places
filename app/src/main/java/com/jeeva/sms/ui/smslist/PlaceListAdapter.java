package com.jeeva.sms.ui.smslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeeva.sms.R;
import com.jeeva.sms.data.webservice.dto.Place;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlacesListVH> {

    private LayoutInflater mInflater;

    private List<Place> mPlaceList = new ArrayList<>();

    private PublishSubject<Integer> mFavoriteClick = PublishSubject.create();

    public PlaceListAdapter(LayoutInflater inflater, List<Place> placeList) {
        this.mInflater = inflater;
        this.mPlaceList = placeList;
    }

    public Observable<Integer> observeFavoriteClicks() {
        return mFavoriteClick;
    }

    @NonNull
    @Override
    public PlacesListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlacesListVH(mInflater.inflate(R.layout.inflater_place_row,
                parent, false), mFavoriteClick);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesListVH holder, int position) {
        holder.bind(mPlaceList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlaceList.size();
    }

    class PlacesListVH extends RecyclerView.ViewHolder {

        TextView tvPlaceName;
        
        TextView tvAddress;
        
        ImageButton ibtnFavourite;

        public PlacesListVH(View view, PublishSubject<Integer> favoriteClick) {
            super(view);

            tvPlaceName = view.findViewById(R.id.place_row_tv_name);
            tvAddress = view.findViewById(R.id.place_row_tv_address);
            ibtnFavourite = view.findViewById(R.id.place_row_ibtn_fav);

            ibtnFavourite.setOnClickListener(v -> favoriteClick.onNext(getAdapterPosition()));
        }

        public void bind(Place place) {
            tvPlaceName.setText(place.getName());
            tvAddress.setText(place.getAddress());

            ibtnFavourite.setSelected(place.isFavorite());
        }
    }
}