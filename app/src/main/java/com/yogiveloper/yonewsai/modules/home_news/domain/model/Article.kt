package com.yogiveloper.yonewsai.modules.home_news.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val sourceName: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable
/*
* TL;DR
* : Parcelable : props/allow for intent passing/menerima/mengambil
* (seperti String, Int, atau Parcelable)
* // continue Explain of Parcelable check on #explain_01
*
* @Parcelize concise code nya dari Java to Kotlin
* tidak perlu mengoverride / declaration yang panjang
* getter setter dan fungsi terkait lainya dalam context intent
*
* Bayangkan Anda ingin mengirim sebuah kado
* (objek Article) dari satu teman
* (Activity atau Composable pertama)
* ke teman lain (Activity atau Composable kedua).
* Anda tidak bisa begitu saja "melemparkan" kado itu.
* Anda harus mengepaknya ke dalam sebuah kotak (Parcel)
* agar bisa dikirim oleh kurir (Sistem Operasi Android).
* */