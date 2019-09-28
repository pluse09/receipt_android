package com.example.receipt.receipt.presentation.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.receipt.receipt.R
import com.example.receipt.receipt.domain.Products

class ScanResultRecyclerViewAdapter(products: Products) :
    RecyclerView.Adapter<ScanResultRecyclerViewHolder>() {

    var products: Products = products
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_product, parent, false)
        return ScanResultRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: ScanResultRecyclerViewHolder, position: Int) {
        val product = products[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price

    }

    fun removeProduct(index: Int) {
        notifyItemRemoved(index)
        products.removeAt(index)
        notifyItemRangeChanged(index, itemCount)
    }

}