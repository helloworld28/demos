package com.jim.java8.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Jim
 * @date 2018/5/9
 */
public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        MyRemote myRemote = new MyRemoteImpl();
        MyRemote stub = (MyRemote) UnicastRemoteObject.exportObject(myRemote, 9999);
        Registry registry = LocateRegistry.createRegistry(1099);

        registry.bind("myRemote", stub);

    }
}
