package com.example.dermcalc

import DataBase.DB_Manager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textField_Email = findViewById<EditText>(R.id.textFieldEmail)
        val textField_Password = findViewById<EditText>(R.id.textFieldPassword)
        val btn_Login = findViewById<Button>(R.id.btn_login)
        val db = DB_Manager(this)

        btn_Login.setOnClickListener {
            val username = textField_Email.text.toString()
            val password = textField_Password.text.toString()

            val accesso = db.checkLogin(username, password)
            val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
            println(accesso)
            if (accesso != null) {
                sharedPref.edit {
                    putBoolean("isLoggedIn", true)
                    putInt("idDottore", accesso.idDottore)
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                sharedPref.edit { putBoolean("isLoggedIn", false) }
                Toast.makeText(this, "Credenziali errate", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
