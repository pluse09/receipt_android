package com.example.receipt.receipt.data.api

import com.example.receipt.receipt.domain.Products
import io.reactivex.Single
import retrofit2.http.*

interface API {

    @Headers("Content-Type: application/json")
    @POST("/api/classify")
    fun getImageContents(
        @Body image_base64: String
    ): Single<Products>

}