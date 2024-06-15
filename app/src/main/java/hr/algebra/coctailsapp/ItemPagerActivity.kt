package hr.algebra.coctailsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.coctailsapp.adapter.ItemPagerAdapter
import hr.algebra.coctailsapp.databinding.ActivityItemPagerBinding
import hr.algebra.coctailsapp.framework.fetchItems
import hr.algebra.coctailsapp.model.Item

const val ITEM_POSITION = "hr.algebra.coctailsapp.item_pos"
class ItemPagerActivity : AppCompatActivity() {

    private lateinit var items: MutableList<Item>
    private var itemPosition  = 0

    private lateinit var binding: ActivityItemPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPager()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        items = fetchItems()
        itemPosition = intent.getIntExtra(ITEM_POSITION, itemPosition)
        binding.viewPager.adapter = ItemPagerAdapter(this, items)
        binding.viewPager.currentItem = itemPosition

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}