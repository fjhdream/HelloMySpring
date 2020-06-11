package com.fjhdream.HelloZookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelloDemo {
    ZooKeeper zooKeeper;


    @Before
    public void conn() {
        zooKeeper = ZKUtils.getZooKeeper();
    }

    @After
    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getConf() throws KeeperException, InterruptedException {
        if (zooKeeper == null) {
            System.out.println("ZooKeeper is null....");
        }

        if (zooKeeper != null) {
            System.out.println(zooKeeper.toString());
            WatchCallBack watchCallBack = new WatchCallBack();
            watchCallBack.setZooKeeper(zooKeeper);

            zooKeeper.exists("/hello",watchCallBack,watchCallBack,"abc");
        }


        while (true){}

    }
}
