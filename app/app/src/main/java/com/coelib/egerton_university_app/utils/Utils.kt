package com.coelib.egerton_university_app.utils

sealed class Utils<T> (val data: T? = null, val message: String? = null){
    class Success<T>(data: T) : Utils<T>(data)
    class Error<T>(message: String?, data: T? = null) : Utils<T>(data, message)
    class Loading<T> : Utils<T>()
}