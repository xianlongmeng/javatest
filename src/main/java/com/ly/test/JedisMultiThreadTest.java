package com.ly.test;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JedisMultiThreadTest {
    private static Jedis jedis;
    private static String v;
    public static void main(String[] args) throws InterruptedException {
        //jedis=new Jedis("localhost",6379);
        v="asdfasd";
        for (int j=0;j<10000;j++){
            v=v+"asdfasdasdafasdf";
        }
        ExecutorService executorService=Executors.newFixedThreadPool(100);
        for (int i=0;i<100;i++) {
            executorService.submit(new ReadWriteRedis(i));
        }
        executorService.awaitTermination(1000, TimeUnit.MINUTES);

    }
    private static class ReadRedis implements Runnable{

        private int index;
        public ReadRedis(int index){
            this.index=index;
        }
        @Override
        public void run() {
            while(true){
                for(int i=0;i<100;i++){
                    long stime=System.nanoTime();
                    System.out.println("R:index:"+index+" value:"+jedis.get(index*100+i+"")+" time:"+(System.nanoTime()-stime));
                }
            }
        }
    }
    private static class WriteRedis implements Runnable{

        private int index;
        public WriteRedis(int index){
            this.index=index;
        }
        @Override
        public void run() {
            while(true){
                for(int i=0;i<100;i++){
                    long stime=System.nanoTime();
                    jedis.set(""+(index*100+i),""+stime);
                    System.out.println("W:index:"+index+" time:"+(System.nanoTime()-stime));
                }
            }
        }
    }
    private static class ReadWriteRedis implements Runnable{

        private int index;
        private Jedis jedis;
        public ReadWriteRedis(int index){
            this.index=index;
        }
        @Override
        public void run() {
            jedis=new Jedis("localhost",6379);

            while(true){
                for(int i=0;i<100;i++){
                    long stime=System.nanoTime();
                    String key="100";//index*100+"";
                    String vv=jedis.get(key);
//                    if (v == null || v.isEmpty())
//                        v="1";
//                    else
//                        v=(Long.parseLong(v)+1)+"";
                    jedis.set(key,v);
                    System.out.println("RW:index:"+index+" value:"+" time:"+(System.nanoTime()-stime));
                }
            }
        }
    }
}
