package ru.gb.donspb.justpic.ui.rv

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.model.AddonViewModel
import ru.gb.donspb.justpic.model.EPICServerResponse
import ru.gb.donspb.justpic.model.EpicData

class RecyclerFragment : Fragment() {

    private val adapter = EpicRecycler(
        object: EpicRecycler.OnListItemClickListener {
            override fun onItemClick(dataItem: EPICServerResponse) {
                Toast.makeText(requireContext(), dataItem.date, Toast.LENGTH_SHORT).show()
            }
        }
    )

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddonViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_earth)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        view.findViewById<FloatingActionButton>(R.id.rv_fragment_fab).setOnClickListener {
            adapter.appendItem()
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recycler)

        viewModel.getData().observe(this@RecyclerFragment, Observer<EpicData> { renderData(it) })
    }

    private fun renderData(data: EpicData) {
        when (data) {
            is EpicData.Success -> {
                adapter.setData(data.serverResponseDataList)
            }
            is EpicData.Loading -> {
                // TODO: showLoading()
            }
            is EpicData.Error -> {
                // TODO: ShowError()
            }
        }
    }

    companion object {
        fun newInstance() : RecyclerFragment {
            return RecyclerFragment()
        }
    }
}