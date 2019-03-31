package br.com.railanxisto.chuckfacts.presentation.facts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.domain.Fact
import kotlinx.android.synthetic.main.fact_item.view.*
import kotlinx.android.synthetic.main.past_terms_item.view.*

class FactsAdapter: RecyclerView.Adapter<FactsAdapter.FactsViewHolder>() {

    private val facts = mutableListOf<Fact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fact_item, parent, false)
        return FactsViewHolder(view)
    }

    override fun getItemCount(): Int = facts.size

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        facts[position].run {
            holder.setFact(this)
        }
    }

    fun setFacts(factsList: List<Fact>) {
        facts.clear()
        facts.addAll(factsList)
        notifyDataSetChanged()
    }

    inner class FactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setFact(fact: Fact) {
            itemView.factTextView.text = fact.value
            itemView.categoryTextView.text = fact.getCategoryText()
            itemView.factTextView.setTextSize(fact.getTextSize(fact.value))
        }
    }

    interface OnItemAdapterClickListener {
        fun onItemClick(fact: Fact)
    }
}
