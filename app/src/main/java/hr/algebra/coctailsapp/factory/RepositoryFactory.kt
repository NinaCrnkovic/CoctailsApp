package hr.algebra.coctailsapp.factory

import android.content.Context
import hr.algebra.coctailsapp.dao.CocktailsSqlHelper

fun getRepository(context: Context?) = CocktailsSqlHelper(context)