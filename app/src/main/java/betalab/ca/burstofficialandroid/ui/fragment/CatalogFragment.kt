package betalab.ca.burstofficialandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.adapter.ResourceAdapter
import kotlinx.android.synthetic.main.fragment_catalog.*

class CatalogFragment : Fragment() {

    companion object {
        fun newInstance(): CatalogFragment {
            return CatalogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_catalog,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resources_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        resources_recyclerview.adapter = ResourceAdapter()
        resources_recyclerview.hasFixedSize()
    }

}