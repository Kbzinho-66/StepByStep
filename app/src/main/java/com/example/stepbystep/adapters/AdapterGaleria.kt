package com.example.stepbystep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.stepbystep.data.entities.Receita
import com.example.stepbystep.databinding.GaleriaItemBinding
import com.example.stepbystep.ui.galeria.GaleriaFragmentoDirections

/**
 * Classe que adapta uma [Receita] para
 * a RecyclerView em [com.example.stepbystep.ui.galeria.GaleriaFragmento].
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
//                    .apply(RequestOptions()
//                        .override(fotoComidaGaleria.width,fotoComidaGaleria.height))
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
            AdapterGaleria.GaleriaHolder {

        val layoutBinding = GaleriaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return GaleriaHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: AdapterGaleria.GaleriaHolder, position: Int) {

        val foto = receitas[position]

        holder.setFoto(foto)
    }

    override fun getItemCount(): Int = receitas.size
}