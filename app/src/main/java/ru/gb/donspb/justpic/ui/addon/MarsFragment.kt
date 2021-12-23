package ru.gb.donspb.justpic.ui.addon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.model.IMarsPresenter
import ru.gb.donspb.justpic.model.MarsPresenter
import java.util.*

class MarsFragment : Fragment(), ViewMarsContract {

    private val presenter: IMarsPresenter = MarsPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setCounter(0)
        setCountText(0)
        requireActivity().findViewById<Button>(R.id.decrementButton)
            .setOnClickListener { presenter.onDecrement() }
        requireActivity().findViewById<Button>(R.id.incrementButton)
            .setOnClickListener { presenter.onIncrement() }
    }

    fun setCountText(count: Int) {
        requireActivity().findViewById<TextView>(R.id.totalCountTextView)
            .text = String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }
}