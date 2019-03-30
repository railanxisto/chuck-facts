package br.com.railanxisto.chuckfacts.presentation.searchFacts

import android.os.Bundle
import br.com.railanxisto.chuckfacts.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.railanxisto.chuckfacts.domain.Category
import br.com.railanxisto.chuckfacts.presentation.common.BaseActivity
import kotlinx.android.synthetic.main.activity_search_facts.*
import org.koin.android.ext.android.inject

class SearchFactsActivity : BaseActivity(), CategoriesAdapter.OnItemAdapterClickListener {

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
            categoriesAdapter.setCategories(it)
        })
    }

    private fun setRecyclerView() {
        categoriesAdapter = CategoriesAdapter(this)
        suggestionsRecyclerView.layoutManager = LinearLayoutManager(this)
        suggestionsRecyclerView.setHasFixedSize(true)
        suggestionsRecyclerView.adapter = categoriesAdapter
    }

    override fun onItemClick(category: Category) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
