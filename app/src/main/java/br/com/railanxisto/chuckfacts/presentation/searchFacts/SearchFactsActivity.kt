package br.com.railanxisto.chuckfacts.presentation.searchFacts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.railanxisto.chuckfacts.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.railanxisto.chuckfacts.domain.Category
import br.com.railanxisto.chuckfacts.presentation.common.BaseActivity
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_search_facts.*
import org.koin.android.ext.android.inject

const val MAX_CATEGORIES = 8

class SearchFactsActivity : BaseActivity(), CategoriesAdapter.OnItemAdapterClickListener {

    companion object {
        const val RESULT_TERM = "term"
    }

    val viewModel: SearchFactsViewModel by inject()
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_facts)

        viewModel.getCategories()

        initializeObservers()
    }

    private fun initializeObservers() {
        viewModel.getCategoriesList().observe(this, Observer {
            setRecyclerView()
            categoriesAdapter.setCategories(it.shuffled().take(MAX_CATEGORIES))
        })
    }

    private fun setRecyclerView() {
        categoriesAdapter = CategoriesAdapter(this)
        suggestionsRecyclerView.layoutManager = FlexboxLayoutManager(this)
        suggestionsRecyclerView.setHasFixedSize(true)
        suggestionsRecyclerView.adapter = categoriesAdapter
    }

    override fun onItemClick(category: Category) {
        val intent = Intent().putExtra(RESULT_TERM, category.name.trim())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
