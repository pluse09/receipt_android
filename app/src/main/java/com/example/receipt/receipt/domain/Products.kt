package com.example.receipt.receipt.domain

import com.squareup.moshi.Json

data class Products(@Json(name = "image_classified") private val products: MutableList<Product>) {

    operator fun get(index: Int): Product {
        return products[index]
    }

    fun count(): Int {
        return products.count()
    }

    fun removeAt(index: Int) {
        products.removeAt(index)
    }

}