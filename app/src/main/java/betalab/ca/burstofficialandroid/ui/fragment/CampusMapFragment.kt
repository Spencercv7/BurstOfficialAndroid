package betalab.ca.burstofficialandroid.ui.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.MapSearch
import betalab.ca.burstofficialandroid.ui.adapter.CampusMapAdapter

import kotlinx.android.synthetic.main.fragment_campus_map.*

class CampusMapFragment : Fragment() {

    companion object {
        fun newInstance(): CampusMapFragment {
            return CampusMapFragment()
        }
    }

    private lateinit var myDataSet: MutableList<MapSearch>

    override fun onCreate(savedInstanceState: Bundle?) {
        setData()   //set data to be passed to recycler view adapter
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_campus_map,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        campus_map_search_recyclerview.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        campus_map_search_recyclerview.adapter = CampusMapAdapter(myDataSet)
        campus_map_search_recyclerview.hasFixedSize()
    }


    // Function in which we draw data from the database
    // Currently just makes placeholder information
    private fun setData() {
        val mapSearchOpen = MapSearch(
            "Stauffer Library", "Study Space",
            "Open", "Closes at 12AM")
        val mapSearchClosed = MapSearch(
            "Stauffer Library", "Study Space",
            "Closed", "Closes at 12AM")
        myDataSet = mutableListOf(mapSearchClosed, mapSearchOpen, mapSearchOpen)
    }

}