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

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)


        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
        private val tvInstructions = itemView.findViewById<TextView>(R.id.tvInstructions)
        private val tvIngredient1 = itemView.findViewById<TextView>(R.id.tvIngredient1)
        private val tvMeasure1 = itemView.findViewById<TextView>(R.id.tvMeasure1)
        private val tvIngredient2 = itemView.findViewById<TextView>(R.id.tvIngredient2)
        private val tvMeasure2 = itemView.findViewById<TextView>(R.id.tvMeasure2)
        private val tvIngredient3 = itemView.findViewById<TextView>(R.id.tvIngredient3)
        private val tvMeasure3 = itemView.findViewById<TextView>(R.id.tvMeasure3)
        private val tvIngredient4 = itemView.findViewById<TextView>(R.id.tvIngredient4)
        private val tvMeasure4 = itemView.findViewById<TextView>(R.id.tvMeasure4)
        private val tvIngredient5 = itemView.findViewById<TextView>(R.id.tvIngredient5)
        private val tvMeasure5 = itemView.findViewById<TextView>(R.id.tvMeasure5)

        fun bind(item: Item) {
            tvTitle.text = item.drinkName
            tvCategory.text = "Category: ${item.category}"
            tvInstructions.text = "Instructions: ${item.instructions}"
            tvIngredient1.text = item.ingredient1
            tvMeasure1.text = item.measure1
            tvIngredient2.text = item.ingredient2
            tvMeasure2.text = item.measure2
            tvIngredient3.text = item.ingredient3
            tvMeasure3.text = item.measure3
            tvIngredient4.text = item.ingredient4
            tvMeasure4.text = item.measure4
            tvIngredient5.text = item.ingredient5
            tvMeasure5.text = item.measure5

            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.aboutcoctail)
                .transform(RoundedCornersTransformation(50, 50))
                .into(ivItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)

    }
}
