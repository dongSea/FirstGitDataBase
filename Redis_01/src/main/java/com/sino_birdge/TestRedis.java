package com.sino_birdge;

public class TestRedis {
    public static class ABC_SYSnc implements Runnable{
        private String name;
        private Object prev;
        private Object self;

        public ABC_SYSnc(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        public void run() {
            int count=0;
            while (count<=10){
                synchronized (prev){
                    synchronized (self){
                        System.out.println(name);
                        count++;
                        self.notify();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ABC_SYSnc pa = new ABC_SYSnc("A", c, a);
        ABC_SYSnc pb = new ABC_SYSnc("B", a, b);
        ABC_SYSnc pc = new ABC_SYSnc("C", b, c);

        new Thread(pa).start();
        Thread.sleep(10);//保证初始ABC的启动顺序
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }

}
