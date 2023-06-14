package com.azure.nzook.function;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
/**
 * @author niu
 * @version 1.0
 */
@FunctionalInterface
public interface ProgressConsumer<T> {

    void accept(T t) throws IOException, InterruptedException,KeeperException;
}
