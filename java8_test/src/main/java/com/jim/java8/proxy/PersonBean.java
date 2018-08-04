package com.jim.java8.proxy;

/**
 * @author Jim
 * @date 2018/5/9
 */
public interface PersonBean {

    void setName(String name);

    void setGender(String gender);

    void setInterests(String interests);

    void setHotOrNotRating(int rating);

    String getName();

    String getGender();

    String getInterests();

    int getHotOrNotRating();
}
