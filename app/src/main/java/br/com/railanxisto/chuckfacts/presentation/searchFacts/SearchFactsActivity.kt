package br.com.railanxisto.chuckfacts.presentation.searchFacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.data.local.model.Term
import br.com.railanxisto.chuckfacts.domain.Category
import br.com.railanxisto.chuckfacts.presentation.common.BaseActivity
import br.com.railanxisto.chuckfacts.presentation.searchFacts.adapters.CategoriesAdapter
import br.com.railanxisto.chuckfacts.presentation.searchFacts.adapters.PastTermsAdapter
import br.com.railanxisto.chuckfacts.presentation.utils.ext.isConnected
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_search_facts.*
import org.koin.android.ext.android.inject

const val MAX_CATEGORIES = 8

class SearchFactsActivity : BaseActivity(), CategoriesAdapter.OnItemAdapterClickListener, PastTermsAdapter.OnItemAdapterClickListener {

    companion object {
        const val RESULT_TERM = "term"
    }

    val viewModel: SearchFactsViewModel by inject()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var pastTermsAdapter: PastTermsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_facts)

        viewModel.getCategories()
        viewModel.getPastTerms()

        searchEditText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendTermForSearch(searchEditText.text?.toString() ?: "")
                true
            } else {
                false
            }
        }

        initializeObservers()
    }

    private fun initializeObservers() {
        viewModel.getCategoriesList().observe(this, Observer {
            setCategoriesRecyclerView()
            categoriesAdapter.setCategories(it.shuffled().take(MAX_CATEGORIES))
        })

        viewModel.getPastTermsList().observe(this, Observer {
            setPastTermsRecyclerView()
            pastTermsAdapter.setTerms(it)
        })
    }

    private fun setCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter(this)
        suggestionsRecyclerView.layoutManager = FlexboxLayoutManager(this)
        suggestionsRecyclerView.setHasFixedSize(true)
        suggestionsRecyclerView.isNestedScrollingEnabled = false
        suggestionsRecyclerView.adapter = categoriesAdapter
    }

    private fun setPastTermsRecyclerView() {
        pastTermsAdapter = PastTermsAdapter(this)
        pastSearchesRecyclerView.layoutManager = LinearLayoutManager(this)
        pastSearchesRecyclerView.setHasFixedSize(true)
        pastSearchesRecyclerView.isNestedScrollingEnabled = false
        pastSearchesRecyclerView.adapter = pastTermsAdapter
    }

    override fun onItemClick(category: Category) {
        sendTermForSearch(category.name)
    }

    override fun onItemClick(term: Term) {
        sendTermForSearch(term.term)
    }

    private fun sendTermForSearch(term: String) {
        if (isConnected()) {
            val intent = Intent().putExtra(RESULT_TERM, term)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            showSnackbar(R.string.no_internet_message)
        }

    }
}
