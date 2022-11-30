package com.example.zadanie.helpers

import android.text.TextUtils.isEmpty
import android.widget.EditText

object FormHelper {

    fun EditText.moveCursorToEnd() = setSelection(text.length)

}