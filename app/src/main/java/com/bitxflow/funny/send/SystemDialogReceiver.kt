package com.bitxflow.funny.send

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


internal class SystemDialogReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
            val dialogType =
                intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY)
            if (dialogType != null && dialogType == SYSTEM_DIALOG_REASON_RECENT_APPS) {
                val closeDialog = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                context.sendBroadcast(closeDialog)
            }
        }
    }

    companion object {
        private const val SYSTEM_DIALOG_REASON_KEY = "reason"
        private const val SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps"
    }
}