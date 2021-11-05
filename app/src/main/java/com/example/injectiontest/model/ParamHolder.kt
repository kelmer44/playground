package com.example.injectiontest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParamHolder(val name: String) : Parcelable {
}
