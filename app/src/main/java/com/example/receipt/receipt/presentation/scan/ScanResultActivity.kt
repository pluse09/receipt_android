package com.example.receipt.receipt.presentation.scan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.receipt.receipt.presentation.common.RxActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipt.receipt.R
import com.example.receipt.receipt.domain.ProductUseCase
import com.example.receipt.receipt.data.api.ApiProvider
import com.example.receipt.receipt.domain.Product
import com.example.receipt.receipt.domain.Products
import com.example.receipt.receipt.presentation.common.MarginItemDecoration
import io.reactivex.rxkotlin.addTo
import java.io.ByteArrayOutputStream
import java.io.File
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_result_scan.*

class ScanResultActivity : RxActivity() {

    // @TODO: SingleTonにしなければならない。
    private val api = ApiProvider.create()

    private val googleCloudVisinoUseCase: ProductUseCase =
        ProductUseCase(api)

    private lateinit var adapter: ScanResultRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_scan)

        initScanResultRecyclerView()
    }

    private fun initScanResultRecyclerView() {

        val layoutManager = LinearLayoutManager(productsRecyclerView.context)
        productsRecyclerView.layoutManager = layoutManager

        val padding = resources.getDimension(R.dimen.card_padding).toInt()
        productsRecyclerView.addItemDecoration(MarginItemDecoration(padding))

        adapter = ScanResultRecyclerViewAdapter(Products(mutableListOf<Product>()))
        productsRecyclerView.adapter = adapter

        setProducts()
    }

    private fun setProducts() {

        val takedImageUrl = Uri.parse(intent.getStringExtra("ImageUrl"))

        takedImageUrl?.let {
            val imageBase64 = convertBase64ToUri(it)
            showLoading()
            googleCloudVisinoUseCase.getImageContents(imageBase64)
                .subscribe({
                    adapter.products = it
                    setSwipe()
                    showContents()
                }, {

                    showError("読み込めませんでした。", "時間を空けてから再度お試しください。")
                    // @TODO: Timber
                    Log.e("ScanResultActicity: ", it.toString())
                }).addTo(disposable)
        }
    }


    // @TODO: Rename
    private fun convertBase64ToUri(imagePath: Uri): String {

        var file: File? = null
        val projection = arrayOf(MediaStore.MediaColumns.DATA)

        val cursor = contentResolver.query(imagePath, projection, null, null, null);
        if (cursor != null) {
            var path: String? = null
            if (cursor.moveToFirst()) {
                path = cursor.getString(0)
            }
            cursor.close()
            if (path != null) {
                file = File(path)
            }
        }
        val baos = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeFile(file?.path)
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos)
        val imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }

    private fun setSwipe() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.Callback() {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return makeMovementFlags(
                    0,
                    ItemTouchHelper.LEFT
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeProduct(viewHolder.adapterPosition)
            }

        })
        helper.attachToRecyclerView(productsRecyclerView)
    }


    private fun showLoading() {
        stateView.showLoading()
        contentGroup.visibility = View.INVISIBLE
    }

    private fun showContents() {
        stateView.hideLoading()
        contentGroup.visibility = View.VISIBLE
    }

    private fun showError(title: String, message: String) {
        stateView.hideLoading()
        contentGroup.visibility = View.INVISIBLE
        stateView.showState()
        stateView.set(title, message)
    }

}