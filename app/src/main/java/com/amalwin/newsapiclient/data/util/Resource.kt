package com.amalwin.newsapiclient.data.util

sealed class Resource<T> constructor
    (val data: T? = null, val message: String? = null) {
    class Success<T> constructor(data: T) : Resource<T>(data)
    class Loading<T> constructor(data: T? = null, message: String? = null) :
        Resource<T>(data, message)
    class Error<T> constructor(data: T? = null, message: String? = null) :
        Resource<T>(data, message)
}