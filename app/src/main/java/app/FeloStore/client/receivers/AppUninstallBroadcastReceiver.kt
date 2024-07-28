package app.FeloStore.client.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import app.FeloStore.client.util.getParcelableExtraCompat

class AppUninstallBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return

        when (intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -999)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                val confirmationIntent = intent
                    .getParcelableExtraCompat(Intent.EXTRA_INTENT, Intent::class.java)
                    ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                context.startActivity(confirmationIntent)
            }
        }
    }
}
