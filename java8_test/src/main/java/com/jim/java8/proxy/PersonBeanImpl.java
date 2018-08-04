package com.jim.java8.proxy;

/**
 * @author Jim
 * @date 2018/5/11
 */
public class PersonBeanImpl implements PersonBean {

    private String name;
    private String gender;
    private String interects;
    private int rating;
    private int ratingCount;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public void setInterests(String interests) {
        this.interects = interests;
    }

    @Override
    public void setHotOrNotRating(int rating) {
        this.rating += rating;
        ratingCount++;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getGender() {
        return this.gender;
    }

    @Override
    public String getInterests() {
        return this.interects;
    }

    @Override
    public int getHotOrNotRating() {
        if (ratingCount == 0) return 0;

        return rating / ratingCount;
    }
}
