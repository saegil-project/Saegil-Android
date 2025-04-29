package com.saegil.ui.util

import android.content.Context
import android.widget.Toast

fun SaegilToast(
    context: Context,
    text: String,
) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}