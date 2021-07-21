package ru.gb.donspb.justpic.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.model.EPICServerResponse

class EpicRecycler(private var onItemViewClickListener: OnListItemClickListener) : RecyclerView.Adapter<EpicRecycler.ViewHolder>() {

    private var epicDataSet: List<EPICServerResponse> = listOf()

    fun setData(data: List<EPICServerResponse>?) {
        if (data != null) {
            epicDataSet = data
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_epic_card, parent, false) as View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(epicDataSet[position])
    }

    override fun getItemCount() = epicDataSet.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(epicItem: EPICServerResponse) {
            itemView.apply {
                findViewById<TextView>(R.id.recycler_card_text).text = epicItem.caption
                findViewById<TextView>(R.id.recycler_card_date).text = epicItem.date
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(dataItem: EPICServerResponse)
    }
}