package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UvmContextSelector;

public class ServiceC {

    private static final Logger logger = LogManager.getLogger(ServiceC.class);
    
    public ServiceC() {
    }

    public void printLogs() {
        logger.debug("This is a debug service C");
        logger.info("This is an info service C");

        logger.warn("This is a warn service C");
        logger.error("This is an error service C");
        logger.fatal("This is a fatal service C");


        UvmContextSelector.instance().setLoggingApp(Long.valueOf(2));

        logger.debug("This is a debug service C");
        logger.info("This is an info service C");

        logger.warn("This is a warn service C");
        logger.error("This is an error service C");
        logger.fatal("This is a fatal service C");
//        Logger localLogger = LogManager.getLogger(this.getClass());
//        localLogger.debug("This is a debug service C");
//        localLogger.info("This is an info service C");
//
//        localLogger.warn("This is a warn service C");
//        localLogger.error("This is an error service C");
//        localLogger.fatal("This is a fatal service C");
    }
}
