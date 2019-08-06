package br.com.railanxisto.chuckfacts.domain

import org.junit.Assert
import org.junit.Test
import br.com.railanxisto.chuckfacts.presentation.utils.Constants
import domain.Fact

class FactsTest {
    @Test
    fun `should return first category of the list when category is not null`() {
        val fact = Fact(id = "1", category = listOf("Food", "DEV"), url = "www", icon_url = "www", value = "Fact")
        Assert.assertEquals("Food", fact.getCategoryText())
    }

    @Test
    fun `should return UNCATEGORIZED if category is null`() {
        val fact = Fact(id = "1", category = null, url = "www", icon_url = "www", value = "Fact")
        Assert.assertEquals("UNCATEGORIZED", fact.getCategoryText())
    }

    @Test
    fun `should return lower textSize when fact has less than 80 characters`() {
        var factString = "".padEnd(Constants.FACT_MAX_CHARS - 1, 'a')
        val fact = Fact(id = "1", category = null, url = "www", icon_url = "www", value = factString)
        Assert.assertEquals(Constants.TEXT_SIZE_HIGHER_MAX_CHARS, fact.getTextSize(fact.value))
    }

    @Test
    fun `should return bigger textSize when fact has more than 80 characters`() {
        var factString = "".padEnd(Constants.FACT_MAX_CHARS, 'a')
        val fact = Fact(id = "1", category = null, url = "www", icon_url = "www", value = factString)
        Assert.assertEquals(Constants.TEXT_SIZE_LOWER_MAX_CHARS, fact.getTextSize(fact.value))
    }
}
