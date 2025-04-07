package com.example.tp3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp3.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {

    lateinit var binding: ActivityCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAppeler.setOnClickListener {
            val numero = binding.editTextNumero.text.toString()

            if (numero.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$numero")
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Veuillez entrer un numéro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}