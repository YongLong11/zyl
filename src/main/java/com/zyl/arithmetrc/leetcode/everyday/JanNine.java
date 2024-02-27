package com.zyl.arithmetrc.leetcode.everyday;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class JanNine {

    static class Foo {

        private static final AtomicInteger lock = new AtomicInteger(0);
        public Foo() {
        }

        public void first(Runnable printFirst) throws InterruptedException {
                lock.incrementAndGet();
                printFirst.run();
            // printFirst.run() outputs "first". Do not change or remove this line.
        }

        public void second(Runnable printSecond) throws InterruptedException {
            while (lock.get() != 1){

            }
                lock.incrementAndGet();
                printSecond.run();

            // printSecond.run() outputs "second". Do not change or remove this line.
        }

        public void third(Runnable printThird) throws InterruptedException {
            while (lock.get() != 2){

            }
                lock.set(0);
                printThird.run();

            // printThird.run() outputs "third". Do not change or remove this line.
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Foo foo = new Foo();
        Runnable first = () -> System.out.println(1);
        Runnable second = () -> System.out.println(2);
        Runnable third = () -> System.out.println(3);
        Thread thread = new Thread();

        foo.first(first);
        foo.third(third);
        foo.second(second);
    }

}
