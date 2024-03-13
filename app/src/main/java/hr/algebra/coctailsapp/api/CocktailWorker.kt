package hr.algebra.coctailsapp.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CocktailWorker(
    private val context: Context,
    workerParams: WorkerParameters
    ) : Worker(context, workerParams) {

    override fun doWork(): Result {
        CocktailFetcher(context).fetchItems("a")
        return Result.success()
    }


}