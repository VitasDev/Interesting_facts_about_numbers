package com.test.facts_of_numbers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersService {
    @GET("/{number}?json")
    fun getFactNumber(@Path("number") number: Int): Call<ResponseNumbers>

    @GET("/random/math?json")
    fun getRandomFact(): Call<ResponseNumbers>
}