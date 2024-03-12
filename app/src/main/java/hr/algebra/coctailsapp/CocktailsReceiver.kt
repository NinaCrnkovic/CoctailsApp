package hr.algebra.coctailsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.coctailsapp.framework.startActivity

class CocktailsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        //context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()

    }
}