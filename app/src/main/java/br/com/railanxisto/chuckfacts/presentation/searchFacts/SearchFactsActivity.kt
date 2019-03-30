package br.com.railanxisto.chuckfacts.presentation.searchFacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.railanxisto.chuckfacts.R
import org.koin.android.ext.android.inject

class SearchFactsActivity : AppCompatActivity() {

    val viewModel: SearchFactsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_facts)

        viewModel.getCategories()
    }
}
