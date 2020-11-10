package com.bitxflow.funny.send

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SystemKeyEventReceiver(private val mListener: SystemKeyEventReceiverListener): BroadcastReceiver() {
    private val TAG = "KeyEventReceiver"

    private val SYSTEM_DIALOG_REASON_KEY = "reason"
    private val SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps"
    private val SYSTEM_DIALOG_REASON_HOME_KEY = "homekey"
    private val SYSTEM_DIALOG_REASON_LOCK = "lock"
    private val SYSTEM_DIALOG_REASON_ASSIST = "assist"

    override fun onReceive(ctx: Context, intent: Intent) {
        intent.action?.let {

            if (it == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
                // android.intent.action.CLOSE_SYSTEM_DIALOGS
                val reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY)
                Log.i(TAG, "reason: $reason")

                when(reason) {
                    // Home 키를 짧게 누르면
                    SYSTEM_DIALOG_REASON_HOME_KEY -> mListener.onPressHomeKey()
                    // 하위 SDK에서 Home 키를 오랫동안 누르거나 히스토리 키 누를 시
                    // TODO : note8에서 resent키를 눌렀다가 다시 resent키를 누르면 home action이 옴. os별로 다른지 기종별로 다른지 확인이 필요함.
                    SYSTEM_DIALOG_REASON_RECENT_APPS -> mListener.onLongPressHomeKey()
                    // 화면 잠그기
                    // TODO : note8에서 테스트 시 lock 대신 dream이라는 action을 받음 os별로 다른지 기종별로 다른지 확인이 필요함.
                    SYSTEM_DIALOG_REASON_LOCK -> mListener.onScreenLock()
                    // Home 키를 길게 누르면 어시스턴트 나오는거
                    SYSTEM_DIALOG_REASON_ASSIST -> mListener.onPressAssist()
                }
            }
        }
    }

    interface SystemKeyEventReceiverListener {
        fun onPressHomeKey()
        fun onLongPressHomeKey()
        fun onScreenLock()
        fun onPressAssist()
    }
}