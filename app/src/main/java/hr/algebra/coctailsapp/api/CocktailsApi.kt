package hr.algebra.coctailsapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

interface CocktailsApi {
    @GET("search.php")
    fun fetchItems(@Query("f") firstLetter: String): Call<CocktailListResponse>
}
