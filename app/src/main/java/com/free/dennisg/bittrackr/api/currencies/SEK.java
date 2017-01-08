package com.free.dennisg.bittrackr.api.currencies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dennis Gimbergsson on 2017-01-08.
 **/

public class SEK {

    @SerializedName("15m")
    double m15;
    double last;
    double buy;
    double sell;
    String symbol;

    public double getM15() {
        return m15;
    }

    public void setM15(double m15) {
        this.m15 = m15;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
