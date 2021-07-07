package com.example.stepbystep.ui.galeria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.ReceitaDAO
import com.example.stepbystep.data.entities.Receita
import com.example.stepbystep.databinding.GaleriaFragmentBinding

/**
 * Fragmento responsável por gerir o Livro de Receitas, a grade com a
 * foto de cada [Receita]
 */

class GaleriaFragmento : Fragment() {

    private var _binding: GaleriaFragmentBinding? = null
    private val binding get() = _binding!!

    private val db: ReceitaDAO by lazy { AppDatabase.getInstance(requireContext()).receitaDAO() }
    private val receitas: MutableList<Receita> by lazy { db.buscarTodasReceitas() }

    private val adapterGaleria: AdapterGaleria by lazy { AdapterGaleria(receitas) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = GaleriaFragmentBinding.inflate(inflater, container, false)

        inicializarRecyclerView()

        return binding.root
    }

    private fun inicializarRecyclerView() {

        binding.rvGaleriaReceitas.adapter = adapterGaleria.also{
                it.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

