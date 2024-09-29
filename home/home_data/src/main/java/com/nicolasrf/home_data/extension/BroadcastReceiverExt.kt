package com.nicolasrf.home_data.extension

import android.content.BroadcastReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/*
* Da soporte de corutinas para el BroadcastReceiver
* para poder usar el repositorio que tiene metodos suspend
* */
//Referencia: https://stackoverflow.com/questions/74111692/run-coroutine-functions-on-broadcast-receiver
fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    CoroutineScope(SupervisorJob()).launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}