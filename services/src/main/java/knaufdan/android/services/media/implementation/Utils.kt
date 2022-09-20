package knaufdan.android.services.media.implementation

import android.content.Context
import android.util.Log
import java.io.File
import java.util.Date

val defaultFileName: String
    get() = System.currentTimeMillis().toString() + DEFAULT_FILE_SUFFIX

fun Context.createFileImage(): File = createFile(storageDir = filesDir)

fun Context.createCacheImage(): File = createFile(storageDir = cacheDir)

fun findOrCreateFile(file: File) {
    if (!file.exists() && !file.mkdirs()) {
        Log.e("MediaUtils", "Failed to create directory for path == ${file.absolutePath}")
    }
}

private fun createFile(
    storageDir: File,
    suffix: String = DEFAULT_FILE_SUFFIX
): File {
    findOrCreateFile(storageDir)

    return File.createTempFile(
        Date().time.toString(),
        suffix,
        storageDir
    ).apply {
        createNewFile()
        deleteOnExit()
    }
}

private const val DEFAULT_FILE_SUFFIX = ".jpeg"
