package com.halcyonmobile.rsrvd.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(wait: Long, scope: CoroutineScope, execute: (T) -> Unit): (T) -> Unit {
    var debounceJob: Job? = null

    return { parameter ->
        // Cancel the active job
        debounceJob?.cancel()

        // Reassign the job
        debounceJob = scope.launch {
            delay(wait)
            execute(parameter)
        }
    }
}