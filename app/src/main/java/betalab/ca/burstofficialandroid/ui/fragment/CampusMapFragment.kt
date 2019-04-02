package betalab.ca.burstofficialandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import betalab.ca.burstofficialandroid.R

class CampusMapFragment : Fragment() {

    companion object {
        fun newInstance(): CampusMapFragment {
            return CampusMapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_campus_map,
            container, false
        )
    }

}