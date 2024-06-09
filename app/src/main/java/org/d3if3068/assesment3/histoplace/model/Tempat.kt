package org.d3if3068.assesment3.histoplace.model

data class Tempat(
    val imageId: String,
    val namaTempat: String,
    val photos: List<String>,
    val biayaMasuk: String,
    val kota: String,
    val negara: String,
    val alamat: String,
    val mapUrl: String,
    val rating: Int,
    val catatan: String,
)

