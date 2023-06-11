package com.azure.nzook.function;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

@FunctionalInterface
public interface ProgressConsumer<T> {

    void accept(T t) throws IOException, InterruptedException,KeeperException;
}
