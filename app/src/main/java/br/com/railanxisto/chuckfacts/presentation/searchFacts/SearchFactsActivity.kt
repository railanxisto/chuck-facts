package br.com.railanxisto.chuckfacts.presentation.searchFacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.railanxisto.chuckfacts.R
import androidx.lifecycle.Observer
import br.com.railanxisto.chuckfacts.presentation.common.BaseActivity
import org.koin.android.ext.android.inject

class SearchFactsActivity : BaseActivity() {

    val viewModel: SearchFactsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_facts)

        viewModel.getCategories()

        initializeObservers()
    }

    private fun initializeObservers() {
        viewModel.getCategoriesList().observe(this, Observer {
            println("aqui " + it)
        })
    }
}
