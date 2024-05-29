package `in`.dazzlingapps.senderapp

import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.explicitIntent).setOnClickListener {
            val intent = Intent()
            intent.putExtra("data", "sending test data- Hello world")
            intent.component = ComponentName("in.dazzlingapps.receiverapp", "in.dazzlingapps.receiverapp.MainActivity")
            startActivity(intent)
        }

        findViewById<Button>(R.id.implicitIntent).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Data from another app")
            startActivity(Intent.createChooser(intent, "Share via"))
        }

        findViewById<Button>(R.id.broadcastButton).setOnClickListener {
            val intent = Intent("android.example.CUSTOM_INTENT")
            intent.putExtra("key", "value")
            sendBroadcast(intent)
        }
    }
}