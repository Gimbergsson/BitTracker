package com.free.dennisg.bittrackr.api;

import android.icu.util.Output;

import java.util.List;

/**
 * Created by Dennis Gimbergsson
 */

public class Txs {

    //Variables that are in our json
    private int ver;
    private long block_height;
    private String relayed_by;
    private int size;
    private long time;
    private long tx_index;
    private String hash;
    private List<Inputs> inputs;
    private List<Out> out;

    public List<Inputs> getInputs() {
        return inputs;
    }

    public void setInputs(List<Inputs> inputs) {
        this.inputs = inputs;
    }

    public List<Out> getOut() {
        return out;
    }

    public void setOut(List<Out> out) {
        this.out = out;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public long getBlock_height() {
        return block_height;
    }

    public void setBlock_height(long block_height) {
        this.block_height = block_height;
    }

    public String getRelayed_by() {
        return relayed_by;
    }

    public void setRelayed_by(String relayed_by) {
        this.relayed_by = relayed_by;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTx_index() {
        return tx_index;
    }

    public void setTx_index(long tx_index) {
        this.tx_index = tx_index;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
