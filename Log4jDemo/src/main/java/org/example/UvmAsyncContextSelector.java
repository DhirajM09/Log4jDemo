package org.example;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.apache.logging.log4j.core.selector.BasicContextSelector;
import org.apache.logging.log4j.core.util.Loader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UvmAsyncContextSelector extends AsyncLoggerContextSelector {
    public static class UvmBasicContextSelector extends BasicContextSelector {
        private final Map<String, LoggerContext> contexts = new ConcurrentHashMap<>();
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
    }
}
