package com.qaraniraka.myapplication.model

data class Foobar(
    val id: Int,
    val column1: String,
    val column2: String,
    val column3: String
)

data class FoobarList(
    val data: List<Foobar>,
    val totalRecordsCount: Int
)

data class FoobarPostData(
    val column1: String,
    val column2: String,
)