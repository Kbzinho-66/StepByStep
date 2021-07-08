package com.example.stepbystep.ui.adapters

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.PassoDAO
import com.example.stepbystep.data.entities.Passo
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

        fun setPasso(passo: Passo) {

            if (passo.cronometrado) {

//                    (layoutBinding as CronometradoBinding).apply{
//                        passoItem = passo
//                        passos.removeAt(posicao)
//                        db.deletarPasso(passo)
//                        notifyItemRemoved(adapterPosition)
//                        TODO(Implementar o Timer)
//                    }

            } else {

                (layoutBinding as NormalBinding).apply{
                    passoItem = passo
                    passoBotaoDeletar.setOnClickListener {
                        passos.remove(passo)

                        try { db.deletarPasso(passo) } catch (erro: SQLiteConstraintException) {
                            Log.e(erro.toString(), "Erro ao deletar um passo")
                        }

                        notifyItemRemoved(adapterPosition)
                    }
                }
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassoHolder {
        //TODO (Fazer retornar os dois tipos de Passos)
        return PassoHolder(
            NormalBinding
                .inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }


    override fun onBindViewHolder(holder: PassoHolder, posicao: Int) {
        holder.setPasso(passos[posicao])
    }

    override fun getItemCount(): Int = passos.size
}