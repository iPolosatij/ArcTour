package space.dlsunity.arctour.utils.tools

import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment
import droidninja.filepicker.FilePickerBuilder
import space.dlsunity.arctour.R

object FilePickerManager {

    fun toChoosingDocFiles(fragment: Fragment, key: Int){
        FilePickerBuilder.instance
            .setMaxCount(10) //optional
            .withOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .setActivityTheme(R.style.LibAppTheme) //optional
            .pickFile(fragment, key)
    }

    fun toChoosingMediaFiles(fragment: Fragment, key: Int){
        FilePickerBuilder.instance
            .setMaxCount(10) //optional
            .enableVideoPicker(true)
            .withOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .setActivityTheme(R.style.LibAppTheme) //optional
            .pickPhoto(fragment, key)
    }

    fun toChoosingDocFilesForOld(fragment: Fragment, key: Int, storages: space.dlsunity.arctour.data.Storages){
        FilePickerBuilder.instance
            .setMaxCount(10) //optional
            .enableDocSupport(true)
            .enableSelectAll(true)
            .showFolderView(true)
            .withOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .setActivityTheme(R.style.LibAppTheme) //optional
            .pickFile(fragment, key)
    }
}