package com.jim.java8.template;

/**
 * @author Jim
 * @date 2018/3/17
 */
public abstract class CaffeinBeverage {

    public void prepareRecipe(){

        boilWater();
        brew();
        pourInCup();
        if(isNeedAddCondiments()){
            addCondiments();
        }
    }

    public boolean isNeedAddCondiments() {
        return true;
    }

    protected abstract void addCondiments();


    private void pourInCup() {
        System.out.println("Pour in cup");
    }

    protected abstract void brew();

    protected  void boilWater(){
        System.out.println("boilWater");
    }




}
