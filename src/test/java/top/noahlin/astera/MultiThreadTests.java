package top.noahlin.astera;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadTests {
    public static void main(String[] args) {
//        testThread(2);
//        testSynchronize();
//        testBlockingQueue();
//        testThreadLocal();
//        testExecutor(2);
//        testAtomic();
        testFuture();
    }

    /**
     * 多线程
     */
    public static void testThread(int testNumber) {
        if (testNumber == 1){
            for (int i = 0; i < 10; ++i) {
                new HelloThread(i).start();
            }
        }
        if (testNumber == 2) {
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

    /**
     * synchronized锁
     */
    public static void testSynchronize() {
//        synchronize1();
        synchronize2();
        synchronize1();
    }

    public static final Object obj = new Object();

    public static void synchronize1() {
        synchronized (obj) {
            try {
                for (int i = 0; i < 10; ++i) {
                    Thread.sleep(1000);
                    System.out.printf("T3: %d%n", i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void synchronize2() {
        synchronized (obj) {
            try {
                for (int i = 0; i < 10; ++i) {
                    Thread.sleep(1000);
                    System.out.printf("T4: %d%n", i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * BlockingQueue阻塞队列 异步
     */
    public static void testBlockingQueue() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(queue), "Producer").start();
        new Thread(new Consumer(queue), "Consumer1").start();
        new Thread(new Consumer(queue), "Consumer2").start();
    }

    /**
     * ThreadLocal 线程局部变量
     */
    private static final ThreadLocal<Integer> threadLocalUserIds = new ThreadLocal<>();
    private static int userId;

    public static void testThreadLocal() {
        for (int i = 0; i < 10; ++i) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        threadLocalUserIds.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocalUserId: " + threadLocalUserIds.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 10; ++i) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        userId = finalI;
                        Thread.sleep(2000);
                        System.out.println("userId: " + userId);
//                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Executor
     */
    public static void testExecutor(int testNumber) {
        if (testNumber == 1) {
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; ++i) {
                        try {
                            Thread.sleep(1000);
                            System.out.println("Executor1: " + i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        if (testNumber == 2) {
            ExecutorService service = Executors.newFixedThreadPool(2);
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; ++i) {
                        try {
                            Thread.sleep(1000);
                            System.out.println("Executor1: " + i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; ++i) {
                        try {
                            Thread.sleep(1000);
                            System.out.println("Executor2:" + i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            service.shutdown();

            while (!service.isTerminated()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Wait for termination.");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Atomic
     */
    public static void testAtomic() {
//        testWithoutAtomic();
        testWithAtomic();
    }

    private static int counter = 0;
    private static AtomicInteger atomicInteger =new AtomicInteger(0);

    private static void testWithoutAtomic() {
        for (int i = 0; i < 10; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        for (int j = 0; j < 10; j++){
                            ++counter;
                            System.out.println(counter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static void testWithAtomic() {
        for (int i = 0; i < 10; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        for (int j = 0; j < 10; j++){
                            System.out.println(atomicInteger.incrementAndGet());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Future
     */
    public static void testFuture() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 1;
//                throw new IllegalArgumentException("异常");
            }
        });

        service.shutdown();
        try {
//            System.out.println(future.get(500, TimeUnit.MILLISECONDS));
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
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

class Consumer implements Runnable {
    private final BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ": " + queue.take());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable {
    private final BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; ++i) {
                Thread.sleep(500);
                queue.put(String.valueOf(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}