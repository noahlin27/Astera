package top.noahlin.astera;

public class MultiThreadTests {
    public static void main(String[] args) {
//        testThread();
        testThread2();
    }

    public static void testThread() {
        for (int i = 0; i < 10; ++i) {
            new HelloThread(i).start();
        }
    }

    public static void testThread2() {
        for (int i = 0; i < 10; ++i) {
            int tid = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 10; ++i) {
                            Thread.sleep(1000);
                            System.out.printf("T2 %d: %d%n", tid, i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

class HelloThread extends Thread {
    private final int tid;

    public HelloThread(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; ++i) {
                Thread.sleep(1000);
                System.out.printf("%d: %d%n", tid, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
