package com.jim.java8.thinkinjava.innerclasses;

/**
 * 通过内部类开实现一个迭代器
 * 体验一个内部的特性
 * 1.类隐藏
 * 2.内部类直接拥有外部类的引用，可以直接访问外部类的变量
 * 3.假如类是继承或者实现接口的,可以向上转型,然后外部是获取不到具体的实现类的,不像实现接口时子类一定是public
 * 另:还可实现在方法内或者语句里实现内部类,这两个用法就是为了让类的访问性更低,无论它声明在哪,它都对外部类的成员属性有访问权
 * <p>
 * 5.使用内部类还可以实现多实现,比如多一个反序的selector
 *
 * @author Jim
 * @date 2019/5/2
 */
public class Sequence {

    private int index;
    private Object[] items;

    public Sequence(int size) {
        items = new Object[size];
    }

    public void add(Object object) {
        if (items.length <= index) {
            throw new IllegalArgumentException("the items is full");
        }
        items[index++] = object;
    }

    /**
     * 可以定义为私有的
     */
    private class SequenceSelector implements Selector {
        private int i;

        @Override
        public boolean end() {
            return i == items.length;
        }

        @Override
        public Object current() {
            return items[i];
        }

        @Override
        public Object next() {
            return items[i++];
        }

        public Sequence outerClass() {
            //返回外部的对象
            return Sequence.this;
        }
    }

    private class ReverseSelector implements Selector {

        @Override
        public boolean end() {
            return false;
        }

        @Override
        public Object current() {
            return null;
        }

        @Override
        public Object next() {
            return null;
        }
    }

    Foo getFoo() {
        if (index > 100) {

            class FooImpl implements Foo {
                @Override
                public void bar() {
                    System.out.println("this is foo bar" + index);
                }
            }
            return new FooImpl();
        }
        return null;
    }

    Selector selector() {
        return new SequenceSelector();
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence(4);
        sequence.add("A");
        sequence.add("B");
        sequence.add("C");
        sequence.add("D");
        SequenceSelector selector = (SequenceSelector) sequence.selector();

        System.out.println(selector.outerClass());

        //直接创建内部类
        SequenceSelector sequenceSelector = sequence.new SequenceSelector();


        while (!selector.end()) {
            System.out.println(selector.next());
        }


        //声明在方法里内部类是不会让外界的发现
        //FooImpl foo = sequence.getFoo();
        Foo foo = sequence.getFoo();
        foo.bar();

    }

    interface Foo {
        void bar();
    }
}
