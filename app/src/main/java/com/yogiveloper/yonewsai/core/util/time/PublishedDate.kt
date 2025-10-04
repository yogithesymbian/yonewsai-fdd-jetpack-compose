package com.yogiveloper.yonewsai.core.util.time

fun formatPublishedDate(publishedAt: String): String {
    return try {
        val parts = publishedAt.split("T")
        if (parts.isNotEmpty()) parts[0] else publishedAt
    } catch (e: Exception) {
        publishedAt
    }
}