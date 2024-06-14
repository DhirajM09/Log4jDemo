package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.example.UvmContextSelector;

public class ServiceB {
    private static final Logger logger = LogManager.getLogger(ServiceB.class);

    public ServiceB() {
    }

    public  void printLogs() {
        //        UvmContextSelector.instance().setLoggingApp(Long.valueOf(2));
//        ThreadContext.put("appName", "App2");
        logger.debug("This is a debug message");
        logger.info("This is an info message");

        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                UvmContextSelector.instance().setLoggingApp(Long.valueOf(1));
//                ThreadContext.put("appName", "App1");
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + " - " + i);
                    try {
                        Thread.sleep(1000); // Sleep for 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                logger.debug("This is a debug message 1");
                logger.info("This is an info message 1");

                logger.warn("This is a warn message 1");
                logger.error("This is an error message 1");
                logger.fatal("This is a fatal message 1");
            }
        });

        thread1.start();
    }
}
