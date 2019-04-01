package betalab.ca.burstofficialandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CatalogFragment : Fragment() {

    companion object {
        fun newInstance(): CatalogFragment {
            return CatalogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.catalog_fragment,
            container, false
        )
    }

}