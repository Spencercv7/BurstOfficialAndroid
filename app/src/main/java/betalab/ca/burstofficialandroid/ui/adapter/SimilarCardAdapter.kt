package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.R.layout.similar_event_card as card_layout

class SimilarCardAdapter(private val imagesToBind : Array<Int>, private val textToBind : Array<Array<String>>) : RecyclerView.Adapter<SimilarCardAdapter.ViewHolder>() {

    class ViewHolder(view : ViewGroup) : RecyclerView.ViewHolder(view) {
        val similarCardImage : ImageView = view.findViewById(R.id.similar_card_image)
        val similarCardTitle : TextView = view.findViewById(R.id.similar_card_title)
        val similarCardAuthor : TextView = view.findViewById(R.id.similar_card_author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.similar_event_card, parent, false) as ViewGroup
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curDraw : Int = imagesToBind[position]
        val curText : Array<String> = textToBind[position]
        holder.similarCardImage.setImageResource(curDraw)
        holder.similarCardTitle.text = curText[0]
        holder.similarCardAuthor.text = curText[1]
    }

    override fun getItemCount() = imagesToBind.size

}
