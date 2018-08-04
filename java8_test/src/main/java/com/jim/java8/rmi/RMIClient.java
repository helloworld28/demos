package com.jim.java8.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Jim
 * @date 2018/5/9
 */
public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry localhostRegistry = LocateRegistry.getRegistry("192.168.1.251");
        MyRemote remoteHello = (MyRemote) localhostRegistry.lookup("myRemote");

        System.out.println(remoteHello.sayHello());
    }
}
