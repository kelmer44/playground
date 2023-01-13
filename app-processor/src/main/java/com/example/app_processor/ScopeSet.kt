package com.example.app_processor

import com.squareup.javapoet.ClassName

/**
 * Convenience class to host the parameters for the different scopes
 */
sealed class ScopeSet(
    val componentType: ClassName,
    val injectableClass: ClassName,
    val paramName: String
) {

    /**
     * Fragment scope data
     */
    object Fragment : ScopeSet(
        ClassName.get(
            "dagger.hilt.android.components", "FragmentComponent"
        ), ClassName.get(
            "androidx.fragment.app", "Fragment"
        ), "fragment"
    )

    /**
     * Activity scope data
     */
    object Activity : ScopeSet(
        ClassName.get(
            "dagger.hilt.android.components", "ActivityComponent"
        ), ClassName.get(
            "androidx.fragment.app", "FragmentActivity"
        ), "activity"
    )
}
