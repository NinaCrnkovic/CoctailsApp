package hr.algebra.coctailsapp.framework



import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.coctailsapp.COCKTAILS_PROVIDER_CONTENT_URI
import hr.algebra.coctailsapp.api.API_URL
import hr.algebra.coctailsapp.model.Item


fun View.applyAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })

inline fun <reified T : Activity> Context.startActivity(key: String, value: Int) =
    startActivity(Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(key, value)
        })

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean = true)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .edit()
    .putBoolean(key, value)
    .apply()

fun Context.getBooleanPreference(key: String)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .getBoolean(key, false)

fun callDelayed(delay: Long, work: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let {network ->
        connectivityManager.getNetworkCapabilities(network)?.let {networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

fun Context.fetchItems(): MutableList<Item>{
    val items = mutableListOf<Item>()
    val cursor = contentResolver?.query(
        COCKTAILS_PROVIDER_CONTENT_URI,
        null,
        null,
        null,
        null
    )
    cursor?.use {
        while (it.moveToNext()) {
            val id = it.getLong(it.getColumnIndexOrThrow("id"))
            val drinkName = it.getString(it.getColumnIndexOrThrow("drinkName"))
            val category = it.getString(it.getColumnIndexOrThrow("category"))
            val instructions = it.getString(it.getColumnIndexOrThrow("instructions"))
            val picturePath = it.getString(it.getColumnIndexOrThrow("picturePath"))
            val ingredient1 = it.getString(it.getColumnIndexOrThrow("ingredient1"))
            val ingredient2 = it.getString(it.getColumnIndexOrThrow("ingredient2"))
            val ingredient3 = it.getString(it.getColumnIndexOrThrow("ingredient3"))
            val ingredient4 = it.getString(it.getColumnIndexOrThrow("ingredient4"))
            val ingredient5 = it.getString(it.getColumnIndexOrThrow("ingredient5"))
            val measure1 = it.getString(it.getColumnIndexOrThrow("measure1"))
            val measure2 = it.getString(it.getColumnIndexOrThrow("measure2"))
            val measure3 = it.getString(it.getColumnIndexOrThrow("measure3"))
            val measure4 = it.getString(it.getColumnIndexOrThrow("measure4"))
            val measure5 = it.getString(it.getColumnIndexOrThrow("measure5"))

            items.add(
                Item(
                    id = id,
                    drinkName = drinkName,
                    category = category,
                    instructions = instructions,
                    picturePath = picturePath,
                    ingredient1 = ingredient1,
                    ingredient2 = ingredient2,
                    ingredient3 = ingredient3,
                    ingredient4 = ingredient4,
                    ingredient5 = ingredient5,
                    measure1 = measure1,
                    measure2 = measure2,
                    measure3 = measure3,
                    measure4 = measure4,
                    measure5 = measure5
                )
            )
        }
    }

    return items

}