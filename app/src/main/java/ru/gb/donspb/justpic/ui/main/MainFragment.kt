package ru.gb.donspb.justpic.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.gb.donspb.justpic.MainActivity
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.model.MainViewModel
import ru.gb.donspb.justpic.model.PictureOfTheDayData
import java.text.SimpleDateFormat
import java.util.*

private const val TWODAYS = 2
private const val ONEDAY = 1

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private var isMain = true
        private var isChecked = false
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_search -> Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
//            R.id.navigation_set_dark -> {
//                isChecked = !item.isChecked()
//                item.setChecked(isChecked)
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
//        val bottomBar = view.findViewById<BottomAppBar>(R.id.bottom_app_bar)
//        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val context = activity as MainActivity
//        if (isMain) {
//            isMain = false
//            bottomBar.navigationIcon = null
//            bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//            fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_arrow))
//            bottomBar.replaceMenu(R.menu.alt_menu)
//        } else {
//            isMain = true
//            bottomBar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_hamburger_icon)
//            bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//            fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_sign))
//            bottomBar.replaceMenu(R.menu.main_menu)
//        }



        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))



        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date = dateFormat.format(Date())
        view.findViewById<Chip>(R.id.chip_2days).setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -TWODAYS)
            date = dateFormat.format(calendar.time)
            loadVM(date)
        }
        view.findViewById<Chip>(R.id.chip_yest).setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -ONEDAY)
            date = dateFormat.format(calendar.time)
            loadVM(date)
        }
        view.findViewById<Chip>(R.id.chip_today).setOnClickListener {
            date = dateFormat.format(Date())
            loadVM(date)
        }
        loadVM(date)
    }

    private fun loadVM(date: String) {
        viewModel.getData(date).observe(this@MainFragment, Observer<PictureOfTheDayData>
        { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val title = serverResponseData.title
                val description = serverResponseData.explanation
                val urlHD = serverResponseData.hdurl
                if (url.isNullOrEmpty()) {
                    // TODO: error message
                }
                else {
                    val chipHD = view?.findViewById<Chip>(R.id.chip_hd_filter)

                    chipHD?.visibility = Chip.VISIBLE
                    chipHD?.setOnCheckedChangeListener { chipHD, isChecked ->
                        if (isChecked) pictureShower(urlHD)
                        else pictureShower(url)
                    }
                    pictureShower(url)
                    view?.findViewById<TextView>(R.id.bottom_sheet_header)?.text = title
                    view?.findViewById<TextView>(R.id.bottom_sheet_description)?.text = description
                }
            }
            is PictureOfTheDayData.Loading -> {
                // TODO: showLoading()
            }
            is PictureOfTheDayData.Error -> {
                // TODO: ShowError()
            }
        }
    }

    private fun pictureShower(url: String?) {
        val imageView = view?.findViewById<ImageView>(R.id.image_view)
        imageView?.load(url) {
            lifecycle(this@MainFragment)
            error(R.drawable.ic_loading_error)
            placeholder(R.drawable.ic_no_image)
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

}