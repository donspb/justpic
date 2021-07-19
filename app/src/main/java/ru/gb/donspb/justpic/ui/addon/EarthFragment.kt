package ru.gb.donspb.justpic.ui.addon

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.model.*
import java.util.*

class EarthFragment : Fragment() {

    private val adapter = EpicRecycler(object : OnItemViewClickListener {
        override fun onItemViewClick(data: EPICServerResponse) {

        }
    })

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddonViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(this@EarthFragment, Observer<EpicData> { renderData(it) })
    }

    private fun renderData(data: EpicData) {
        when (data) {
            is EpicData.Success -> {
                adapter.setData(data.serverResponseDataList.resultsList)
            }
            is EpicData.Loading -> {
                // TODO: showLoading()
            }
            is EpicData.Error -> {
                // TODO: ShowError()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(data: EPICServerResponse)
    }
}