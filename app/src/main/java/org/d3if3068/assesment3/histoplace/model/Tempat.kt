package org.d3if3068.assesment3.histoplace.model

data class Tempat(
    val image_id: String,
    val nama_tempat: String,
    val photos: List<String>?,
    val biaya_masuk: String,
    val kota: String,
    val negara: String,
    val alamat: String,
    val map_Url: String = "",
    val rating: Int,
    val catatan: String,
)

