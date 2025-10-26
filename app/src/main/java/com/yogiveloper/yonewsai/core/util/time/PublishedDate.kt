package com.yogiveloper.yonewsai.core.util.time

import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Parses an ISO 8601 timestamp (e.g., 2025-10-25T10:30:00Z) and extracts only the date part.
 *
 * @param publishedAt The ISO 8601 date string.
 * @return The date part (YYYY-MM-DD), or the original string if parsing fails.
 */
fun formatPublishedDate(publishedAt: String): String {
    return try {
        val parts = publishedAt.split("T")
        if (parts.isNotEmpty()) parts[0] else publishedAt
    } catch (e: Exception) {
        publishedAt
    }
}

// --- Time Ago Constants ---
private const val MINUTE_IN_SECONDS = 60L
private const val HOUR_IN_SECONDS = 3600L
private const val DAY_IN_SECONDS = 86400L
private const val WEEK_IN_SECONDS = 604800L
private const val MONTH_IN_SECONDS = 2592000L // Approx 30 days
private const val YEAR_IN_SECONDS = 31536000L // Approx 365 days

/**
 * Calculates the time difference between the published date and the current time,
 * returning a human-readable "time ago" string.
 *
 * This implementation uses SimpleDateFormat and Date, making it compatible with
 * all Android API levels (pre-API 26).
 *
 * @param publishedAt The ISO 8601 date string (e.g., 2025-10-25T10:30:00Z).
 * @return A formatted string like "5 minutes ago" or "Just now".
 */
fun getTimeAgo(publishedAt: String): String {
    return try {
        // Pattern for ISO 8601 format like "YYYY-MM-DDThh:mm:ssZ"
        // 'Z' indicates UTC, so we must set the timezone explicitly.
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }

        val publishedDate: Date = format.parse(publishedAt) ?: return "Invalid time format"

        val now = Date()
        val diffMillis = now.time - publishedDate.time

        // Handle future dates (diffMillis will be negative)
        if (diffMillis < 0) return "Just now"

        // Convert milliseconds to seconds
        val seconds = diffMillis / 1000

        return when {
            seconds < MINUTE_IN_SECONDS -> {
                if (seconds < 5) "Just now" else "$seconds seconds ago"
            }

            seconds < HOUR_IN_SECONDS -> {
                val minutes = seconds / MINUTE_IN_SECONDS
                if (minutes == 1L) "1 minute ago" else "$minutes minutes ago"
            }

            seconds < DAY_IN_SECONDS -> {
                val hours = seconds / HOUR_IN_SECONDS
                if (hours == 1L) "1 hour ago" else "$hours hours ago"
            }

            seconds < WEEK_IN_SECONDS -> {
                val days = seconds / DAY_IN_SECONDS
                if (days == 1L) "1 day ago" else "$days days ago"
            }

            seconds < MONTH_IN_SECONDS -> {
                val weeks = seconds / WEEK_IN_SECONDS
                if (weeks == 1L) "1 week ago" else "$weeks weeks ago"
            }

            seconds < YEAR_IN_SECONDS -> {
                val months = seconds / MONTH_IN_SECONDS
                if (months == 1L) "1 month ago" else "$months months ago"
            }

            else -> {
                val years = seconds / YEAR_IN_SECONDS
                if (years == 1L) "1 year ago" else "$years years ago"
            }
        }
    } catch (e: Exception) {
        // Catches ParseException and other runtime issues
        println("Error processing date '$publishedAt': ${e.message}")
        "Unknown time"
    }
}
