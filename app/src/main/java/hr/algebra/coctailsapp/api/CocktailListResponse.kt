package hr.algebra.coctailsapp.api

import com.google.gson.annotations.SerializedName

data class CocktailListResponse(
    @SerializedName("drinks") val drinks: List<CocktailItem>
)
