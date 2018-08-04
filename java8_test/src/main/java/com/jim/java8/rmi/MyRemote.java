package com.jim.java8.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Jim
 * @date 2018/5/9
 */
public interface MyRemote extends Remote {
    String sayHello() throws RemoteException;
}
