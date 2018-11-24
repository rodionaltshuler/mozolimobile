package com.ottamotta.mozoli

import android.view.View

infix fun View.onClick(onClickListener: (View) -> Unit) {
    this.setOnClickListener(onClickListener)
}