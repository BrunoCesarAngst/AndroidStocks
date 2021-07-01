package com.example.myhttp;

import java.io.Serializable;

public class Stocks implements Serializable {


    private String nName;
    private String nLocation;
    private Double nPoints;
    private Double nVariation;

    public Stocks(String nName, String nLocation, double nPoints, double nVariation) {
        this.nName = nName;
        this.nLocation = nLocation;
        this.nPoints = nPoints;
        this.nVariation = nVariation;
    }

    public String getName() {
        return "Na: " + nName;
    }

    public String getLocation() {
        return "De: "+ nLocation.toString();
    }

    public String getPoints() {
        return "Índice: " + nPoints;

    }

    public String getVariation() {
        return "Variação: " + nVariation;

    }
}
