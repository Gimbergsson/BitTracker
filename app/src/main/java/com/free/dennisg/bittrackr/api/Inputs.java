package com.free.dennisg.bittrackr.api;

/**
 * Created by Dennis Gimbergsson
 */

public class Inputs {

    //Variables that are in our json
    private long sequence;
    private PrevOut prevOut;

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public PrevOut getPrevOut() {
        return prevOut;
    }

    public void setPrevOut(PrevOut prevOut) {
        this.prevOut = prevOut;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    private String script;

}
