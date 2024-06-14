package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.example.service.ServiceA;
import org.example.service.ServiceB;
import org.example.service.ServiceC;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {

        LogManager.setFactory(new Log4jContextFactory(new UvmContextSelector()));

        logger.info("This is a console logger");

        // Set the context property to route the log
//        ThreadContext.put("appName", "App1");
//        UvmContextSelector.instance().setLoggingApp(Long.valueOf(1));
        logger.info("This is a log message for App1");

        // Change the context property to route the log
//        ThreadContext.put("appName", "App2");
//        UvmContextSelector.instance().setLoggingApp(Long.valueOf(2));
        System.setProperty("appName", "App2");
        logger.info("This is a log message for App2");

//        UvmContextSelector.instance().setLoggingApp(Long.valueOf(1));
        System.setProperty("appName", "App1");
        Configurator.reconfigure();
        ServiceA serviceA = new ServiceA();
        serviceA.printLogs();

//        UvmContextSelector.instance().setLoggingApp(Long.valueOf(2));
        System.setProperty("appName", "App2");
        Configurator.reconfigure();
        ServiceB serviceB = new ServiceB();
        serviceB.printLogs();

//        UvmContextSelector.instance().setLoggingApp(Long.valueOf(1));
        System.setProperty("appName", "App1");
        Configurator.reconfigure();
        ServiceC serviceC = new ServiceC();
        serviceC.printLogs();

        // Clear ThreadContext after use
//        ThreadContext.clearAll();
    }
}