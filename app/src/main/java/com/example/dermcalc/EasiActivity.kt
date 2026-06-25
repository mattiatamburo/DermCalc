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

class EasiActivity : AppCompatActivity()
{
    private var calcoloEffettuato   = false
    private var easiTotale          = 0.0
    private var statoClinico        = 0
    private var parametriClinici    = listOf<Int>()
    private var parametriArea       = listOf<Double>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_easi)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db          = DB_Manager(this)
        val idPaziente  = intent.getIntExtra("idPaziente", -1)

        // Testa
        val eHErit      = findViewById<EditText>(R.id.editTestaEritemaEasi)
        val eHEdem      = findViewById<EditText>(R.id.editTestaEdemaEasi)
        val eHEsco      = findViewById<EditText>(R.id.editTestaEscoriazioneEasi)
        val eHLich      = findViewById<EditText>(R.id.editTestaLichenEasi)
        val eHArea      = findViewById<EditText>(R.id.editTestaAreaEasi)

        // Arti Sup
        val eUErit      = findViewById<EditText>(R.id.editArtiSupEritemaEasi)
        val eUEdem      = findViewById<EditText>(R.id.editArtiSupEdemaEasi)
        val eUEsco      = findViewById<EditText>(R.id.editArtiSupEscoriazioneEasi)
        val eULich      = findViewById<EditText>(R.id.editArtiSupLichenEasi)
        val eUArea      = findViewById<EditText>(R.id.editArtiSupAreaEasi)

        // Tronco
        val eTErit      = findViewById<EditText>(R.id.editTroncoEritemaEasi)
        val eTEdem      = findViewById<EditText>(R.id.editTroncoEdemaEasi)
        val eTEsco      = findViewById<EditText>(R.id.editTroncoEscoriazioneEasi)
        val eTLich      = findViewById<EditText>(R.id.editTroncoLichenEasi)
        val eTArea      = findViewById<EditText>(R.id.editTroncoAreaEasi)

        // Arti Inf
        val eLErit      = findViewById<EditText>(R.id.editArtiInfEritemaEasi)
        val eLEdem      = findViewById<EditText>(R.id.editArtiInfEdemaEasi)
        val eLEsco      = findViewById<EditText>(R.id.editArtiInfEscoriazioneEasi)
        val eLLich      = findViewById<EditText>(R.id.editArtiInfLichenEasi)
        val eLArea      = findViewById<EditText>(R.id.editArtiInfAreaEasi)

        val campiClinici = listOf(
            eHErit, eHEdem, eHEsco, eHLich,
            eUErit, eUEdem, eUEsco, eULich,
            eTErit, eTEdem, eTEsco, eTLich,
            eLErit, eLEdem, eLEsco, eLLich
        )

        val campiArea = listOf(
            eHArea, eUArea, eTArea, eLArea
        )

        val tuttiICampi = campiClinici + campiArea

        val btnCalcolaSalva = findViewById<Button>      (R.id.btnCalcolaSalvaEasi)
        val btnModifica     = findViewById<Button>      (R.id.btnModificaEasi)
        val btnAnnulla      = findViewById<Button>      (R.id.btn_Annulla)
        val layoutRisultato = findViewById<LinearLayout>(R.id.layoutRisultatoEasi)
        val txtRisultato    = findViewById<TextView>    (R.id.txtRisultatoEasi)
        val txtSeverita     = findViewById<TextView>    (R.id.txtSeveritaEasi)

        btnCalcolaSalva.setOnClickListener {
            if (!calcoloEffettuato)
            {
                var hasError = false

                for (campo in campiClinici)
                {
                    val testo = campo.text.toString().trim()
                    if (testo.isEmpty())
                    {
                        campo.error = getString(R.string.err_campi)
                        hasError = true
                    }
                    else
                    {
                        val valore = testo.toIntOrNull()
                        if (valore == null || valore !in 0..3)
                        {
                            campo.error = getString(R.string.err_parametriEASI)
                            hasError = true
                        }
                    }
                }

                for (campo in campiArea)
                {
                    val testo = campo.text.toString().trim()
                    if (testo.isEmpty())
                    {
                        campo.error = getString(R.string.err_campi)
                        hasError = true
                    }
                    else
                    {
                        val valore = testo.toDoubleOrNull()
                        if (valore == null || valore !in 0.0..100.0)
                        {
                            campo.error = getString(R.string.err_area)
                            hasError = true
                        }
                    }
                }

                if (hasError)
                  return@setOnClickListener

                // Testa (H)
                val hE      = eHErit.text.toString().toInt()
                val hI      = eHEdem.text.toString().toInt()
                val hEx     = eHEsco.text.toString().toInt()
                val hL      = eHLich.text.toString().toInt()
                val hArea   = eHArea.text.toString().toDouble()

                // Arti Sup (U)
                val uE      = eUErit.text.toString().toInt()
                val uI      = eUEdem.text.toString().toInt()
                val uEx     = eUEsco.text.toString().toInt()
                val uL      = eULich.text.toString().toInt()
                val uArea   = eUArea.text.toString().toDouble()

                // Tronco (T)
                val tE      = eTErit.text.toString().toInt()
                val tI      = eTEdem.text.toString().toInt()
                val tEx     = eTEsco.text.toString().toInt()
                val tL      = eTLich.text.toString().toInt()
                val tArea   = eTArea.text.toString().toDouble()

                // Arti Inf (L)
                val lE      = eLErit.text.toString().toInt()
                val lI      = eLEdem.text.toString().toInt()
                val lEx     = eLEsco.text.toString().toInt()
                val lL      = eLLich.text.toString().toInt()
                val lArea   = eLArea.text.toString().toDouble()

                parametriClinici    = listOf(hE, hI, hEx, hL, uE, uI, uEx, uL, tE, tI, tEx, tL, lE, lI, lEx, lL)
                parametriArea       = listOf(hArea, uArea, tArea, lArea)

                val hScore = 0.1 * (hE + hI + hEx + hL) * calcolaPunteggioArea(hArea)
                val uScore = 0.2 * (uE + uI + uEx + uL) * calcolaPunteggioArea(uArea)
                val tScore = 0.3 * (tE + tI + tEx + tL) * calcolaPunteggioArea(tArea)
                val lScore = 0.4 * (lE + lI + lEx + lL) * calcolaPunteggioArea(lArea)

                easiTotale  = hScore + uScore + tScore + lScore

                statoClinico =  if      (easiTotale < 7.0)      0
                                else if (easiTotale <= 21.0)    1
                                else                            2

                txtRisultato    .text       = getString(R.string.text_RisultatoEASI,    easiTotale)
                txtSeverita     .text       = getString(R.string.text_GravitàBSA,       traduciStato(statoClinico))
                layoutRisultato .visibility = View.VISIBLE
                btnCalcolaSalva .text       = getString(R.string.btn_Salva)
                btnModifica     .visibility = View.VISIBLE

                tuttiICampi.forEach { it.isEnabled = false }
                calcoloEffettuato = true

            }
            else
            {
                val sharedPref          = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
                val idDottore           = sharedPref.getInt("idDottore", -1)
                val idCartellaClinica   = db.getCartellaClinica(idPaziente)

                val noteDiagnosi        = (parametriClinici.map { it.toString() } + parametriArea.map { it.toString() }).joinToString("|")

                if (idPaziente != -1 && idDottore != -1)
                {
                    val nuovaDiagnosi = Diagnosi(
                        idDottore           = idDottore,
                        dataDiagnosi        = Date(),
                        tipoCalcolatore     = "EASI",
                        punteggioTotale     = easiTotale,
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
            tuttiICampi     .forEach { it.isEnabled = true }
            layoutRisultato .visibility = View.GONE
            btnModifica     .visibility = View.GONE
            btnCalcolaSalva .text       = getString(R.string.btn_Salva)
            calcoloEffettuato           = false
        }

        btnAnnulla.setOnClickListener{
            finish()
        }
    }

    private fun calcolaPunteggioArea(percentuale: Double): Int
    {
        if      (percentuale == 0.0)    return 0
        else if (percentuale < 10.0)    return 1
        else if (percentuale < 30.0)    return 2
        else if (percentuale < 50.0)    return 3
        else if (percentuale < 70.0)    return 4
        else if (percentuale < 90.0)    return 5
        else                            return 6
    }

    private fun traduciStato(stato: Int): String
    {
        if        (stato==0)  return getString(R.string.stato_Lieve)
        else if   (stato==1)  return getString(R.string.stato_Moderata)
        else                  return getString(R.string.stato_Severa)
    }
}