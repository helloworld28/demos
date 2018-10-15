package com.jim.java8.proxy.cglib;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 * @author Jim
 * @date 2018/10/15
 */
public abstract class Bean implements Serializable {

    String sampleProperty;

    public abstract void addPropertyChangeListener(PropertyChangeListener listener);

    public abstract void removePropertyChangeListner(PropertyChangeListener listener);

    public String getSampleProperty() {
        return sampleProperty;
    }

    public void setSampleProperty(String sampleProperty) {
        this.sampleProperty = sampleProperty;
    }
}
