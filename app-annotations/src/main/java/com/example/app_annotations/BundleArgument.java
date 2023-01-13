package com.example.app_annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that a parameter is to be treated as a Bundle Argument, that is, to be automatically injected on the
 * viewModel by retrieving it from the passed in bundle.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface BundleArgument {

    /**
     * Name of the argument from the extras bundle.
     *
     * @return name
     */
    String name();
}
