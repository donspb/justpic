package ru.gb.donspb.justpic.ui.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.gb.donspb.justpic.model.EPICServerResponse

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: EPICServerResponse)
}