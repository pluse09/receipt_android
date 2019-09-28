package com.example.receipt.receipt.presentation.scan

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_product.view.*


class ScanResultRecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val nameTextView: TextView = view.nameTextView

    val priceTextView: TextView = view.priceTextView

}