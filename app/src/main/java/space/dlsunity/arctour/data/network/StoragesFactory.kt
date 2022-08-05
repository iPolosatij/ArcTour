package space.dlsunity.arctour.data.network

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

class StoragesFactory {
    fun createNew(context: Context): space.dlsunity.arctour.data.Storages {
        val externalStoragePath = File(externalStorage(), "ArcTour").absolutePath
        val cacheDir: File = context.cacheDir
        val cachePath = File(cacheDir, "tmp-images").absolutePath
        val filesDir: File = context.filesDir
        val filesPath = filesDir.absolutePath
        return space.dlsunity.arctour.data.Storages.StoragesBuilder()
            .setExternalStoragePath(externalStoragePath)
            .setCachePath(cachePath)
            .setFilesDir(filesPath)
            .build()
    }

    private fun externalStorage(): File{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        else
            Environment.getExternalStorageDirectory()
    }
}
