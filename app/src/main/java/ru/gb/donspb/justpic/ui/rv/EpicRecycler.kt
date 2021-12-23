package ru.gb.donspb.justpic.ui.rv

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.model.EPICServerResponse
import java.time.LocalDateTime

class EpicRecycler(private var onItemViewClickListener: OnListItemClickListener)
    : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    private var epicDataSet: MutableList<Pair<EPICServerResponse, Boolean>> = mutableListOf(
        Pair(EPICServerResponse(null,null,null, "Header"), false))

    fun setData(data: List<EPICServerResponse>?) {
        if (data != null) {
            for (dataItem in data) {
                epicDataSet.add(Pair(dataItem, false))
            }
            notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun appendItem() {
        epicDataSet.add(Pair(EPICServerResponse(
            null,"Test string", null, LocalDateTime.now().toString()),false))
        notifyItemInserted(itemCount - 1)
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

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView), ItemTouchHelperViewHolder {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun bind(epicItem: Pair<EPICServerResponse, Boolean>) {
            itemView.apply {
                val datefield = findViewById<TextView>(R.id.recycler_card_date)
                val imagefield = findViewById<ImageView>(R.id.rv_card_image)
                val spaceIndex = epicItem.first.date?.indexOf(" ", 0, false)
                var shortDate: String?
                if (spaceIndex != -1) {
                    shortDate = epicItem.first.date?.subSequence(0, spaceIndex!!).toString()
                }
                else shortDate = ""

                val url = "https://epic.gsfc.nasa.gov/archive/natural/" +
                        shortDate.replace('-','/') + "/png/${epicItem.first.url}.png"
                datefield.setOnClickListener {
                    toggleImage()
                }
                datefield.text = epicItem.first.date
                findViewById<TextView>(R.id.recycler_card_text).text = epicItem.first.caption

                imagefield.load(url) {
                    lifecycle(context as LifecycleOwner)
                    error(R.drawable.ic_loading_error)
                    placeholder(R.drawable.ic_no_image)
                }
                imagefield.visibility =
                    if (epicItem.second) View.VISIBLE else View.GONE
            }
        }

        private fun toggleImage() {
            epicDataSet[layoutPosition] = epicDataSet[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(data: Pair<EPICServerResponse, Boolean>) {
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

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        epicDataSet.removeAt(fromPosition).apply {
            epicDataSet.add(
                if (toPosition > fromPosition) toPosition - 1 else toPosition, this
            )
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        epicDataSet.removeAt(position)
        notifyItemRemoved(position)
    }
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewHolder {

    fun onItemSelected()

    fun onItemClear()
}

class ItemTouchHelperCallback(private val adapter: EpicRecycler) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END

        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
        itemViewHolder.onItemClear()
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}