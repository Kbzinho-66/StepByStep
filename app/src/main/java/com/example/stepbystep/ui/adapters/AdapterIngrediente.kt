package com.example.stepbystep.ui.adapters

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.IngredienteDAO
import com.example.stepbystep.data.entities.Ingrediente
import com.example.stepbystep.databinding.ReceitasIngredienteItemBinding as IngredienteBinding

/**
 * Classe que adapta um [Ingrediente] para
 * a RecyclerView em [com.example.stepbystep.ui.receitas.IngredientesFragmento].
 *
 * @param ingredientes A lista dos ingredientes a serem adaptados
 */

class AdapterIngrediente(
    private val ingredientes: MutableList<Ingrediente>,
    private val contexto: Context
) : RecyclerView.Adapter<AdapterIngrediente.IngredienteHolder>() {

    private val db: IngredienteDAO by lazy {
        AppDatabase.getInstance(contexto).ingredienteDAO()
    }

    inner class IngredienteHolder(private val layoutBinding: IngredienteBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun setIngrediente(ingrediente: Ingrediente) {


            layoutBinding.apply {
                itemIngrediente = ingrediente

                ingredienteBotaoDeletar.setOnClickListener {
                    ingredientes.remove(ingrediente)
                    try { db.deletarIngrediente(ingrediente) } catch (erro: SQLiteConstraintException) {
                        Log.e(erro.toString(), "Erro ao deletar um ingrediente")
                    }
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredienteHolder =
        IngredienteHolder(IngredienteBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        )

    override fun onBindViewHolder(holder: IngredienteHolder, posicao: Int) {
        holder.setIngrediente(ingredientes[posicao])
    }

    override fun getItemCount(): Int = ingredientes.size
}