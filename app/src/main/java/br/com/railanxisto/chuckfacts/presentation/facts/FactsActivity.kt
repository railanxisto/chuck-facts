package br.com.railanxisto.chuckfacts.presentation.facts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsActivity

class FactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_search -> {
            val intent = Intent(this@FactsActivity, SearchFactsActivity::class.java)
            startActivity(intent)
            true
        }
        else -> false
    }
}
