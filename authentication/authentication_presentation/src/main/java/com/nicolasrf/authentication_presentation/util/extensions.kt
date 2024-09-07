package com.nicolasrf.authentication_presentation.util

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.lang.IllegalArgumentException

fun Throwable.toReadableError() : String =
    when(this){
        is IllegalArgumentException -> "Los campos estan vacios"
        is FirebaseAuthInvalidCredentialsException -> "Los datos estan mal formateados"
        is FirebaseAuthInvalidUserException -> "El usuario no existe"
        else -> { "Ocurrio un error" }
    }
