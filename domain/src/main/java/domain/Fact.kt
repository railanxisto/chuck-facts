package domain

data class Fact(
    val id: String,
    val url: String,
    val value: String,
    val category: List<String>?,
    val icon_url: String
) {

    companion object {
        const val FACT_MAX_CHARS = 80
        const val TEXT_SIZE_HIGHER_MAX_CHARS = 26f
        const val TEXT_SIZE_LOWER_MAX_CHARS = 18f
    }

    fun getCategoryText(): CharSequence? {
        category?.let {
            return category.first()
        }
        return "UNCATEGORIZED"
    }

    fun getTextSize(text: String): Float =
        if (text.length >= FACT_MAX_CHARS) {
            TEXT_SIZE_LOWER_MAX_CHARS
        } else {
            TEXT_SIZE_HIGHER_MAX_CHARS
        }
}