package betalab.ca.burstofficialandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import betalab.ca.burstofficialandroid.R

class ResourcesFragment : Fragment() {

    companion object {
        fun newInstance(): ResourcesFragment {
            return ResourcesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_resources,
            container, false
        )
    }
}