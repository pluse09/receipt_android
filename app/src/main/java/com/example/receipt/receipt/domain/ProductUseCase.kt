package com.example.receipt.receipt.domain

import com.example.receipt.receipt.data.api.API
import com.example.receipt.receipt.domain.Products
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


// @maybe-later: Repositoryから作成
class ProductUseCase(private val api: API) {

    fun getImageContents(imageBase64: String): Single<Products> {
        return api.getImageContents(imageBase64)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}