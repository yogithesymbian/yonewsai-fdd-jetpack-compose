package com.yogiveloper.yonewsai.core.util

/*
* Deklarasi Sealed Class Generic
* sealed class adalah kelas "tersegel" atau "tertutup".
* Artinya, semua kelas turunan (subclass) yang bisa mewarisinya
* harus didefinisikan di dalam file yang sama (Resource.kt).
*
* Ini memberikan kekuatan super pada when expression di Kotlin.*/
sealed class Resource<out T> { /* out (Covariance): Keyword */
    data class Success<T>(val data: T) : Resource<T>() /* secara otomatis membuatkan fungsi seperti .equals(), .hashCode(), dan .toString(). */

    /*
    * Nothing adalah tipe spesial di Kotlin yang tidak memiliki nilai.
    * Artinya, state Error tidak akan pernah membawa data sukses.
    * Karena T dideklarasikan sebagai out T,
    * kita bisa menggunakan Nothing
    * (yang merupakan turunan dari semua tipe)
    * di sini. Ini memperkuat jaminan type-safety.
    *
    *  Stackoverflow: Type safety means that you use the types correctly and, therefore, avoid unsafe casts and unions.
    * simplenya : memory , data type & speed vs safety
    * */
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
