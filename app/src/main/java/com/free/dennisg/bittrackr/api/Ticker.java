package com.free.dennisg.bittrackr.api;

/**
 * Created by Dennis Gimbergsson on 2017-01-08.
 **/

import com.free.dennisg.bittrackr.api.currencies.*;

public class Ticker {

    USD USD;
    SEK SEK;

    public com.free.dennisg.bittrackr.api.currencies.USD getUSD() {
        return USD;
    }

    public void setUSD(com.free.dennisg.bittrackr.api.currencies.USD USD) {
        this.USD = USD;
    }

    public com.free.dennisg.bittrackr.api.currencies.SEK getSEK() {
        return SEK;
    }

    public void setSEK(com.free.dennisg.bittrackr.api.currencies.SEK SEK) {
        this.SEK = SEK;
    }
}
