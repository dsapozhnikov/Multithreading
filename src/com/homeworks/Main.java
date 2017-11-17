package com.homeworks;

import java.io.*;

public class Main {
    static Object obj = new Object();
    static char currentLetter = 'A';
    static Object monitor = new Object();
    static File file = new File("1.txt");
    static BufferedWriter fw;

    public static  void writeToFile(String thread) {

        try{
            fw = new BufferedWriter(new FileWriter(file));
          //  synchronized (monitor) {
            for (int i = 0; i <10 ; i++) {


                    fw.write(thread + "\n");
                    fw.flush();
                    Thread.sleep(2000);

              //  }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        /* printing three letters 5 times in ABC order by three threads, synchronizing them

        by using obj monitor */

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (obj) {
                        while (currentLetter != 'A') {
                            obj.wait();
                        }
                        System.out.print('A');
                        currentLetter = 'B';
                        obj.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (obj) {
                        while (currentLetter != 'B') {
                            obj.wait();
                        }
                        System.out.print('B');
                        currentLetter = 'C';
                        obj.notifyAll();

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (obj) {
                        while (currentLetter != 'C') {
                            obj.wait();
                        }
                        System.out.print('C');
                        currentLetter = 'A';
                        obj.notifyAll();

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        task2();


    }

    public static void task2() {


        new Thread(() -> {                // 1

                Main.writeToFile("thread1");

        }).start();


        new Thread(() -> {           // 2 thread

                Main.writeToFile("thread2");

        }).start();


        new Thread(() -> {           // 3 thread

                Main.writeToFile("thread3");


        }).start();


    }
}
 class MFU {

    /* Task 3
    we need to print and scan two documents
    condition: both docs cannot be printed or scanned simultaniously but they can be printed AND scanned simultaniously
    */

    Object print = new Object();
    Object scan = new Object();

    public  void printAndScan(int countOfPages,int countOfCannedPages,String thread) {

        synchronized (print) {
            if (thread.equals("first")) {

                System.out.println();
                System.out.print("\n"+thread+" "+"thread is "+"printing... ");
            }
            else if (thread.equals("second")) {
                System.out.println();
                System.out.print(thread + " "+"thread is " + "printing... ");
            }
            int count = 0;
            for (int i = 0; i <countOfPages; i++) {
                try {
                    Thread.sleep(500);
                    count++;
                    System.out.print(" "+count);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println();
        }



        synchronized (scan)  {
            if (thread.equals("first")) {

                System.out.println();
                System.out.print("\n"+thread+" "+"thread is "+"scanning... ");
            }
            else if (thread.equals("second")) {
                System.out.println();
                System.out.print(thread + " "+"thread is " + "scanning... ");
            }
            int count = 0;

            for (int i = 0; i <countOfCannedPages ; i++) {
                try {
                    Thread.sleep(500);
                    count++  ;
                    System.out.print(" "+count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println();
        }


    }
}
 class Main2 {


    public static void main(String[] args) {
        MFU doc1 = new MFU();

        Thread t = new Thread(()-> {
          // System.out.println("Thread1 is started");
            doc1.printAndScan(3,3,"first");

        });

        Thread t1 =  new Thread(()-> {
            System.out.println("Thread2 is started");
            doc1.printAndScan(6,6,"second");

        });
        t.start();
        t1.start();


    }
}



