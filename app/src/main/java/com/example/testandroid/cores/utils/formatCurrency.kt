package com.example.testandroid.cores.utils

import java.text.NumberFormat
import java.util.Locale

enum class CurrencyType(val symbol: String) {
    USD("$"),
    KHR("៛")
}

object CurrencyUtils {
    fun format(
        amount: Double,
        currency: CurrencyType,
        toRate: Double? = null
    ): String {
        val converted = if (toRate != null) amount * toRate else amount

        val formatted = when (currency) {
            CurrencyType.USD -> NumberFormat.getNumberInstance(Locale.US).apply {
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }.format(converted)

            CurrencyType.KHR -> NumberFormat.getNumberInstance(Locale.US).apply {
                minimumFractionDigits = 0
                maximumFractionDigits = 0
            }.format(converted)
        }

        return when (currency) {
            CurrencyType.USD -> "${currency.symbol}${formatted}"
            CurrencyType.KHR -> "${formatted}${currency.symbol}"
        }
    }
}