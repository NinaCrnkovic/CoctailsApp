package hr.algebra.coctailsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import hr.algebra.coctailsapp.databinding.ActivitySplashScreenBinding
import hr.algebra.coctailsapp.framework.applyAnimation
import hr.algebra.coctailsapp.framework.startActivity

private const val DELAY = 3000L



class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.rotate)
        binding.tvSplashWelcome.applyAnimation(R.anim.blink)
    }
    private fun redirect() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity<HostActivity>()


            },
            DELAY
        )

    }



}