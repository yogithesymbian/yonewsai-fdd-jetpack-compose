package com.yogiveloper.yonewsai.core.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Extension function to log all emissions from a StateFlow.
 * Useful for debugging UI state changes in ViewModels.
 */
fun <T> StateFlow<T>.logChanges(
    tag: String,
    scope: CoroutineScope
) {
    scope.launch {
        this@logChanges.collect {
            Log.d(tag, "UI state updated: $it")
        }
    }
}
