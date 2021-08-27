package com.examplel.perpustakaanapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Perpustakaan (
    var id: String? = null,
    var judul: String? = null,
    var peminjam: String? = null,
    var tgl: String? = null,
    var desk: String? = null
) : Parcelable {
    constructor(): this("", "", "", "", "")
}