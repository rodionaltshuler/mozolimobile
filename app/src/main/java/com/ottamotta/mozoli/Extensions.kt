package com.ottamotta.mozoli

import android.view.View

infix fun View.onClick(onClickListener: (View) -> Unit) {
    this.setOnClickListener(onClickListener)
}

infix fun View.visibleIf(condition : Boolean) {
    visibility = when (condition) {
        true -> View.VISIBLE
        else -> View.GONE
    }
}