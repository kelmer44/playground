package com.example.app_annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a ViewModel as Activity Scoped in order for annotation processor to generate a Dagger module for it.
 * Classes annotated with this must extend androidx.lifecycle.ViewModel.
 *
 * <p>If the annotated ViewModel has more than one constructor, then there must be one (and only one) annotated with
 * [ViewModelConstructor]</p>
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ActivityViewModel {

    /**
     * Enables SavedStateHandle injection on ViewModels. Under the hood it will use an
     * AbstractSavedSateViewModelFactory to produce the ViewModel instances.
     *
     * <p>Note that this means the ViewModel creation has to happen</p>
     *
     * <p>a) in the main thread</p>
     * <p>b) after the Activity's onCreate</p>
     *
     * <p>Therefore, injection of the ViewModel on the Activity must be wrapped with dagger.Lazy.</p>
     *
     * <p>Injecting the ViewModel in any other place is discouraged as control over the ViewModel creation might be lost
     * and produce a runtime exception.</p>
     *
     * @return whether to opt in for the feature or not.
     */
    boolean optInSavedSateViewModel() default false;

    /**
     * Specifies an interface or parent class that the binding will be created for.
     *
     * <p>For example if you have an interface IViewModel and your implementation ViewModelImpl, but you need to inject
     * objects of type IViewModel because modules might not include the dependency where ViewModelImpl is included.</p>
     *
     * <p>Note that this will replace the binding so ViewModelImpl will not be available on the dagger graph if a
     * parent class is specified.</p>
     *
     * @return the class
     */
    Class<?> parentClass() default DefaultParentClass.class;
}
