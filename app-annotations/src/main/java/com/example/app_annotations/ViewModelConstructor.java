package com.example.app_annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * For ViewModels with multiple constructors, marks the one to be used for autogenerated code.
 *
 * <p>Can only be used within classes annotated with [ActivityViewModel] or [FragmentViewModel]</p>
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ViewModelConstructor {
}
