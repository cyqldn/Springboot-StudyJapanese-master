package com.chun.myspringboot.util;

/**
 * @author cyq
 * @create 2022-09-05.23:15
 */
public class MyRunnable implements Runnable{
    private int TICKET=100;
   Object object =new Object();

    @Override
    public void run() {

        while (true){
            synchronized (object) {
                if (TICKET > 0) {

                    try {
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在抢第" + TICKET + "张票");
                    TICKET--;
                } else {
                    break;
                }
            }
      }
}

}
class MyThread{
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();

        new Thread(myRunnable).start();
        new Thread(myRunnable).start();
        new Thread(myRunnable).start();



    }
}

//单例模式 饿汉式

class Bank{
    private Bank(){

    }
    private static Bank bank= new Bank();

    public static Bank getBank(){
        return bank;
    }
}
class BankTest{
    public static void main(String[] args) {
        Bank.getBank();
    }
}