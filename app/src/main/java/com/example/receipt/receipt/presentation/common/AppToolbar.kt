package com.example.receipt.receipt.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.receipt.receipt.R
import kotlinx.android.synthetic.main.widget_toolbar_app.view.*

class AppToolbar : Toolbar {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, R.attr.toolbarStyle)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.widget_toolbar_app, this, true)
        background = context?.resources?.getDrawable(R.drawable.background_toolbar, context.theme)
    }

    fun setTitle(title: String?) {
        background = context?.resources?.getDrawable(R.drawable.background_toolbar, context.theme)
        logoImageView.visibility = View.GONE
        titleTextView.text = title ?: ""
        titleTextView.visibility = View.VISIBLE
    }

}
