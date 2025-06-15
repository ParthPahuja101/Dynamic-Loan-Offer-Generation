package com.cred.loan.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for application logging using SLF4J with Logback.
 * This class sets up various loggers and appenders for different components
 * of the loan offer generation system.
 */
@Configuration
public class LoggingConfig {

    /**
     * Creates a logger for the offer generation component.
     *
     * @return Configured logger
     */
    @Bean
    public Logger offerLogger() {
        LoggerContext context = new LoggerContext();
        Logger logger = context.getLogger("com.cred.loan.offers");
        logger.addAppender(createConsoleAppender(context));
        logger.addAppender(createFileAppender(context, "offers"));
        return logger;
    }

    /**
     * Creates a logger for the risk assessment component.
     *
     * @return Configured logger
     */
    @Bean
    public Logger riskLogger() {
        LoggerContext context = new LoggerContext();
        Logger logger = context.getLogger("com.cred.loan.risk");
        logger.addAppender(createConsoleAppender(context));
        logger.addAppender(createFileAppender(context, "risk"));
        return logger;
    }

    /**
     * Creates a logger for the behavior analysis component.
     *
     * @return Configured logger
     */
    @Bean
    public Logger behaviorLogger() {
        LoggerContext context = new LoggerContext();
        Logger logger = context.getLogger("com.cred.loan.behavior");
        logger.addAppender(createConsoleAppender(context));
        logger.addAppender(createFileAppender(context, "behavior"));
        return logger;
    }

    /**
     * Creates a console appender for logging to standard output.
     *
     * @param context The logger context
     * @return Configured console appender
     */
    private ConsoleAppender<ILoggingEvent> createConsoleAppender(LoggerContext context) {
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(context);
        appender.setName("CONSOLE");
        appender.start();
        return appender;
    }

    /**
     * Creates a file appender for logging to files with rolling policy.
     *
     * @param context The logger context
     * @param component The component name for the log file
     * @return Configured file appender
     */
    private RollingFileAppender<ILoggingEvent> createFileAppender(LoggerContext context, String component) {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(context);
        appender.setName("FILE");
        appender.setFile("logs/" + component + ".log");

        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setFileNamePattern("logs/" + component + ".%d{yyyy-MM-dd}.log");
        rollingPolicy.setMaxHistory(30);
        rollingPolicy.setParent(appender);
        rollingPolicy.start();

        appender.setRollingPolicy(rollingPolicy);
        appender.start();
        return appender;
    }
} 