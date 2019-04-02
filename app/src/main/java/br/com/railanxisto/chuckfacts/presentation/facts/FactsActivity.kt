package br.com.railanxisto.chuckfacts.presentation.facts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.presentation.common.BaseActivity
import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsActivity
import br.com.railanxisto.chuckfacts.presentation.utils.hide
import br.com.railanxisto.chuckfacts.presentation.utils.isConnected
import br.com.railanxisto.chuckfacts.presentation.utils.show
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class FactsActivity : BaseActivity() {

    companion object {
        const val REQUEST_TERM = 0
    }

    val viewModel: FactsViewModel by inject()
    private lateinit var factsAdapter: FactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCategoriesRecyclerView()
        initializeObservers()

        if (!isConnected()) {
            showSnackbar(R.string.no_internet_message)
        }
    }

    private fun setCategoriesRecyclerView() {
        factsAdapter = FactsAdapter()
        factsRecyclerView.layoutManager = LinearLayoutManager(this)
        factsRecyclerView.setHasFixedSize(true)
        factsRecyclerView.adapter = factsAdapter
    }

    private fun initializeObservers() {
        viewModel.getFactsList().observe(this, Observer {
            factsAdapter.setFacts(it)
        })

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                loadingErrorView.show()
                factsRecyclerView.hide()
            } else {
                factsRecyclerView.show()
                loadingErrorView.hide()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TERM) {
            if (resultCode == Activity.RESULT_OK) {
                val query = data?.getStringExtra(SearchFactsActivity.RESULT_TERM)
                query?.let {
                    if (query.length > 0) {
                        viewModel.saveSearch(it)
                        viewModel.getFacts(query)
                    }
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_search -> {
            val intent = Intent(this@FactsActivity, SearchFactsActivity::class.java)
            startActivityForResult(intent, REQUEST_TERM)
            true
        }
        else -> false
    }
}
