package hr.algebra.coctailsapp.api



import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.coctailsapp.COCKTAILS_PROVIDER_CONTENT_URI
import hr.algebra.coctailsapp.CocktailsReceiver
import hr.algebra.coctailsapp.framework.sendBroadcast
import hr.algebra.coctailsapp.handler.downloadImageAndStore
import hr.algebra.coctailsapp.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CocktailFetcher(private val context: Context) {

    private val cocktailsApi : CocktailsApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        cocktailsApi = retrofit.create(CocktailsApi::class.java)
    }

    fun fetchItems(letter: String){
        val request = cocktailsApi.fetchItems(letter)
        request.enqueue(object: Callback<CocktailListResponse> {
            override fun onResponse(
                call: Call<CocktailListResponse>,
                response: Response<CocktailListResponse>
            ) {
                val cocktailItems = response.body()?.drinks
                if (cocktailItems != null) {
                    populateItems(cocktailItems)
                }
            }

            override fun onFailure(call: Call<CocktailListResponse>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }


    private fun populateItems(cocktailsItems: List<CocktailItem>) {

        //val items = mutableListOf<Item>()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {

            cocktailsItems.forEach {
                val picturePath = downloadImageAndStore(context, it.strDrinkThumb)

                val values = ContentValues().apply {
                    put(Item::drinkName.name, it.strDrink ?: "")
                    put(Item::category.name, it.strCategory ?: "")
                    put(Item::instructions.name, it.strInstructions ?: "")
                    put(Item::picturePath.name, picturePath ?: "")
                    put(Item::ingredient1.name, it.strIngredient1 ?: "")
                    put(Item::ingredient2.name, it.strIngredient2 ?: "")
                    put(Item::ingredient3.name, it.strIngredient3 ?: "")
                    put(Item::ingredient4.name, it.strIngredient4 ?: "")
                    put(Item::ingredient5.name, it.strIngredient5 ?: "")
                    put(Item::measure1.name, it.strMeasure1 ?: "")
                    put(Item::measure2.name, it.strMeasure2 ?: "")
                    put(Item::measure3.name, it.strMeasure3 ?: "")
                    put(Item::measure4.name, it.strMeasure4 ?: "")
                    put(Item::measure5.name, it.strMeasure5 ?: "")



                }
                context.contentResolver.insert(
                    COCKTAILS_PROVIDER_CONTENT_URI,
                    values
                )

            }



            context.sendBroadcast<CocktailsReceiver>()
        }
    }


}