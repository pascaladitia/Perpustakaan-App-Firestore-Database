package com.examplel.perpustakaanapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var status: String? = null,
    var email: String? = null,
    var password: String? = null,
    var key: String? = null
): Parcelable
{
    constructor(): this("", "", "", "")
}