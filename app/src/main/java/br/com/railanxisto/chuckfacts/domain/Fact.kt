package br.com.railanxisto.chuckfacts.domain

import br.com.railanxisto.chuckfacts.presentation.utils.Constants

data class Fact(
    val id: String,
    val url: String,
    val value: String,
    val category: List<String>?,
    val icon_url: String
) {
    fun getCategoryText(): CharSequence? {
        category?.let {
            return category.first()
        }
        return "UNCATEGORIZED"
    }

    fun getTextSize(text: String): Float =
        if (text.length >= Constants.FACT_MAX_CHARS) {
            Constants.TEXT_SIZE_LOWER_MAX_CHARS
        } else {
            Constants.TEXT_SIZE_HIGHER_MAX_CHARS
        }
}