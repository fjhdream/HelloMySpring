package com.fjhdream.HelloZookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

public class DefaultWatch implements Watcher {
    private CountDownLatch countDownLatch;

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        Event.EventType type = watchedEvent.getType();
        switch (type){
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }

        switch (watchedEvent.getState()){

            case Unknown:
            case Disconnected:
            case ConnectedReadOnly:
            case NoSyncConnected:
            case SyncConnected:
            case AuthFailed:
            case SaslAuthenticated:
            case Expired:
                System.out.println("watchedEvent = " + watchedEvent);
                countDownLatch.countDown();
                break;
        }
    }
}
