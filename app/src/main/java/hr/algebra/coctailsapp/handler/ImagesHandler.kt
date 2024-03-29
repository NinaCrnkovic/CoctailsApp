package hr.algebra.coctailsapp.handler

import android.content.Context
import android.util.Log
import hr.algebra.coctailsapp.factory.createGetHttpUrlConnection
import java.io.File
import java.lang.Exception
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadImageAndStore(context: Context, url: String): String?{

    //https://www.thecocktaildb.com/images/media/drink/tqpvqp1472668328.jpg
    val filename = url.substring(url.lastIndexOf(File.separatorChar)+1)
    val file: File = createFile(context, filename)
    try{
        val con: HttpURLConnection = createGetHttpUrlConnection(url)
        Files.copy(con.inputStream, Paths.get(file.toURI()))
        return file.absolutePath

    }catch (e: Exception){
        Log.e("IMAGES_HANDLER", e.toString(), e)
    }
    return null
}



fun createFile(context: Context, filename: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, filename)
    if(file.exists()) file.delete()
    return file
}
