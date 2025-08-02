package com.api.certificado.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        int núcleos = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(núcleos);                    
        executor.setMaxPoolSize(núcleos * 2);                 
        executor.setQueueCapacity(núcleos * 50);             
        executor.setThreadNamePrefix("Async-");  
        executor.initialize();
        return executor;
    }
}

