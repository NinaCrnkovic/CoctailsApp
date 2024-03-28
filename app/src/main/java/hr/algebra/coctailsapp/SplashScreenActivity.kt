package hr.algebra.coctailsapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.coctailsapp.api.CocktailWorker
import hr.algebra.coctailsapp.databinding.ActivitySplashScreenBinding
import hr.algebra.coctailsapp.framework.applyAnimation
import hr.algebra.coctailsapp.framework.callDelayed
import hr.algebra.coctailsapp.framework.getBooleanPreference
import hr.algebra.coctailsapp.framework.isOnline
import hr.algebra.coctailsapp.framework.startActivity

private const val DELAY = 5000L

const val DATA_IMPORTED = "hr.algebra.coctailsapp.data_imported"


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        startAnimations()
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        redirect()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.rotate)
        binding.tvSplashWelcome.applyAnimation(R.anim.blink)
    }
    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) {
                startActivity<HostActivity>()
            }
        } else {
            if (isOnline()) {

                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(CocktailWorker::class.java)
                    )
                }


            } else {
                binding.tvSplashWelcome.text = getString(R.string.no_internet)
                callDelayed(DELAY) {
                    finish()
                }
            }
        }
    }

}