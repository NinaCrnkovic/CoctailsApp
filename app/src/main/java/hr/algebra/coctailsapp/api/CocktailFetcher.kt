package hr.algebra.coctailsapp.api



import android.content.Context
import android.util.Log
import hr.algebra.coctailsapp.CocktailsReceiver
import hr.algebra.coctailsapp.framework.sendBroadcast
import hr.algebra.coctailsapp.model.Item
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

        val items = mutableListOf<Item>()

        cocktailsItems.forEach {
            val picturePath = null
            items.add(Item(
                null,
                it.strDrink,
                it.strCategory,
                it.strInstructions,
                picturePath ?: "",
                it.strIngredient1,
                it.strIngredient2,
                it.strIngredient3,
                it.strIngredient4,
                it.strIngredient5,
                it.strMeasure1,
                it.strMeasure2,
                it.strMeasure3,
                it.strMeasure4,
                it.strMeasure5



            ))
        }

        println(items)

        context.sendBroadcast<CocktailsReceiver>()
    }


}