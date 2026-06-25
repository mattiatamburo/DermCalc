package com.example.dermcalc

import DataBase.DB_Manager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class ProfiloActivity : AppCompatActivity()
{
    override fun onResume()
    {
        super.onResume()

        setDatiDottore()
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profilo_doc)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnHome             = findViewById<ImageButton> (R.id.btnHome)
        val btnModifica         = findViewById<Button>      (R.id.btnModifica)
        val btnLogout           = findViewById<Button>      (R.id.btnLogout)
        val btnProfilo          = findViewById<ImageButton> (R.id.btnProfilo)
        val sharedPref          = getSharedPreferences      ("DermCalcPrefs", MODE_PRIVATE)

        btnHome.setImageResource(R.drawable.ic_arrow_back)
        btnProfilo.isClickable  = false
        btnProfilo.isFocusable  = false
        btnProfilo.background   = null

        setDatiDottore()

        btnHome.setOnClickListener {
            finish()
        }

        btnModifica.setOnClickListener {
            val intent = Intent(this, ModificaDatiPersonali::class.java)
            startActivity(intent)
        }


        btnLogout.setOnClickListener {
            sharedPref.edit {
                putBoolean("isLoggedIn", false)
                putInt("idDottore", -1)
            }
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun setDatiDottore()
    {
        val sharedPref          = getSharedPreferences      ("DermCalcPrefs", MODE_PRIVATE)
        val txtUpperName        = findViewById<TextView>    (R.id.txtName)
        val textViewCellulare   = findViewById<TextView>    (R.id.textViewCellulare)
        val textViewCodFiscale  = findViewById<TextView>    (R.id.textViewCodFiscale)
        val textViewEmail       = findViewById<TextView>    (R.id.textViewEmail)
        val textViewDataNascita = findViewById<TextView>    (R.id.textViewDataNascita)

        val db                  = DB_Manager(this)
        val id                  = sharedPref.getInt("idDottore", -1)
        val dottore             = db.getDottoreById(id)

        if (dottore != null)
        {
            txtUpperName        .text = "${dottore.nome.lowercase().replaceFirstChar { it.uppercase() }} ${dottore.cognome.lowercase().replaceFirstChar { it.uppercase() }}"
            textViewCellulare   .text = dottore.cellulare
            textViewCodFiscale  .text = dottore.codFiscale
            textViewEmail       .text = dottore.email

            // Formattazione della data
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
            textViewDataNascita.text = formatter.format(dottore.dataNascita)
        }
    }
}
