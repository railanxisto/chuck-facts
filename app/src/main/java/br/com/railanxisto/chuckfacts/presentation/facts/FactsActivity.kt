package br.com.railanxisto.chuckfacts.presentation.facts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsActivity

class FactsActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_TERM = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TERM) {
            if (resultCode == Activity.RESULT_OK) {
                val query = data?.getStringExtra(SearchFactsActivity.RESULT_TERM)
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
