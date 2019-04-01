package betalab.ca.burstofficialandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ResourcesFragment : Fragment() {

    companion object {
        fun newInstance(): ResourcesFragment {
            return ResourcesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.resources_fragment,
            container, false
        )
    }
}