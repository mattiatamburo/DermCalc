package com.example.dermcalc

import DataBase.DB_Manager
import DataBase.Diagnosi
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Date
import java.util.Locale

class BsaActivity : AppCompatActivity()
{
    private var calcoloEffettuato   = false
    private var bsaTotale           = 0.0
    private var statoClinico        = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bsa)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db              = DB_Manager(this)
        val idPaziente      = intent.getIntExtra("idPaziente", -1)

        val editTesta       = findViewById<EditText>    (R.id.editBsaTesta)
        val editArtiSup     = findViewById<EditText>    (R.id.editBsaArtiSup)
        val editTronco      = findViewById<EditText>    (R.id.editBsaTronco)
        val editArtiInf     = findViewById<EditText>    (R.id.editBsaArtiInf)

        val tuttiICampi     = listOf                    (editTesta, editArtiSup, editTronco, editArtiInf)

        val btnCalcolaSalva = findViewById<Button>      (R.id.btnCalcolaSalva)
        val btnModifica     = findViewById<Button>      (R.id.btnModifica)
        val btnAnnulla      = findViewById<Button>      (R.id.btn_Annulla)
        val layoutRisultato = findViewById<LinearLayout>(R.id.layoutRisultatoBsa)
        val txtRisultato    = findViewById<TextView>    (R.id.textRisultatoBsa)
        val txtSeverita     = findViewById<TextView>    (R.id.textSeveritaBsa)

        btnCalcolaSalva.setOnClickListener {
            if (!calcoloEffettuato)
            {
                if (tuttiICampi.any { it.text.toString().isEmpty() })
                {
                    Toast.makeText(this, getString(R.string.err_distretti), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                try
                {
                    val areaTesta       = editTesta     .text.toString().toDouble()
                    val areaArtiSup     = editArtiSup   .text.toString().toDouble()
                    val areaTronco      = editTronco    .text.toString().toDouble()
                    val areaArtiInf     = editArtiInf   .text.toString().toDouble()

                    val parametriArea   = listOf(areaTesta, areaArtiSup, areaTronco, areaArtiInf)

                    var haErrore = false
                    for (area in parametriArea)
                      if (area < 0.0 || area > 100.0)
                        haErrore = true

                    if (haErrore)
                    {
                        Toast.makeText(this, getString(R.string.err_perc), Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    bsaTotale = (areaTesta * 0.1) + (areaArtiSup * 0.2) + (areaTronco * 0.3) + (areaArtiInf * 0.4)

                    statoClinico =  if      (bsaTotale < 5.0)   getString(R.string.stato_Lieve)
                                    else if (bsaTotale <= 10.0) getString(R.string.stato_Moderata)
                                    else                        getString(R.string.stato_Severa)

                    txtRisultato    .text       = getString(R.string.text_RisultatoBSA  , bsaTotale)
                    txtSeverita     .text       = getString(R.string.text_GravitàBSA    ,statoClinico)
                    layoutRisultato .visibility = View.VISIBLE
                    btnCalcolaSalva .text       = getString(R.string.btn_Salva)
                    btnModifica     .visibility = View.VISIBLE

                    tuttiICampi     .forEach { it.isEnabled = false }
                    calcoloEffettuato = true

                }
                catch (e: Exception)
                {
                    Toast.makeText(this, getString(R.string.err_dati), Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                val sharedPref          = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
                val idDottore           = sharedPref.getInt("idDottore", -1)
                val idCartellaClinica   = db.getCartellaClinica(idPaziente)

                val noteDiagnosi = getString(
                                                R.string    .note_BSA,
                                                editTesta   .text.toString().toDouble(),
                                                editArtiSup .text.toString().toDouble(),
                                                editTronco  .text.toString().toDouble(),
                                                editArtiInf .text.toString().toDouble()
                                            )

                if (idPaziente != -1 && idDottore != -1)
                {
                    val nuovaDiagnosi = Diagnosi(
                        idDottore           = idDottore,
                        dataDiagnosi        = Date(),
                        tipoCalcolatore     = "BSA",
                        punteggioTotale     = bsaTotale,
                        severita            = statoClinico,
                        idCartellaClinica   = idCartellaClinica,
                        note                = noteDiagnosi
                    )

                    db.insertDiagnosi(nuovaDiagnosi)
                    finish()
                }
                else
                    Toast.makeText(this, getString(R.string.err_paz), Toast.LENGTH_SHORT).show()

            }
        }

        btnModifica.setOnClickListener {
            tuttiICampi         .forEach    { it.isEnabled = true }
            layoutRisultato     .visibility = View.GONE
            btnModifica         .visibility = View.GONE
            btnCalcolaSalva     .text       = getString(R.string.btn_Calcola)
            calcoloEffettuato               = false
        }

        btnAnnulla.setOnClickListener{
            finish()
        }
    }
}