package betalab.ca.burstofficialandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.internal.glide.GlideApp
import com.bumptech.glide.RequestManager
import betalab.ca.burstofficialandroid.R.layout.image_card as image_card_layout

class ImageCardAdapter(private val manager: RequestManager, private val imagesToBind : ArrayList<String> = ArrayList()) : RecyclerView.Adapter<ImageCardAdapter.ViewHolder>(){

    class ViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
        val cardImage : ImageView = view.findViewById(R.id.gallery_card_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(image_card_layout, parent, false) as ViewGroup
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        manager.load(imagesToBind[position]).into(holder.cardImage)
    }

    override fun getItemCount() = imagesToBind.size
    fun setData(images: Array<String>) {
        imagesToBind.clear()
        imagesToBind.addAll(images)
        notifyDataSetChanged()
    }

}