package br.com.railanxisto.chuckfacts.domain

data class Fact(
    val id: String,
    val url: String,
    val value: String,
    val category: String?,
    val icon_url: String
)