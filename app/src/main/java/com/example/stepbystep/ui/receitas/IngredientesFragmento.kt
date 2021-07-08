package com.example.stepbystep.ui.receitas

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stepbystep.R
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.IngredienteDAO
import com.example.stepbystep.data.entities.Ingrediente
import com.example.stepbystep.databinding.ReceitasFragmentRvIngredientesBinding
import com.example.stepbystep.ui.adapters.AdapterIngrediente
import com.google.android.material.snackbar.Snackbar

/**
 * Fragmento responsável por gerir a RecyclerView de [Ingrediente] associada
 * a cada receita.
 */

class IngredientesFragmento : Fragment() {

    private var _binding: ReceitasFragmentRvIngredientesBinding? = null
    private val binding get() = _binding!!

    private val args: IngredientesFragmentoArgs by navArgs()
    private var codigoReceita: Long = 0L

    private val db: IngredienteDAO by lazy {
        AppDatabase.getInstance(requireContext()).ingredienteDAO()
    }
    private val listaIngredientes: MutableList<Ingrediente> by lazy {
        db.buscarIngredientesReceita(codigoReceita)
    }

    private val adapterIngrediente: AdapterIngrediente by lazy {
        AdapterIngrediente(listaIngredientes, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ReceitasFragmentRvIngredientesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        codigoReceita = args.codigoReceita

        inicializarRecyclerView()

        binding.botaoNovoIngrediente.setOnClickListener {
            listaIngredientes.add(Ingrediente(idReceita = codigoReceita))
            adapterIngrediente.notifyItemInserted(listaIngredientes.size - 1)
        }

        return binding.root
    }

    private fun inicializarRecyclerView() {

        binding.rvListaIngredientes.apply {

            val layoutManager = LinearLayoutManager(requireContext()).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            setLayoutManager(layoutManager)

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
        menuInflater.inflate(R.menu.menu_listas, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_refresh -> {
            resetarLista()
            true
        }

        R.id.action_save -> {
            salvarIngredientes()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun salvarIngredientes() {
        try { db.inserirIngredientes(listaIngredientes) } catch (erro: SQLiteConstraintException) {
            Snackbar.make(binding.root, "Por favor, salve a receita antes de adicionar ingredientes", Snackbar.LENGTH_LONG).show()
            Log.e(erro.toString(), "Erro ao salvar a lista de ingredientes")
        }

        findNavController().navigateUp()
    }

    private fun resetarLista() {
        listaIngredientes.forEach {
            it.ok = false
        }
        adapterIngrediente.notifyDataSetChanged()
    }

}
