package space.dlsunity.arctour.utils.tools

import android.app.Activity
import android.app.AlertDialog
import space.dlsunity.arctour.databinding.AlertDialogFrameBinding
import space.dlsunity.arctour.databinding.ProgressDialogLayoutBinding

object DialogHelper {

    fun createClosingDialog(act: Activity, message: String) {
        val builder = AlertDialog.Builder(act)
        val binding = AlertDialogFrameBinding.inflate(act.layoutInflater)
        val view = binding.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.show()
        binding.apply {
            messageText.text = message
            closeDialog.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    fun createClosingDialog(act: Activity):AlertDialog{
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = ProgressDialogLayoutBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }
}