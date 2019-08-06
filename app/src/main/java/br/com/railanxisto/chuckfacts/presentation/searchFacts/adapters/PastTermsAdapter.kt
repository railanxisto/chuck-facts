package br.com.railanxisto.chuckfacts.presentation.searchFacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.railanxisto.chuckfacts.R
import data.local.model.Term
import kotlinx.android.synthetic.main.past_terms_item.view.*

class PastTermsAdapter(
    private val listener: OnItemAdapterClickListener
) : RecyclerView.Adapter<PastTermsAdapter.TermViewHolder>() {

    private val pastTerms = mutableListOf<Term>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.past_terms_item, parent, false)
        return TermViewHolder(view)
    }

    override fun getItemCount(): Int = pastTerms.size

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        pastTerms[position].run {
            holder.setTerm(this)
            holder.setClickListener {
                listener.onItemClick(this)
            }
        }
    }

    fun setTerms(pastTermsList: List<Term>) {
        pastTerms.clear()
        pastTerms.addAll(pastTermsList)
        notifyDataSetChanged()
    }

    inner class TermViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setTerm(term: Term) {
            itemView.termTextView.text = term.term
        }

        fun setClickListener(clickListener: () -> Unit) {
            itemView.setOnClickListener {
                clickListener()
            }
        }
    }

    interface OnItemAdapterClickListener {
        fun onItemClick(term: Term)
    }
}
