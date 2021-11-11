package com.example.injectiontest.util

import android.view.View
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Update a [Flow] "content"
 * Remove the previous views to the flow parent, and to the [Flow] references
 * Add the new views to the flow parent, and to the [Flow] references
 *
 * @param views: new views to add
 * @param previousViews: old views to remove
 */
fun Flow.updateViews(views: List<View>, previousViews: List<View>) {
    val root = (parent as? ConstraintLayout)
    previousViews.forEach {
        root?.removeView(it)
        removeView(it)
    }
    views.forEach {
        root?.addView(it)
        addView(it)
    }
}

/**
 * Apply [action] for each view in [Flow] referenceIds.
 */
fun Flow.forEachView(ignoreIds: List<Int>, action: (View) -> Unit) {
    referencedIds.forEach { id ->
        rootView?.findViewById<View>(id).takeUnless { ignoreIds.contains(id) }?.let { view -> action(view) }
    }
}
