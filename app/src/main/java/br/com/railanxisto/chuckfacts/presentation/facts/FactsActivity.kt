package br.com.railanxisto.chuckfacts.presentation.facts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.railanxisto.chuckfacts.R
import domain.Fact
import br.com.railanxisto.chuckfacts.presentation.common.BaseActivity
import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsActivity
import br.com.railanxisto.chuckfacts.presentation.utils.ext.hide
import br.com.railanxisto.chuckfacts.presentation.utils.ext.isConnected
import br.com.railanxisto.chuckfacts.presentation.utils.ext.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_error_view.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class FactsActivity : BaseActivity(), FactsAdapter.ShareButtonClickListener {
    companion object {
        const val REQUEST_TERM = 0
    }

    val viewModel: FactsViewModel by viewModel()
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
        factsAdapter = FactsAdapter(this)
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

        viewModel.error.observe(this, Observer {
            if (it.isNotEmpty()) {
                factsRecyclerView.hide()
                loadingErrorView.progressBar.hide()
                loadingErrorView.erroDescriptionTextView.text = it
                loadingErrorView.errorLayout.show()
                loadingErrorView.show()
            }
        })

        viewModel.isEmpty.observe(this, Observer {
            if (it) {
                factsAdapter.setFacts(listOf())
                Toast.makeText(this, R.string.no_results, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onShareButtonClick(fact: Fact) {
        val intent = Intent(android.content.Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_TEXT, fact.value)
        startActivity(Intent.createChooser(intent, getString(R.string.share_on)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TERM) {
            if (resultCode == Activity.RESULT_OK) {
                val query = data?.getStringExtra(SearchFactsActivity.RESULT_TERM)
                query?.let {
                    if (query.isNotEmpty()) {
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
