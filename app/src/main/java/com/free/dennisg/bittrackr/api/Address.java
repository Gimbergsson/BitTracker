package com.free.dennisg.bittrackr.api;

import java.util.List;

/**
 * Created by Dennis Gimbergsson
 **/

public class Address {

    //Variables that are in our json
    private String hash160;
    private String address;
    private int n_tx;
    private long total_received;
    private long total_sent;
    private long final_balance;
    private List<Txs> txs;

    public String getHash160() {
        return hash160;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getN_tx() {
        return n_tx;
    }

    public void setN_tx(int n_tx) {
        this.n_tx = n_tx;
    }

    public long getTotal_received() {
        return total_received;
    }

    public void setTotal_received(int total_received) {
        this.total_received = total_received;
    }

    public long getTotal_sent() {
        return total_sent;
    }

    public void setTotal_sent(int total_sent) {
        this.total_sent = total_sent;
    }

    public long getFinal_balance() {
        return final_balance;
    }

    public void setFinal_balance(int final_balance) {
        this.final_balance = final_balance;
    }

    public List<Txs> getTxs() {
        return txs;
    }

    public void setTxs(List<Txs> txs) {
        this.txs = txs;
    }
}
