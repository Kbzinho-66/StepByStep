package com.example.stepbystep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.stepbystep.R
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.PassoDAO
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.data.entities.PassoCronometrado
import com.example.stepbystep.data.entities.PassoNormal
import com.example.stepbystep.databinding.ReceitasPassoCronometradoBinding as CronometradoBinding
import com.example.stepbystep.databinding.ReceitasPassoItemBinding as NormalBinding

/**
 * Classe que adapta qualquer implementação de um [Passo]
 * para ser exibida na RecyclerView em [com.example.stepbystep.ui.receitas.PassosFragmento].
 */

class AdapterPasso(
    private val passos: MutableList<Passo>,
    private val contexto: Context
) : RecyclerView.Adapter<AdapterPasso.PassoHolder>() {

    private val db: PassoDAO by lazy {
        AppDatabase.getInstance(contexto).passoDAO()
    }

    inner class PassoHolder(private val layoutBinding: ViewBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun setPasso(passo: Passo) {

            when (passo) {

                is PassoNormal -> {
                    with(layoutBinding as NormalBinding) {
                        passoCheckbox.isChecked = passo.pronto
                        (passoDescricao as TextView).text = passo.descricao
                        passoBotaoDeletar.setOnClickListener {
                            db.deletarPasso(passo.idPasso)
                        }
                    }
                }
                is PassoCronometrado -> {
                    with(layoutBinding as CronometradoBinding) {
                        (passoDescricao as TextView).text = passo.descricao
                        passoBotaoDeletar.setOnClickListener {
                            db.deletarPasso(passo.idPasso)
                        }
                        passoProgresso.max = (passo.duracao / 1000).toInt()
                        passoIniciarTimer.setOnClickListener {
                            TODO("Implementar o timer")
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AdapterPasso.PassoHolder {

        val layoutBinding = when (viewType) {
            R.layout.receitas_passo_item -> NormalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            R.layout.receitas_passo_cronometrado -> CronometradoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> throw Error()
        }

        return PassoHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: AdapterPasso.PassoHolder, position: Int) {

        val passo = passos[position]

        holder.setPasso(passo)
    }

    override fun getItemCount(): Int = passos.size
}