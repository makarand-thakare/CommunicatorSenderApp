package `in`.dazzlingapps.senderapp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

//        // Insert data in ContentValues- for ContentProvider to retrieve it
//        val values = ContentValues()
//        values.put("name", "Sample Data sending")
//        contentResolver.insert(MyContentProvider.CONTENT_URI, values)
class MyContentProvider : ContentProvider() {
    private var dataStorage: MutableList<ContentValues?>? = null
    override fun onCreate(): Boolean {
        dataStorage = ArrayList()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        if (uriMatcher.match(uri) == DATA) {
            val columns = arrayOf("_id", "name")
            val cursor = MatrixCursor(columns)
            for (i in dataStorage!!.indices) {
                val item = dataStorage!![i]
                cursor.addRow(arrayOf<Any>(i, item!!.getAsString("name")))
            }
            return cursor
        }
        throw IllegalArgumentException("Unsupported URI: $uri")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (uriMatcher.match(uri) == DATA) {
            dataStorage!!.add(values)
            val id = (dataStorage!!.size - 1).toLong()
            val newUri = ContentUris.withAppendedId(CONTENT_URI, id)
            context!!.contentResolver.notifyChange(newUri, null)
            return newUri
        }
        throw IllegalArgumentException("Unsupported URI: $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // Implement deletion if needed
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        // Implement update if needed
        return 0
    }

    override fun getType(uri: Uri): String? {
        if (uriMatcher.match(uri) == DATA) {
            return "vnd.android.cursor.dir/vnd.example.data"
        }
        throw IllegalArgumentException("Unsupported URI: $uri")
    }

    companion object {
        private const val AUTHORITY = "com.example.mycontentprovider"
        private const val PATH = "data"
        val CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH)
        private const val DATA = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, PATH, DATA)
        }
    }
}
