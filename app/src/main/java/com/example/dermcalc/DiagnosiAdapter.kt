package com.example.dermcalc

import DataBase.Diagnosi
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class DiagnosiAdapter(private var listaDiagnosi: List<Diagnosi>) : RecyclerView.Adapter<DiagnosiAdapter.DiagnosiViewHolder>()
{
    private var filteredList: List<Diagnosi> = listaDiagnosi

    fun updateData(nuovaLista: List<Diagnosi>)
    {
        listaDiagnosi = nuovaLista
        filteredList = nuovaLista
        notifyDataSetChanged()
    }
    class DiagnosiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val texttipoCalcolatore     : TextView  = itemView.findViewById(R.id.textTipoCalcolatore)
        val textDataDiagnosi        : TextView  = itemView.findViewById(R.id.textDataDiagnosi)
        val textSeverita            : TextView  = itemView.findViewById(R.id.textSeverita)
        val textID                  : TextView  = itemView.findViewById(R.id.textFieldID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnosiViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diagnosi, parent, false)
        return DiagnosiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiagnosiViewHolder, index: Int)
    {
        val DiagnosiCorrente    = filteredList[index]
        val formatter           = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY)

        holder.texttipoCalcolatore  .text =                         DiagnosiCorrente.tipoCalcolatore
        holder.textDataDiagnosi     .text =                         formatter       .format(DiagnosiCorrente.dataDiagnosi)
        holder.textSeverita         .text =                         traduciSeverita (holder.itemView.context, DiagnosiCorrente.severita, DiagnosiCorrente.tipoCalcolatore)
        holder.textID               .text =                         DiagnosiCorrente.idDiagnosi.toString()

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent  = Intent(context, DettaglioDiagnosiActivity::class.java)
            intent.putExtra("idDiagnosi", DiagnosiCorrente.idDiagnosi)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int
    {
        return filteredList.size
    }

    fun filter(query: String)
    {
        filteredList = if(query.isEmpty()) listaDiagnosi else {
            val searchTerms = query.trim().split("\\s+".toRegex())

            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY)

            listaDiagnosi.filter { item ->
                val tipoCalcolatore = item.tipoCalcolatore

                val dataDiagnosi    = formatter.format(item.dataDiagnosi)
                val id              = item.idDiagnosi.toString()

                searchTerms.all { term ->
                    tipoCalcolatore .contains(term, ignoreCase = true) ||
                    dataDiagnosi    .contains(term, ignoreCase = true) ||
                    id              .contains(term, ignoreCase = true)
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun traduciSeverita(context: android.content.Context, severita: Int, calcolatore: String): String
    {
        if(calcolatore == "BMI")
        {
            if      (severita==0)   return context.getString(R.string.stato_Sottopeso)
            else if (severita==1)   return context.getString(R.string.stato_Normopeso)
            else if (severita==2)   return context.getString(R.string.stato_Sovrappeso)
            else                    return context.getString(R.string.stato_Obesità)
        }
        else
        {
            if      (severita==0)   return context.getString(R.string.stato_Lieve)
            else if (severita==1)   return context.getString(R.string.stato_Moderata)
            else                    return context.getString(R.string.stato_Severa)
        }
    }
}
