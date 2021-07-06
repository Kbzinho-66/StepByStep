package com.example.stepbystep.ui.receitas

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stepbystep.R
import com.example.stepbystep.adapters.AdapterPasso
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.PassoDAO
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.databinding.ReceitasFragmentRvPassosBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class PassosFragmento : Fragment() {

    private var _binding: ReceitasFragmentRvPassosBinding? = null
    private val binding get() = _binding!!

    private val args: PassosFragmentoArgs by navArgs()
    private var codigoReceita: Long by Delegates.notNull()

    private val db: PassoDAO by lazy {
        AppDatabase.getInstance(requireContext()).passoDAO()
    }
    private val listaPassos: MutableList<Passo> by lazy {
        db.buscarPassosReceita(codigoReceita)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ReceitasFragmentRvPassosBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        codigoReceita = args.codigoReceita

        inicializarRecyclerView()

        return binding.root
    }

    private fun inicializarRecyclerView() {

        val layoutManager = LinearLayoutManager(activity).also {
            it.orientation = LinearLayoutManager.VERTICAL
        }

        binding.rvListaPassos.apply {
            setLayoutManager(layoutManager)
            val adapterPasso = AdapterPasso(listaPassos, context)
            adapter = adapterPasso.also {
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
            salvarPassos()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun salvarPassos() {
        Snackbar.make(binding.root, "Passos salvos", Snackbar.LENGTH_LONG).show()
        // TODO (Salvar todos os passos)
    }
}