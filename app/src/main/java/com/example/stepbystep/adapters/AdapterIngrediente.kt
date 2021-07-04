package com.example.stepbystep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.entities.Ingrediente
import com.example.stepbystep.data.dao.IngredienteDAO
import com.example.stepbystep.databinding.ReceitasIngredienteItemBinding as IngredienteBinding

/**
 * Classe que adapta um [Ingrediente] para
 * a RecyclerView em [com.example.stepbystep.ui.receitas.IngredientesFragmento].
 */

class AdapterIngrediente(
    private val ingredientes: MutableList<Ingrediente>,
    private val contexto: Context) : RecyclerView.Adapter<AdapterIngrediente.IngredienteHolder>() {

    private val db: IngredienteDAO by lazy {
        AppDatabase.getInstance(contexto).ingredienteDAO()
    }

    inner class IngredienteHolder(private val layoutBinding: IngredienteBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun setIngrediente(ingrediente: Ingrediente) {
            with (layoutBinding) {
                ingredienteCheckbox.isChecked = false
                (ingredienteNome as TextView).text = ingrediente.nome
                (ingredienteQuantidade as TextView).text = ingrediente.quantidade
                ingredienteBotaoDeletar.setOnClickListener {
                    db.deletarIngrediente(ingrediente.idIngrediente)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AdapterIngrediente.IngredienteHolder {

        val layoutBinding = IngredienteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return IngredienteHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: AdapterIngrediente.IngredienteHolder, position: Int) {

        val ingrediente = ingredientes[position]

        holder.setIngrediente(ingrediente)
    }

    override fun getItemCount(): Int = ingredientes.size
}