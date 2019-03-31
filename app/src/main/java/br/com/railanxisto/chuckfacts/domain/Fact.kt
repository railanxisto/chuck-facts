package br.com.railanxisto.chuckfacts.domain

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
}