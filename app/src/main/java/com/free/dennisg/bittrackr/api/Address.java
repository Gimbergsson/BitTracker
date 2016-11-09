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
    /*private int total_received;
    private int total_sent;
    private int final_balance;
    private List<AddressTxs> txs;*/

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

    /*
    public int getTotal_received() {
        return total_received;
    }

    public void setTotal_received(int total_received) {
        this.total_received = total_received;
    }

    public int getTotal_sent() {
        return total_sent;
    }

    public void setTotal_sent(int total_sent) {
        this.total_sent = total_sent;
    }

    public int getFinal_balance() {
        return final_balance;
    }

    public void setFinal_balance(int final_balance) {
        this.final_balance = final_balance;
    }

    public List<AddressTxs> getTxs() {
        return txs;
    }

    public void setTxs(List<AddressTxs> txs) {
        this.txs = txs;
    }
    */
}
