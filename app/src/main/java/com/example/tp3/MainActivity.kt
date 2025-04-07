package com.example.tp3

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.Manifest
import android.telephony.SmsManager
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tp3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val REQUEST_SMS_PERMISSION = 102

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.appelurgence -> {
                val numero = resources.getString(R.string.numero_urgence)
                val intent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse("tel:$numero")
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        1
                    )
                }
                return true
            }

            R.id.appelnormal -> {
                val intent = Intent(this, CallActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.smsnormal -> {
                val intent = Intent(this, WriteSMS::class.java)
                startActivity(intent)
                return true
            }

            R.id.camera -> {
                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
                return true
            }



            R.id.smsurgence -> {
                sendEmergencySMS()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendEmergencySMS() {
        val phone = resources.getString(R.string.numero_urgence)
        val message = resources.getString(R.string.message_sms_urgence)

        val requiredPermissions = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE
        )

        val allPermissionsGranted = requiredPermissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }

        if (allPermissionsGranted) {
            sendSMS(phone, message)
        } else {
            ActivityCompat.requestPermissions(
                this,
                requiredPermissions,
                REQUEST_SMS_PERMISSION
            )
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(
                phoneNumber,
                null,
                message,
                null,
                null
            )
            Toast.makeText(this, "SMS d'urgence envoyé", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Échec d'envoi du SMS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
