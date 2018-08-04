package com.jim.java8.iterator;

/**
 * @author Jim
 * @date 2018/4/21
 */
public class Test {
    public static void main(String[] args) {
        Menu rootMenu = new Menu();
        rootMenu.setName("rootMenu");
        rootMenu.setDescription("this is rootMenu");

        Menu dinerMenu = new Menu();
        dinerMenu.setName("dinerMenu");
        dinerMenu.setDescription("dinerMenu");
        MenuItem item1 = new MenuItem();
        item1.setName("mice");
        item1.setDescription("mice");
        item1.setPrice(1.02);
        dinerMenu.add(item1);

        Menu lauchMenu = new Menu();
        lauchMenu.setName("lauchMenu");
        lauchMenu.setDescription("lachfd");
        MenuItem menuItem = new MenuItem();
        menuItem.setName("dog");
        menuItem.setDescription("good");
        menuItem.setPrice(20.3);
        lauchMenu.add(menuItem);

        rootMenu.add(dinerMenu);
        rootMenu.add(lauchMenu);

        rootMenu.print();
    }
}
