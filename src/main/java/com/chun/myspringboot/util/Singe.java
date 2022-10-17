package com.chun.myspringboot.util;

public class Singe {
    public static void main(String[] args) {
Computer computer=new Computer();
Up up=new Up();
Printer printer=new Printer();

computer.transferData(up);
        System.out.println("*****************上传文件");
        computer.transferData(printer);

    }

}

class Computer {
    public void transferData(USB usb) {
        usb.start();
        System.out.println("正在传输数据");
        usb.stop();

    }
}

interface USB {
    void start();

    void stop();
}

class Up implements USB {

    @Override
    public void start() {
        System.out.println("U盘开启");
    }

    @Override
    public void stop() {
        System.out.println("U盘关闭");
    }
}

class Printer implements USB {

    @Override
    public void start() {
        System.out.println("打印机工作");
    }

    @Override
    public void stop() {
        System.out.println("打印机关闭");
    }
}
