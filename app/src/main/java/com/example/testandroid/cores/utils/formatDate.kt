package com.example.testandroid.cores.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class DateFormat(val pattern: String) {
    // --- Date Formats ---
    DATE_1("yyyy-MM-dd"),
    DATE_2("dd/MM/yyyy"),
    DATE_3("yyyy-MM-dd hh:mm:ss"),
    DATE_4("yyyy-MM-dd HH:mm:ss"),
    DATE_5("dd MMM yyyy"),
    DATE_6("EEEE, dd MMMM yyyy"),
    DATE_7("EEE"),
    DATE_8("dd-MM-yyyy"),
    DATE_9("dd-MMM-yy"),
    DATE_10("dd MMM"),
    DATE_11("MM/dd/yyyy"),
    DATE_12("dd-MMM-yyyy"),
    DATE_SORT("yyyyMMdd"),

    // --- Month Formats ---
    MONTH_1("MM / yyyy"),
    MONTH_2("MM"),
    MONTH_3("MMMM"),
    MONTH_4("MMMM yyyy"),

    // --- Year Formats ---
    YEAR_1("yyyy"),

    // --- Time Formats ---
    TIME_1("hh:mm a"),
    TIME_2("HH:mm"),
    TIME_3("hh:mm:ss"),
    TIME_4("HH:mm:ss"),
    TIME_5("hh:mm a"),
    TIME_6("hh:mm"),
    TIME_7("dd MMMM yyyy hh:mm:ss a"),
    TIME_8("dd/MM/yyyy hh:mm a"),
    TIME_9("dd MMMM yyyy hh:mm a"),
    TIME_10("dd-MMM-yyyy hh:mm a"),
    TIME_11("yyyyMMddHHmmss"),
}

object DateUtils {
    fun format(date: String, inputFormat: DateFormat, outputFormat: DateFormat): String? {
        return try {
            val inputFormatter =
                DateTimeFormatter.ofPattern(inputFormat.pattern, Locale.getDefault())
            val temporal = parseDateTime(date, inputFormatter)
                ?: parseDate(date, inputFormatter)
                ?: return null

            val outputFormatter =
                DateTimeFormatter.ofPattern(outputFormat.pattern, Locale.getDefault())
            outputFormatter.format(temporal)
        } catch (e: Exception) {
            null
        }
    }

    private fun parseDate(date: String, formatter: DateTimeFormatter): LocalDate? {
        return try {
            LocalDate.parse(date, formatter)
        } catch (e: Exception) {
            null
        }
    }

    private fun parseDateTime(date: String, formatter: DateTimeFormatter): LocalDateTime? {
        return try {
            LocalDateTime.parse(date, formatter)
        } catch (e: Exception) {
            null
        }
    }
}