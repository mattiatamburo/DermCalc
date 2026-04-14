package com.example.dermcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.app.Service
import android.widget.TextView
import android.widget.Button
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //se l'utente non ha già fatto l'accesso passare prima per la LoginActivity
        val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        if (!isLoggedIn) {
            finish()
            return
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.upperToolBar)
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        btnHome.setOnClickListener {
            //portare alla pagina home
            val intent = Intent(this, MainActivity::class.java)
        }
        val txtNome = findViewById<TextView>(R.id.txtName)
        val btnProfilo = findViewById<ImageButton>(R.id.btnProfilo)
        txtNome.setText("Mauro Rossi")
    }
}