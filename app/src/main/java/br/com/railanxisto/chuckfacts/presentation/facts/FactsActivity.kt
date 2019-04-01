package br.com.railanxisto.chuckfacts.presentation.facts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.domain.Fact
import br.com.railanxisto.chuckfacts.presentation.common.BaseActivity
import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class FactsActivity : BaseActivity(), FactsAdapter.ShareButtonClickListener {
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
