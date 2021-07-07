package com.example.stepbystep.ui.receitas

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.stepbystep.R
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.PassoDAO
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.databinding.ReceitasPassoCronometradoBinding as CronometradoBinding
import com.example.stepbystep.databinding.ReceitasPassoItemBinding as NormalBinding

/**
 * Classe que adapta qualquer um [Passo]
 * para ser exibida na RecyclerView em [com.example.stepbystep.ui.receitas.PassosFragmento].
 *
 * Por enquanto só trata com Passos normais, mas no futuro precisa lidar com passos
 * cronometrados também.
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

//        fun setPasso(passo: Passo) {
//
//            if  (passo.cronometrado) {
//
//                with(layoutBinding as NormalBinding) {
//                    passoCheckbox.isChecked = passo.pronto
//                    (passoDescricao as TextView).text = passo.descricao
//                    passoBotaoDeletar.setOnClickListener {
//                        db.deletarPasso(passo)
//                    }
//                }
//
//            } else {
//
//                with(layoutBinding as CronometradoBinding) {
//                    (passoDescricao as TextView).text = passo.descricao
//                    passoBotaoDeletar.setOnClickListener {
//                        db.deletarPasso(passo)
//                    }
//                    passoProgresso.max = (passo.duracao / 1000).toInt()
//                    passoIniciarTimer.setOnClickListener {
//                        TODO("Implementar o timer")
//                    }
//                }
//            }
//        }

            fun setPasso(passo: Passo) {
                if (passo.cronometrado) {
//                    (layoutBinding as CronometradoBinding).passoItem = passo
                } else {
                    (layoutBinding as NormalBinding).passoItem = passo
                }

                //TODO (Colocar os onClickListeners nos botões)
            }
    }

    //TODO (Descobrir como fazer isso aqui direito, no momento dá erro no Layout)
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
//            PassoHolder {
//
//        val layoutBinding = when (viewType) {
//            R.layout.receitas_passo_item -> NormalBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//            R.layout.receitas_passo_cronometrado -> CronometradoBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//            else -> throw Error()
//        }
//
//        return PassoHolder(layoutBinding)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassoHolder =
         PassoHolder(NormalBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: PassoHolder, position: Int) {
        holder.setPasso(passos[position])
    }

    override fun getItemCount(): Int = passos.size
}