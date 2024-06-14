package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.example.UvmContextSelector;

public class ServiceA {
    private static final Logger logger = LogManager.getLogger(ServiceA.class);

    public ServiceA() {

    }

    public void printLogs() {
//        UvmContextSelector.instance().setLoggingApp(Long.valueOf(1));
//        ThreadContext.put("appName", "App1");
        logger.debug("This is a debug message");
        logger.info("This is an info message");

        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                UvmContextSelector.instance().setLoggingApp(Long.valueOf(2));
//                ThreadContext.put("appName", "App2");
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + " - " + i);
                    try {
                        Thread.sleep(1000); // Sleep for 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                logger.debug("This is a debug message 2");
                logger.info("This is an info message 2");

                logger.warn("This is a warn message 2");
                logger.error("This is an error message 2");
                logger.fatal("This is a fatal message 2");

                ServiceB serviceB = new ServiceB();
                serviceB.printLogs();
            }
        });

        thread1.start();
    }
}
