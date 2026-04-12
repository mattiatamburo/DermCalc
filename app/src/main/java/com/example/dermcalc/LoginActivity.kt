package com.example.dermcalc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dermcalc.ui.theme.DermCalcTheme

class LoginActivity : ComponentActivity()
{
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    val textField_Email    = findViewById<TextView>(R.id.textFieldEmail)
    val textField_Password = findViewById<TextView>(R.id.textFieldPassword)
    val btn_Login          = findViewById<TextView>(R.id.btn_login)

    btn_Login.setOnClickListener{
                                    //controllo credenziali

                                }
  }
}

