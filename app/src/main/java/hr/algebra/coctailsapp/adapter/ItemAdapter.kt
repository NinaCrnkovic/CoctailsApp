package hr.algebra.coctailsapp.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.coctailsapp.COCKTAILS_PROVIDER_CONTENT_URI
import hr.algebra.coctailsapp.ITEM_POSITION
import hr.algebra.coctailsapp.ItemPagerActivity
import hr.algebra.coctailsapp.R
import hr.algebra.coctailsapp.framework.startActivity
import hr.algebra.coctailsapp.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivImage)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)

        fun bind(item: Item){

            tvTitle.text = item.drinkName
            tvCategory.text = "Category: ${item.category}"


            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.about)
                .transform(RoundedCornersTransformation(50,50))
                .into(ivItem)



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = items[position]
        holder.itemView.setOnLongClickListener{
            context.contentResolver.delete(
                ContentUris.withAppendedId(COCKTAILS_PROVIDER_CONTENT_URI, item.id!!),
                null,
                null

            )
            File(item.picturePath).delete()
            items.removeAt(position)
            notifyDataSetChanged()


            true
        }
        holder.itemView.setOnClickListener{
            context.startActivity<ItemPagerActivity>(ITEM_POSITION, position)

        }
        holder.bind(item)
    }

}