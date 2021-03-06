/*
 * Created by Romain Mathieu => https://github.com/mclouu
 */

package com.romain.mathieu.go4lunch.model;


public class CardData {

    private String name, adresse, horary, distance, numberWorkmates, imageUrl, placeID;
    private double rating;


    public CardData(String mName, String mAdresse, String mHorary, String mDistance, String mNumberWorkmates, double mRating, String mImageUrl, String placeID) {

        this.name = mName;
        this.adresse = mAdresse;
        this.horary = mHorary;
        this.distance = mDistance;
        this.numberWorkmates = mNumberWorkmates;
        this.rating = mRating;
        this.imageUrl = mImageUrl;
        this.placeID = placeID;
    }


    public String getAdresse() {
        return adresse;
    }

    public String getName() {
        return name;
    }

    public String getHorary() {
        return horary;
    }

    public String getDistance() {
        return distance;
    }

    public String getNumberWorkmates() {
        return numberWorkmates;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlaceID() {
        return placeID;
    }
}
