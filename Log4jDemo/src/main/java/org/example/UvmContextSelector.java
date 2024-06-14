package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.selector.BasicContextSelector;
import org.apache.logging.log4j.core.selector.ContextSelector;
import org.apache.logging.log4j.core.util.Loader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UvmContextSelector implements ContextSelector {

    private final Map<String, LoggerContext> contexts = new ConcurrentHashMap<>();
    public static final String DEFAULT_LOG = "uvm";
    private static final UvmContextSelector INSTANCE;

    static {
        INSTANCE = new UvmContextSelector();
    }

    public static UvmContextSelector instance() {
        return INSTANCE;
    }

    @Override
    public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {

        String contextName = ThreadContext.get("appName");
        if (contextName == null) {
            contextName = "default";
        }

        return contexts.computeIfAbsent(contextName, key -> {
            LoggerContext context = new LoggerContext(key);
            URI configLocation = null;
            try {
                configLocation = Loader.getResource("log4j2.xml", loader).toURI();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            context.setConfigLocation(configLocation);
            return context;
        });
    }

    @Override
    public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
        return getContext(fqcn, loader, currentContext);
    }

    @Override
    public List<LoggerContext> getLoggerContexts() {
        Collection<LoggerContext> values = contexts.values();
        return new ArrayList<>(values);
    }

    @Override
    public void removeContext(LoggerContext context) {
        LoggerContext remove = contexts.remove(context.getName());
    }

    public void setLoggingApp(Long appId) {
        ThreadContext.put("appName", "App" + appId.toString());
    }

    public void setLoggingUvm() {

    }

    public void reconfigureAll() {

    }
}
