package betalab.ca.burstofficialandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CampusMapFragment : Fragment() {

    companion object {
        fun newInstance(): CampusMapFragment {
            return CampusMapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.campus_map_fragment,
            container, false
        )
    }

}