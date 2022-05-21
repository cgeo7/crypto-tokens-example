package com.example.cryptotokens.config;

import static java.util.Optional.ofNullable;

import com.example.cryptotokens.domain.token.TokenService;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    private final TokenService tokenService;

    private final static int MIN_INTERVAL = 1;
    private final static int MAX_INTERVAL = 5;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(tokenService::updateToken, createTrigger());
    }

    private Trigger createTrigger() {
        return context -> {
            Optional<Date> lastCompletionTime = ofNullable(context.lastCompletionTime());
            int interval = ThreadLocalRandom.current().nextInt(MIN_INTERVAL, MAX_INTERVAL) * 1000;
            Instant nextExecutionTime = lastCompletionTime.orElseGet(Date::new)
                .toInstant()
                .plusMillis(interval);
            return Date.from(nextExecutionTime);
        };
    }

}