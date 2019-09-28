package com.example.receipt.receipt.presentation.common

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable


open class RxFragment : Fragment() {

    val disposable = CompositeDisposable()

    val disposeBag: CompositeDisposable get() = disposable

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}