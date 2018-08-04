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
public class MyRemoteImpl  implements MyRemote {

    protected MyRemoteImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello world!";
    }

    public static void main(String[] args) {
        try {
            MyRemoteImpl myRemote = new MyRemoteImpl();
            Registry registry = LocateRegistry.getRegistry(9999);
            Remote remote = UnicastRemoteObject.exportObject(myRemote, 4099);
            registry.bind("remoteHello", remote);
//            Naming.rebind("remoteHello", myRemote);
        } catch (RemoteException e) {
            e.printStackTrace();
        }  catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

    }
}
