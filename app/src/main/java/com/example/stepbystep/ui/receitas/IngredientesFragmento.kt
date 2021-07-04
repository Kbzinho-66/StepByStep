package com.example.stepbystep.ui.receitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stepbystep.R
import com.example.stepbystep.adapters.AdapterIngrediente
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.IngredienteDAO
import com.example.stepbystep.data.entities.Ingrediente
import com.example.stepbystep.databinding.ReceitasFragmentRvIngredientesBinding
import com.google.android.material.snackbar.Snackbar

class IngredientesFragmento : Fragment() {

    private var _binding: ReceitasFragmentRvIngredientesBinding? = null
    private val binding get() = _binding!!

    private val db: IngredienteDAO by lazy {
        AppDatabase.getInstance(requireContext()).ingredienteDAO()
    }

    private val args: IngredientesFragmentoArgs by navArgs()
    private var codigoReceita: String = args.codigoReceita
    private val listaIngredientes: MutableList<Ingrediente> by lazy {
        db.buscarIngredientesReceita(codigoReceita)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ReceitasFragmentRvIngredientesBinding.inflate(inflater, container, false)

        inicializarRecyclerView()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            Snackbar.make(binding.root, "Salvar Ingredientes", Snackbar.LENGTH_SHORT)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun inicializarRecyclerView() {

        val layoutManager = LinearLayoutManager(activity).also {
            it.orientation = LinearLayoutManager.VERTICAL
        }

        binding.rvListaIngredientes.apply {
            setLayoutManager(layoutManager)
            val adapterIngrediente = AdapterIngrediente(listaIngredientes, context)
            adapter = adapterIngrediente.also {
                it.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
