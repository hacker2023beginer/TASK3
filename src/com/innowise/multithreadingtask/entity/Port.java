package com.innowise.multithreadingtask.entity;

public class Port {
    private Port() {}

    private static class PortHolder{
        private static final Port INSTANCE = new Port();
    }

    public static Port getInstance(){
        return PortHolder.INSTANCE;
    }
}
