package com.example.m4wwtbam;

public interface MyQueueInterface<E> {

    public void offer(E item);
    public E poll();
    public int size();
    public boolean isEmpty();
    public boolean isFull();
    public E front();
    public E back();

}
