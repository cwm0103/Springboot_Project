package com.sbm.synchronizedclass;

/**
 * 同步线程
 * synchronized的用法 ，一个线程访问一个对象中的synchronized(this)同步代码块时，其他试图访问该对象的线程将被阻塞。
 */
public class SyncThread implements Runnable {
    private static int count;

    public SyncThread(){
        count=0;
    }
    @Override
    public void run() {
        synchronized (this){
            for(int i=0;i<5;i++){
                try{
                    System.out.println(Thread.currentThread().getName()+":"+(count++));
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
