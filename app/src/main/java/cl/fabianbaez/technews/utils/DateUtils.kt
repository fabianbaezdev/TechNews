package cl.fabianbaez.technews.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

class DateUtils {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getDifference(time: String): String {
            val now: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val givenTime = Calendar.getInstance()
                givenTime.time = sdf.parse(time)

                val differenceInMillis = now.timeInMillis - givenTime.timeInMillis

                val days = differenceInMillis / (1000 * 60 * 60 * 24)
                if (days > 1)
                    return "$days days ago"
                if (days > 0)
                    return "Yesterday"
                val hours = (differenceInMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                if (hours > 0)
                    return "${hours}h"
                val minutes = (differenceInMillis % (1000 * 60 * 60)) / (1000 * 60)
                if (minutes > 0)
                    return "${minutes}m"
                else return "1m"
            } catch (e: Exception) {
                e.printStackTrace()
                return "unknown"
            }
        }
    }
}