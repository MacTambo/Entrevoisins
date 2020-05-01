package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

public class NeighbourProfileActivity extends AppCompatActivity {

    private FloatingActionButton mBackButton;
    private ImageView mProfilePicture;
    private FloatingActionButton mFavButtonClicked;
    private FloatingActionButton mFavButtonUnclicked;
    private TextView mName;
    private TextView mLocation;
    private TextView mPhone;
    private TextView mNetwork;
    private ImageView mLocationPicture;
    private ImageView mPhonePicture;
    private ImageView mNetworkPicture;
    private CardView mTopCardView;
    private TextView mNameTitle;
    private CardView mBottomCardView;
    private TextView mInfoTitle;
    private TextView mDescription;
    private Neighbour mNeighbour;
    private NeighbourApiService mFavApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNeighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");
        mBackButton = findViewById(R.id.backbutton);
        mProfilePicture = findViewById(R.id.profilepicture);
        mFavButtonClicked = findViewById(R.id.favbuttonclicked);
        mFavButtonUnclicked = findViewById(R.id.favbuttonunclicked);
        mName = findViewById(R.id.name);
        mLocation = findViewById(R.id.location);
        mPhone = findViewById(R.id.phone);
        mNetwork = findViewById(R.id.network);
        mLocationPicture = findViewById(R.id.locationpicture);
        mPhonePicture = findViewById(R.id.phonepicture);
        mNetworkPicture = findViewById(R.id.networkpicture);
        mTopCardView = findViewById(R.id.topcardview);
        mNameTitle = findViewById(R.id.nametitle);
        mBottomCardView = findViewById(R.id.bottomcardview);
        mInfoTitle = findViewById(R.id.infotitle);
        mDescription = findViewById(R.id.description);
        mFavApiService =  DI.getNeighbourApiService();

        Glide.with(NeighbourProfileActivity.this)
                .load(mNeighbour.getAvatarUrl())
                .into(mProfilePicture);
        mName.setText(mNeighbour.getName());
        mLocation.setText(mNeighbour.getAddress());
        mPhone.setText(mNeighbour.getPhoneNumber());
        mNetwork.setText(mNeighbour.getNetwork());
        mNameTitle.setText(mNeighbour.getName());
        mDescription.setText(mNeighbour.getAboutMe());

        if(mFavApiService.getFavorites().contains(mNeighbour)) {
            mFavButtonUnclicked.hide();
            mFavButtonClicked.show();
        }
        else{
            mFavButtonClicked.hide();
            mFavButtonUnclicked.show();
        }


        mFavButtonUnclicked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Vous avez ajouté ce voisin aux favoris !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mFavButtonUnclicked.hide();
                mFavButtonClicked.show();
                mFavApiService.createFavorite(mNeighbour);
            }
        });

        mFavButtonClicked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Vous avez retiré ce voisin des favoris !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mFavButtonClicked.hide();
                mFavButtonUnclicked.show();
                mFavApiService.deleteFavorite(mNeighbour);
            }
        });
    }

    public void BackButton(View view) {
        this.finish();
    }

}
