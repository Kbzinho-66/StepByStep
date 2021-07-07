package com.example.stepbystep.ui.galeria

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stepbystep.data.entities.Receita
import com.example.stepbystep.databinding.GaleriaItemBinding

/**
 * Classe que adapta uma [Receita] para
 * a RecyclerView em [com.example.stepbystep.ui.galeria.GaleriaFragmento].
 *
 * Não usa DataBinding por causa do Glide. TODO(Quem sabe mudar isso no futuro)
 */

class AdapterGaleria(
    private val receitas: MutableList<Receita>
) : RecyclerView.Adapter<AdapterGaleria.GaleriaHolder>() {

    inner class GaleriaHolder(private val layoutBinding: GaleriaItemBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun setFoto(receita: Receita) {

            with(layoutBinding) {
                Glide
                    .with(this.root)
                    .load(receita.uriFoto)
                    .into(fotoComidaGaleria)
                fotoComidaGaleria.setOnClickListener {
                    val acao = GaleriaFragmentoDirections
                        .actionNavVerDetalhesReceita(receita.codigo)
                    findNavController(this.root).navigate(acao)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
        GaleriaHolder = GaleriaHolder(
            GaleriaItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )

    override fun onBindViewHolder(holder: GaleriaHolder, position: Int) {
        val foto = receitas[position]
        holder.setFoto(foto)
    }

    override fun getItemCount(): Int = receitas.size
}