package ru.gb.donspb.justpic.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.model.EPICServerResponse

class EpicRecycler(private var onItemViewClickListener: OnListItemClickListener) : RecyclerView.Adapter<BaseViewHolder>() {

    private var epicDataSet: List<EPICServerResponse> = mutableListOf(
        EPICServerResponse(null,null,null, "Header"))

    fun setData(data: List<EPICServerResponse>?) {
        if (data != null) {
            //epicDataSet.plus(data)
            epicDataSet = data
            notifyDataSetChanged()
        }
    }

    fun appendItem() {
        epicDataSet = generateItem()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        if (viewType == TYPE_CARD)
            return ViewHolder(inflater.inflate(
                R.layout.recycler_epic_card, parent, false) as View)
        else
            return HeaderViewHolder(inflater.inflate(
                R.layout.recycler_epic_header, parent, false) as View)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(epicDataSet[position])
    }

    override fun getItemCount() = epicDataSet.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            else -> TYPE_CARD
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(epicItem: EPICServerResponse) {
            itemView.apply {
                findViewById<TextView>(R.id.recycler_card_text).text = epicItem.caption
                findViewById<TextView>(R.id.recycler_card_date).text = epicItem.date
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(dataItem: EPICServerResponse) {
            itemView.setOnClickListener {  }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(dataItem: EPICServerResponse)
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CARD = 1
    }
}