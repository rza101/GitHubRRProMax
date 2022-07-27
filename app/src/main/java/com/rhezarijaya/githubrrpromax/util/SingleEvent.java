package com.rhezarijaya.githubrrpromax.util;

public class SingleEvent<T> {
    private boolean hasBeenDone = false;
    private final T data;

    public SingleEvent(T data) {
        this.data = data;
    }

    public boolean hasBeenDone() {
        return hasBeenDone;
    }

    public T getData() {
        if (hasBeenDone) {
            return null;
        } else {
            hasBeenDone = true;
            return data;
        }
    }

    public T peekData() {
        return data;
    }
}
