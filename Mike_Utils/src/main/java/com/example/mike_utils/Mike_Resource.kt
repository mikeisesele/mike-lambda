package com.example.mike_utils


/**
 * Resource handler to work with various network state.
 */

sealed class MikeResource<T>(){
    data class Success<T>(val data: T) : MikeResource<T>()
    data class Error<T>(val message: String) : MikeResource<T>()
    data class Loading<T>(val message: String) : MikeResource<T>()
    class Empty<T>() : MikeResource<T>()
}