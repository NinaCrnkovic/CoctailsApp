package hr.algebra.coctailsapp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.coctailsapp.dao.Repository
import hr.algebra.coctailsapp.factory.getRepository
import hr.algebra.coctailsapp.model.Item
import java.lang.IllegalArgumentException

private const val AUTHORITY = "hr.algebra.coctailsapp.api.provider"
private const val PATH = "hr.algebra.coctailsapp.api.provider"
val COCKTAILS_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

private const val ITEMS = 10
private const val ITEM_ID = 20

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)){
    addURI(AUTHORITY, PATH, ITEMS)
    addURI(AUTHORITY, "$PATH/#", ITEM_ID)
    this
}
//content:hr.algebra.coctailsapp.api.provider/items
class CocktailsContentProvider : ContentProvider() {

    private lateinit var repository: Repository
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)){
            ITEMS -> return repository.delete(selection, selectionArgs)
                ITEM_ID ->{
                    uri.lastPathSegment?.let {
                        id -> repository.delete("${Item::id.name}=?", arrayOf(id))
                    }
                }
        }
        throw IllegalArgumentException("WRONG URI")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = repository.insert(values)
        return ContentUris.withAppendedId(COCKTAILS_PROVIDER_CONTENT_URI, id)
    }

    override fun onCreate(): Boolean {
        repository = getRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = repository.query(
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)){
            ITEMS -> return repository.update(values, selection, selectionArgs)
            ITEM_ID ->{
                uri.lastPathSegment?.let {
                        id -> repository.update(values,"${Item::id.name}=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("WRONG URI")
    }
}