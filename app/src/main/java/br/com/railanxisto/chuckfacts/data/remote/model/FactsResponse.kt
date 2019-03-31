package br.com.railanxisto.chuckfacts.data.remote.model

import br.com.railanxisto.chuckfacts.domain.Fact

data class FactsResponse(
    val total: Int,
    val result: List<Fact>
)