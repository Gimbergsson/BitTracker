package com.free.dennisg.bittrackr.api;

/**
 * Created by Dennis Gimbergsson
 */

public class PrevOut {

    //Variables that are in our json
    private boolean spent;
    private long tx_index;
    private int type;
    private String addr;
    private long value;
    private int n;
    private String script;

    public boolean isSpent() {
        return spent;
    }

    public void setSpent(boolean spent) {
        this.spent = spent;
    }

    public long getTx_index() {
        return tx_index;
    }

    public void setTx_index(long tx_index) {
        this.tx_index = tx_index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
