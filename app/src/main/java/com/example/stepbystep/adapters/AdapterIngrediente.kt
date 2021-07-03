package com.example.stepbystep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stepbystep.data.Ingrediente
import com.example.stepbystep.databinding.ReceitasIngredienteItemBinding as IngredienteBinding

/**
 * Classe que adapta um [Ingrediente] para
 * a RecyclerView em [com.example.stepbystep.ui.receitas.IngredientesFragmento].
 */

class AdapterIngrediente(
    private val ingredientes: MutableList<Ingrediente>,
    val contexto: Context) : RecyclerView.Adapter<AdapterIngrediente.IngredienteHolder>() {

    inner class IngredienteHolder(private val layoutBinding: IngredienteBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun setIngrediente(ingrediente: Ingrediente) {
            with (layoutBinding) {
                ingredienteCheckbox.isChecked = false
                (ingredienteNome as TextView).text = ingrediente.nome
                (ingredienteQuantidade as TextView).text = ingrediente.quantidade
                ingredienteBotaoDeletar.setOnClickListener {
                    // TODO (Deletar esse item)
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

        val ingrediente: Ingrediente = ingredientes[position]

        with (holder) {
            setIngrediente(ingrediente)
        }
    }

    override fun getItemCount(): Int = ingredientes.size
}