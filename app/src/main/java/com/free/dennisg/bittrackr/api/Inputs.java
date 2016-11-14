package com.free.dennisg.bittrackr.api;

/**
 * Created by Dennis Gimbergsson
 */

public class Inputs {

    //Variables that are in our json
    private long sequence;
    private Prev_out prev_out;
    private String script;

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public Prev_out getPrev_out() {
        return prev_out;
    }

    public void setPrev_out(Prev_out prev_out) {
        this.prev_out = prev_out;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

}
