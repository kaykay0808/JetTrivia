package com.example.jettrivia.data

// DataOrException<T, Boolean, E:Exception>
// in this case T is a generic which mean we can pass in any type we want. It could be any template
// The Boolean we can use to toggle if loading is true or false
// This may be overkill, but it is a part of understanding the clean architecture.

data class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)