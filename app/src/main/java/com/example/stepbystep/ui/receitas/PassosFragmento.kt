package com.example.stepbystep.ui.receitas

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stepbystep.R
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.PassoDAO
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.databinding.ReceitasFragmentRvPassosBinding
import com.example.stepbystep.ui.adapters.AdapterPasso

/**
 * Fragmento responsável por gerir a RecyclerView de [Passo] associada
 * a cada receita.
 */

class PassosFragmento : Fragment() {

    private var _binding: ReceitasFragmentRvPassosBinding? = null
    private val binding get() = _binding!!

    private val args: PassosFragmentoArgs by navArgs()
    private var codigoReceita: Long = 0

    private val db: PassoDAO by lazy {
        AppDatabase.getInstance(requireContext()).passoDAO()
    }
    private val listaPassos: MutableList<Passo> by lazy {
        db.buscarPassosReceita(codigoReceita)
    }

    private val adapterPasso: AdapterPasso by lazy {
        AdapterPasso(listaPassos, requireContext())
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

        binding.botaoNovoPasso.setOnClickListener {
            listaPassos.add(Passo(idReceita = codigoReceita))
            adapterPasso.notifyItemInserted(listaPassos.size - 1)
        }

        return binding.root
    }

    private fun inicializarRecyclerView() {

        binding.rvListaPassos.apply {

            val layoutManager = LinearLayoutManager(requireContext()).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            setLayoutManager(layoutManager)

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
        menuInflater.inflate(R.menu.menu_listas, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_refresh -> {
            resetarLista()
            true
        }

        R.id.action_save -> {
            salvarPassos()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun salvarPassos() {
        db.inserirPassos(listaPassos)
        findNavController().navigateUp()
    }

    private fun resetarLista() {
        listaPassos.forEach {
            it.pronto = false
        }
        adapterPasso.notifyDataSetChanged()
    }
}