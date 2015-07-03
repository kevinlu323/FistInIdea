package com.linkui;

import sun.swing.BakedArrayList;

/**
 * Created by linkui on 7/3/15.
 */
public class ProducerConsumer {
    public static void main(String[] args){
        Basket bst = new Basket();
        Producer p = new Producer(bst);
        Consumer c = new Consumer(bst);
        new Thread(p).start();
        new Thread(c).start();
    }
}

class Bread{
    int id;
    Bread(int _id){
        this.id=_id;
    }

    public String toString(){
        return "Bread id: "+ id;
    }
}

class Basket{
    int index=0;
    Bread[] brList = new Bread[6];

    public synchronized void push(Bread br){
        while(index==brList.length){
            try{
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        notify();
        brList[index]=br;
        index++;
        System.out.println("New Bread produced, now there are "+index+" bread in the basket.");
    }

    public synchronized Bread pop(){
        while(index==0){
            try{
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        notify();
        System.out.println("The last bread is consumed, now there are "+index+" bread in the basket.");
        index--;
        return brList[index];
    }
}

class Producer implements Runnable{
    Basket bst = null;
    Producer(Basket _bst){
        this.bst=_bst;
    }
    public void run(){
        for (int i=0;i<20;i++){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            bst.push(new Bread(i));
            System.out.println("Producing the "+i+"th Bread");
        }
    }
}

class Consumer implements Runnable{
    Basket bst = null;
    Consumer(Basket _bst){
        this.bst=_bst;
    }

    public void run(){
        for (int i=0;i<20;i++){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Consuming the "+bst.pop());
        }
    }
}
