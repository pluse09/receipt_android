package com.example.receipt.receipt.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.receipt.receipt.R
import kotlinx.android.synthetic.main.view_state.view.*

class StateView : ConstraintLayout {

    init {
        View.inflate(context, R.layout.view_state, this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun showState() {
        visibility = View.VISIBLE
        setProgressGroupVisibility(View.GONE)
        setStateGroupVisibility(View.VISIBLE)
    }

    fun hideLoading() {
        setProgressGroupVisibility(View.GONE)
        visibility = View.GONE
    }

    fun showLoading() {
        showLoading(false)
    }

    private fun showLoading(isShowMessage: Boolean) {
        visibility = View.VISIBLE
        setStateGroupVisibility(View.GONE)

        progressMessageTextView.visibility = if (isShowMessage) {
            View.VISIBLE
        } else {
            View.GONE
        }
        setProgressGroupVisibility(View.VISIBLE)
    }

    fun set(title: String, message: String) {
        titleTextView.text = title
        messageTextView.text = message
    }


    private fun setProgressGroupVisibility(visibility: Int) {
        listOf(progressBar, progressMessageTextView)
            .forEach {
                it.visibility = visibility
            }
    }

    private fun setStateGroupVisibility(visibility: Int) {
        listOf(retryButton, messageTextView, titleTextView)
            .forEach {
                it.visibility = visibility
            }
    }

}