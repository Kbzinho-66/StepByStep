package com.example.stepbystep.ui.receitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stepbystep.adapters.AdapterIngrediente
import com.example.stepbystep.data.AppDatabase
import com.example.stepbystep.data.DAO
import com.example.stepbystep.data.Ingrediente
import com.example.stepbystep.databinding.ReceitasFragmentRvIngredientesBinding

class IngredientesFragmento: Fragment() {

    private var _binding: ReceitasFragmentRvIngredientesBinding? = null
    private val binding get() = _binding!!
    private val db: DAO by lazy {
        AppDatabase.getInstance(requireContext()).receitaDAO()
    }

    private var codigo: Int = 0
    private val listaIngredientes: MutableList<Ingrediente> by lazy {
        db.buscarIngredientesReceita(codigo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ReceitasFragmentRvIngredientesBinding.inflate(inflater, container, false)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun inicializarRecyclerView() {

        val layoutManager = LinearLayoutManager(activity).also{
            it.orientation = LinearLayoutManager.VERTICAL
        }

        binding.rvListaIngredientes.apply {
            setLayoutManager(layoutManager)
            val adapterIngrediente = AdapterIngrediente(listaIngredientes, context)
        }
    }
}
