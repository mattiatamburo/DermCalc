package com.example.dermcalc

import DataBase.Diagnosi
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiagnosiAdapter(private var listaDiagnosi: List<Diagnosi>) : RecyclerView.Adapter<DiagnosiAdapter.DiagnosiViewHolder>()
{
    private var filteredList: List<Diagnosi> = listaDiagnosi
    class DiagnosiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val texttipoCalcolatore     : TextView  = itemView.findViewById(R.id.textTipoCalcolatore)
        val textDataDiagnosi        : TextView  = itemView.findViewById(R.id.textDataDiagnosi)
        val textSeverita            : TextView  = itemView.findViewById(R.id.textSeverita)
        val textID                  : TextView  = itemView.findViewById(R.id.textFieldID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnosiViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paziente, parent, false)
        return DiagnosiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiagnosiViewHolder, index: Int)
    {
        val DiagnosiCorrente = filteredList[index]

        holder.texttipoCalcolatore  .text = DiagnosiCorrente.tipoCalcolatore
        holder.textDataDiagnosi     .text = DiagnosiCorrente.dataDiagnosi   .toString()
        holder.textSeverita         .text = DiagnosiCorrente.severita
        holder.textID               .text = DiagnosiCorrente.idDiagnosi     .toString()

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent  = Intent(context, ProfiloPaziente::class.java)
            intent.putExtra("idPaziente", DiagnosiCorrente.idDiagnosi)
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

            listaDiagnosi.filter { item ->

                val tipoCalcolatore = item.tipoCalcolatore
                val dataDiagnosi    = item.dataDiagnosi     .toString()
                val id              = item.idDiagnosi       .toString()

                searchTerms.all { term ->
                    tipoCalcolatore .contains(term, ignoreCase = true) ||
                    dataDiagnosi    .contains(term, ignoreCase = true) ||
                    id              .contains(term, ignoreCase = true)
                }
            }
        }
        notifyDataSetChanged()
    }
}
