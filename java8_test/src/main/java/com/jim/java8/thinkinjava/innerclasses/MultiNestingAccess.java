package com.jim.java8.thinkinjava.innerclasses;

/**
 * @author Jim
 * @date 2019/5/3
 */
public class MultiNestingAccess {
    class MNA {
        private void f() {
        }

        class A {

            private void g() {
            }

            class B {

                private void method() {
                    f();
                    g();
                }
            }
        }

    }

    public static void main(String[] args) {
        MultiNestingAccess multiNestingAccess = new MultiNestingAccess();
    }
}
