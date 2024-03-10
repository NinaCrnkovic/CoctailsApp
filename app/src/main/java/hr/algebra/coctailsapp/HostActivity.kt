package hr.algebra.coctailsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.coctailsapp.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}