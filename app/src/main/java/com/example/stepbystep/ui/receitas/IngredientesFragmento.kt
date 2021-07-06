package com.example.stepbystep.ui.receitas

import android.os.Bundle
import android.view.*
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
import kotlin.properties.Delegates

class IngredientesFragmento : Fragment() {

    private var _binding: ReceitasFragmentRvIngredientesBinding? = null
    private val binding get() = _binding!!

    private val args: IngredientesFragmentoArgs by navArgs()
    private var codigoReceita: Long by Delegates.notNull()

    private val db: IngredienteDAO by lazy {
        AppDatabase.getInstance(requireContext()).ingredienteDAO()
    }
    private val listaIngredientes: MutableList<Ingrediente> by lazy {
        db.buscarIngredientesReceita(codigoReceita)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ReceitasFragmentRvIngredientesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        codigoReceita = args.codigoReceita

        inicializarRecyclerView()

        return super.onCreateView(inflater, container, savedInstanceState)
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

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_listas, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            salvarIngredientes()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun salvarIngredientes() {
        Snackbar.make(binding.root, "Ingredientes salvos", Snackbar.LENGTH_LONG).show()
        // TODO (Salvar todos os ingredientes)
    }

}
