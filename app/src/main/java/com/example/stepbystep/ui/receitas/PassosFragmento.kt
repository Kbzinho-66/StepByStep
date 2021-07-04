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
import com.example.stepbystep.adapters.AdapterPasso
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.PassoDAO
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.databinding.ReceitasFragmentRvPassosBinding
import com.google.android.material.snackbar.Snackbar

class PassosFragmento : Fragment() {

    private var _binding: ReceitasFragmentRvPassosBinding? = null
    private val binding get() = _binding!!

    private val db: PassoDAO by lazy {
        AppDatabase.getInstance(requireContext()).passoDAO()
    }

    private val args: PassosFragmentoArgs by navArgs()
    private var codigoReceita: String = args.codigoReceita
    private val listaPassos: MutableList<Passo> by lazy {
        db.buscarPassosReceita(codigoReceita)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = ReceitasFragmentRvPassosBinding.inflate(inflater, container, false)

        inicializarRecyclerView()

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            Snackbar.make(binding.root, "Salvar Passos", Snackbar.LENGTH_SHORT)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun inicializarRecyclerView() {

        val layoutManager = LinearLayoutManager(activity).also{
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
}