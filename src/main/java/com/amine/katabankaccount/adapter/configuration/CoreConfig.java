package com.amine.katabankaccount.adapter.configuration;

import com.amine.katabankaccount.application.core.annotation.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Scan all the "UseCase" annotations from the base package below to load them as beans in the application context
 */
@Configuration
@ComponentScan(basePackages = "com.amine.katabankaccount.application.core.service",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = UseCase.class))
public class CoreConfig {

}
