package com.cole.stoplightv0;

public class StopLight {

    public enum State {
        STOP ("Stop"),
        GO ("Go"),
        WAIT ("Wait");

        private final String msg;

        State(String msg) {
            this.msg = msg;
        }

        public String msg() { return msg; }
    }

    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public StopLight (State state) {
        this.state = state;
    }
}
