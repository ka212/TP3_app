package com.example.tp3

import android.os.Bundle
import android.net.Uri
import android.content.Intent

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp3.databinding.ActivityConfirmSmsBinding


class ConfirmSMS : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmSmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfirmSmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)){ v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            val numero = intent.getStringExtra("numero")
            val message = intent.getStringExtra("message")

            binding.numconfirme.setText(numero)
            binding.messageconfime.setText(message)

            binding.envoyer.setOnClickListener {
                val num = binding.numconfirme.text.toString()
                val msg = binding.messageconfime.text.toString()

                val uri = Uri.parse("smsto:$num")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", msg)
                startActivity(intent)
            }

            binding.annuler.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }
