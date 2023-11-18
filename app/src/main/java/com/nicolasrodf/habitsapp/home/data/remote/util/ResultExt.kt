package com.nicolasrodf.habitsapp.home.data.remote.util

import kotlinx.coroutines.CancellationException

// https://www.droidcon.com/2022/04/06/resilient-use-cases-with-kotlin-result-coroutines-and-annotations/

//Esta funcion utiliza un "lambda con receiver" el cual es equivalente a una funcion de extension
//de una lambda. El cual permite retornar la misma variable que se le pasa a la lambda
//Lo que hara la funcion sera hacer un try/catch sobre lo que este dentro de la lambda.
//Retornara un success o un failure el cual podremos utilizar a nuestra conveniencia
inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}

//Es equivalente a la esta funcion mas simplificada:
//Esta no permite retornar lo que se le pasa a la lambda
inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}