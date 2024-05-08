package com.amine.katabankaccount.application.core.annotation;

import java.lang.annotation.*;

/**
 * The annotation may indicate a suggestion for a logical component name, in our case a domain class
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseCase {
}
