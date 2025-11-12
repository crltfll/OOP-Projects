package com.example.m4wwtbam;

public class MyQueue<E> implements MyQueueInterface<E> {

    private E[] queue;
    private int front;
    private int back;
    private int MAX_SIZE;

    public MyQueue(int capacity) {
        queue = (E[]) new Object[capacity];
        front = 0;
        back = 0;
        MAX_SIZE = capacity;
    }

    public boolean isEmpty() {
        return front == back;
    }

    public boolean isFull() {
        return (back - front) == MAX_SIZE;
    }

    public void offer(E item) {
        if (isFull()) {
            throw new RuntimeException("Queue is full");
        }
        queue[back % MAX_SIZE] = item;
        back++;
    }

    public int size(){
        return back - front;
    }

    public E poll(){
        if (isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        E result = queue[front % MAX_SIZE];
        front++;
        return result;
    }

    public E front(){
        return queue[front % MAX_SIZE];
    }
    public E back(){
        return queue[(back - 1 + MAX_SIZE) % MAX_SIZE];
    }
}
