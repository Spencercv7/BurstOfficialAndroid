package betalab.ca.burstofficialandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.model.ExploreHorizontalCardData
import betalab.ca.burstofficialandroid.ui.activity.EventActivity
import betalab.ca.burstofficialandroid.ui.adapter.ExploreAdapter

class ExploreFragment : Fragment(), OnClickAdapterExplore {
    companion object {
        fun newInstance(): ExploreFragment {
            return ExploreFragment()
        }
    }
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSet = listOf(ExploreHorizontalCardData("Card One"), ExploreHorizontalCardData("Card Two"), ExploreHorizontalCardData("Card Three"))

        recyclerView = view.findViewById(R.id.explore_fragment_recycler_view) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = ExploreAdapter(dataSet) {event : ExploreHorizontalCardData -> onClickedAdapterExplore(event)}

        recyclerView.hasFixedSize()
    }

    override fun onClickedAdapterExplore(event: ExploreHorizontalCardData) {
        val intent = Intent(context, EventActivity::class.java)
        startActivity(intent)
    }
}

interface OnClickAdapterExplore {
    fun onClickedAdapterExplore(event : ExploreHorizontalCardData)
}