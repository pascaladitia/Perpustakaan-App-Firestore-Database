package com.examplel.perpustakaanapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Buku (
    var id: String? = null,
    var judul: String? = null,
    var author: String? = null,
    var tgl: String? = null,
    var desk: String? = null
) : Parcelable {
    constructor(): this("", "", "", "", "")
}