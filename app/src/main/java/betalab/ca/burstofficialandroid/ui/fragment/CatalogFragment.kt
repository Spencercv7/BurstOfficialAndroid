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
import betalab.ca.burstofficialandroid.model.CatalogItem
import betalab.ca.burstofficialandroid.ui.adapter.ResourceAdapter
import kotlinx.android.synthetic.main.fragment_catalog.*
import betalab.ca.burstofficialandroid.ui.activity.OrgProfileActivity
import betalab.ca.burstofficialandroid.ui.adapter.CatalogAdapter

class CatalogFragment : Fragment(), OnClickAdapterCat {

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

        val catItems = listOf(CatalogItem("Club One", "This is club One"), CatalogItem("Club Two", "This is club two"), CatalogItem("Club Three", "This is Club Three"), CatalogItem("Club Four", "This is club Four"))

        resources_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        resources_recyclerview.adapter = CatalogAdapter(catItems) { event : CatalogItem -> onClickAdapterCat(event)}
        resources_recyclerview.hasFixedSize()
    }

    override fun onClickAdapterCat(event: CatalogItem) {
        val intent = Intent(context, OrgProfileActivity::class.java)
        startActivity(intent)
    }
}

interface OnClickAdapterCat {
    fun onClickAdapterCat(event : CatalogItem )
}