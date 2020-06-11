package com.fjhdream.HelloZookeeper;


import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZKUtils {

    private static ZooKeeper zooKeeper;

    private static String address="192.168.244.131:2181,192.168.244.131:2182,192.168.244.131:2183";

    private static DefaultWatch watcher = new DefaultWatch();

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public static ZooKeeper getZooKeeper() {
        try {
            watcher.setCountDownLatch(countDownLatch);
            zooKeeper = new ZooKeeper(address, 1000, watcher);
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return zooKeeper;

    }

}
