package com.fjhdream.HelloZookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

public class WatchCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    private ZooKeeper zooKeeper;

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {

        if (stat != null) {
            System.out.println("I'm StatCallBack content: ");
            System.out.println("i = " + i + ", s = " + s + ", o = " + o + ", stat = " + stat);
            zooKeeper.getData(s, this, this,o);
        } else {
            System.out.println("There is no /hello");
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getState()){
            case Unknown:
            case Disconnected:
            case NoSyncConnected:
            case SyncConnected:
            case AuthFailed:
            case ConnectedReadOnly:
            case SaslAuthenticated:
            case Expired:
                System.out.println("watchedEvent = " + watchedEvent);
                break;
        }

        switch (watchedEvent.getType()){
            case None:
            case NodeCreated:
            case NodeDeleted:
            case NodeDataChanged:
            case NodeChildrenChanged:
                System.out.println("watchedEvent = " + watchedEvent);
                break;
        }
    }

    @Override
    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        if (stat != null) {
            System.out.println("I'm DataCallBack content:");
            System.out.println("Config answer:" + new String(bytes));
            System.out.println("i = " + i + ", s = " + s + ", o = " + o + ", bytes = " + Arrays.toString(bytes) + ", stat = " + stat);
        }
    }
}
