package br.com.railanxisto.chuckfacts.data.remote.model

import domain.Fact

data class FactsResponse(
    val total: Int,
    val result: List<Fact>
)