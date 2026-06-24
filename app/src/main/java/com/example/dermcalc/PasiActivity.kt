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

class PasiActivity : AppCompatActivity()
{
    private var calcoloEffettuato   = false
    private var pasiTotale          = 0.0
    private var statoClinico        = ""
    private var parametriClinici    = listOf<Int>()
    private var parametriArea       = listOf<Double>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pasi)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db          = DB_Manager(this)
        val idPaziente  = intent.getIntExtra("idPaziente", -1)

        // Recupero campi Testa (H)
        val editTestaE = findViewById<EditText>(R.id.editTestaEritema)
        val editTestaI = findViewById<EditText>(R.id.editTestaIndurimento)
        val editTestaD = findViewById<EditText>(R.id.editTestaDesquamazione)
        val editTestaA = findViewById<EditText>(R.id.editTestaArea)

        // Recupero campi Arti Superiori (U)
        val editArtiSupE = findViewById<EditText>(R.id.editArtiSupEritema)
        val editArtiSupI = findViewById<EditText>(R.id.editArtiSupIndurimento)
        val editArtiSupD = findViewById<EditText>(R.id.editArtiSupDesquamazione)
        val editArtiSupA = findViewById<EditText>(R.id.editArtiSupArea)

        // Recupero campi Tronco (T)
        val editTroncoE = findViewById<EditText>(R.id.editTroncoEritema)
        val editTroncoI = findViewById<EditText>(R.id.editTroncoIndurimento)
        val editTroncoD = findViewById<EditText>(R.id.editTroncoDesquamazione)
        val editTroncoA = findViewById<EditText>(R.id.editTroncoArea)

        // Recupero campi Arti Inferiori (L)
        val editArtiInfE = findViewById<EditText>(R.id.editArtiInfEritema)
        val editArtiInfI = findViewById<EditText>(R.id.editArtiInfIndurimento)
        val editArtiInfD = findViewById<EditText>(R.id.editArtiInfDesquamazione)
        val editArtiInfA = findViewById<EditText>(R.id.editArtiInfArea)


        val tuttiICampi = listOf(
            editTestaE,     editTestaI,     editTestaD,     editTestaA,
            editArtiSupE,   editArtiSupI,   editArtiSupD,   editArtiSupA,
            editTroncoE,    editTroncoI,    editTroncoD,    editTroncoA,
            editArtiInfE,   editArtiInfI,   editArtiInfD,   editArtiInfA
        )

        val btnCalcolaSalva     = findViewById<Button>      (R.id.btnCalcolaSalva)
        val btnModifica         = findViewById<Button>      (R.id.btnModifica)
        val btnAnnulla          = findViewById<Button>      (R.id.btn_Annulla)
        val layoutRisultato     = findViewById<LinearLayout>(R.id.layoutRisultatoPasi)
        val txtRisultato        = findViewById<TextView>    (R.id.txtRisultatoPasi)
        val txtSeverita         = findViewById<TextView>    (R.id.txtSeveritaPasi)

        btnCalcolaSalva.setOnClickListener {
            if (!calcoloEffettuato)
            {
                if (tuttiICampi.any { it.text.toString().isEmpty() })
                {
                    Toast.makeText(this, getString(R.string.err_campi), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                try
                {
                    // Lettura valori Testa
                    val tE                  = editTestaE.text.toString().toInt()
                    val tI                  = editTestaI.text.toString().toInt()
                    val tD                  = editTestaD.text.toString().toInt()
                    val tAreaPercentuale    = editTestaA.text.toString().toDouble()

                    // Lettura valori Arti Superiori
                    val uE                  = editArtiSupE.text.toString().toInt()
                    val uI                  = editArtiSupI.text.toString().toInt()
                    val uD                  = editArtiSupD.text.toString().toInt()
                    val uAreaPercentuale    = editArtiSupA.text.toString().toDouble()

                    // Lettura valori Tronco
                    val trE                 = editTroncoE.text.toString().toInt()
                    val trI                 = editTroncoI.text.toString().toInt()
                    val trD                 = editTroncoD.text.toString().toInt()
                    val trAreaPercentuale   = editTroncoA.text.toString().toDouble()

                    // Lettura valori Arti Inferiori
                    val lE                  = editArtiInfE.text.toString().toInt()
                    val lI                  = editArtiInfI.text.toString().toInt()
                    val lD                  = editArtiInfD.text.toString().toInt()
                    val lAreaPercentuale    = editArtiInfA.text.toString().toDouble()

                    parametriClinici    = listOf(tE, tI, tD, uE, uI, uD, trE, trI, trD, lE, lI, lD)
                    parametriArea       = listOf(tAreaPercentuale, uAreaPercentuale, trAreaPercentuale, lAreaPercentuale)

                    if (parametriClinici.any { it !in 0..4 })
                    {
                        Toast.makeText(this, getString(R.string.err_parametri), Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    if (parametriArea.any { it !in 0.0..100.0 })
                    {
                        Toast.makeText(this, getString(R.string.err_area), Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    val tA  = calcolaPunteggioArea(tAreaPercentuale)
                    val uA  = calcolaPunteggioArea(uAreaPercentuale)
                    val trA = calcolaPunteggioArea(trAreaPercentuale)
                    val lA  = calcolaPunteggioArea(lAreaPercentuale)

                    val testaScore      = 0.1 * (tE + tI + tD) * tA
                    val artiSupScore    = 0.2 * (uE + uI + uD) * uA
                    val troncoScore     = 0.3 * (trE + trI + trD) * trA
                    val artiInfScore    = 0.4 * (lE + lI + lD) * lA

                    pasiTotale   = testaScore + artiSupScore + troncoScore + artiInfScore

                    statoClinico =  if      (pasiTotale < 5.0)      getString(R.string.stato_Lieve)
                                    else if (pasiTotale <= 10.0)    getString(R.string.stato_Moderata)
                                    else                            getString(R.string.stato_Severa)

                    txtRisultato    .text       = getString(R.string.text_RisultatoPASI,    pasiTotale)
                    txtSeverita     .text       = getString(R.string.text_SeveritaPASI,     statoClinico)
                    layoutRisultato .visibility = View.VISIBLE
                    btnCalcolaSalva .text       = getString(R.string.btn_Salva)
                    btnModifica     .visibility = View.VISIBLE

                    tuttiICampi.forEach { it.isEnabled = false }

                    calcoloEffettuato = true

                }
                catch (e: Exception)
                {
                    Toast.makeText(this, getString(R.string.err_lettura), Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                val sharedPref          = getSharedPreferences  ("DermCalcPrefs", MODE_PRIVATE)
                val idDottore           = sharedPref.getInt     ("idDottore", -1)

                val idCartellaClinica   = db.getCartellaClinica(idPaziente)


                val noteDiagnosi = getString(
                    R.string.nota_diagnosi_PASI,

                    parametriClinici[0], parametriClinici[1],   parametriClinici[2],
                    parametriClinici[3], parametriClinici[4],   parametriClinici[5],
                    parametriClinici[6], parametriClinici[7],   parametriClinici[8],
                    parametriClinici[9], parametriClinici[10],  parametriClinici[11],

                    parametriArea[0],
                    parametriArea[1],
                    parametriArea[2],
                    parametriArea[3]
                )

                if (idPaziente != -1 && idDottore != -1)
                {
                    val nuovaDiagnosi = Diagnosi(
                        idDottore           = idDottore,
                        dataDiagnosi        = Date(),
                        tipoCalcolatore     = "PASI",
                        punteggioTotale     = pasiTotale,
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
            tuttiICampi.forEach { it.isEnabled = true }

            layoutRisultato .visibility = View.GONE
            btnModifica     .visibility = View.GONE
            btnCalcolaSalva .text       = getString(R.string.btn_Modifica)
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
}
