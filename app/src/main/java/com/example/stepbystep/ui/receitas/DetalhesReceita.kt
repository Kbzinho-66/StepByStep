package com.example.stepbystep.ui.receitas

import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.stepbystep.R
import com.example.stepbystep.Utilidades
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.ReceitaDAO
import com.example.stepbystep.data.entities.Receita
import com.example.stepbystep.databinding.DetalhesFragmentBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Fragmento que é chamada em dois momentos ligeiramente diferentes.
 * Pode ser invocada para a criação de uma receita, se não for passado
 * nenhum argumento em [DetalhesReceitaArgs]; ou pode ser invocada para
 * mostrar os detalhes da receita cujo código é passado em [DetalhesReceitaArgs].
 */

class DetalhesReceita : Fragment() {

    private var _binding: DetalhesFragmentBinding? = null
    private val binding get() = _binding!!

    // Pedidos de atividade
    private val tirarFotoPrato =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
            if (ok) {

                Glide
                    .with(this)
                    .load(uriFoto)
                    .into(binding.fotoRC)

                binding.fotoRC.apply {
                    foreground = null
//                    rotation = 90F
                    // OBS: Quando a foto é tirada na hora, ela fica virada, porque
                    // foi tirada com o celular "deitado". A exibição fica meio estranha,
                    // mas acho que é mais comum tirar foto assim do que retrato.
                }
            }
        }

    private val escolherFotoPrato =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                uriFoto = uri

                Glide
                    .with(this)
                    .load(uriFoto)
                    .into(binding.fotoRC)

                binding.fotoRC.foreground = null
            }

        }

    // Valores relacionados ao banco de dados
    private val db: ReceitaDAO by lazy {
        AppDatabase.getInstance(requireContext()).receitaDAO()
    }
    private val args: DetalhesReceitaArgs by navArgs()

    // Variáveis temporárias
    private var uriFoto: Uri? = null
    private lateinit var receita: Receita

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DetalhesFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        var codigoReceita = args.codigoReceita

        // O valor default do codigoReceita é -1L, então, se for 1, criar uma receita
        if (codigoReceita == -1L) {
            /**
             * FIXME (Não dá pra chamar os fragments de ingredientes e passos direto
             *  depois de criar a receita aqui. Mesmo inserindo no banco de dados, a
             *  chave estrangeira ainda não é reconhecida.
             */
            receita = Receita()
            codigoReceita = receita.codigo
//            db.inserirReceita(receita)
        } else {
            receita = db.buscarReceitaPorCodigo(codigoReceita)
        }

        with(binding) {
            // Setar os listeners dos botões
            fotoRC.setOnClickListener {
                registerForContextMenu(fotoRC)
                mostrarMenu(fotoRC)
            }

            botaoIngredientesRC.setOnClickListener {
                val acaoIngredientes = DetalhesReceitaDirections
                    .actionNavVerListaIngredientes(codigoReceita)
                findNavController().navigate(acaoIngredientes)
            }

            botaoPassosRC.setOnClickListener {
                val acaoPassos = DetalhesReceitaDirections
                    .actionNavVerListaPassos(codigoReceita)
                findNavController().navigate(acaoPassos)
            }

        }

        if (args.codigoReceita != -1L) {

            // Atualizar os campos de dados se a receita não for null
            with(binding) {

                (entradaNome as TextView).text = receita.nome

                Glide.with(this@DetalhesReceita).load(receita.uriFoto).into(fotoRC)
                fotoRC.foreground = null

                (entradaTempoPreparo as TextView).text =
                    Utilidades.millisToString(receita.tempoPreparoMilis)

                (entradaTempoCozimento as TextView).text =
                    Utilidades.millisToString(receita.tempoCozimentoMilis)

                (entradaPorcoes as TextView).text = receita.porcoes.toString()
            }
        }

        return binding.root
    }

    override fun onResume() {
        uriFoto?.let {
            binding.fotoRC.foreground = null
            Glide.with(this@DetalhesReceita).load(it).into(binding.fotoRC)
        }

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.criador, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_save -> {
            salvarReceita()
            true
        }
        R.id.action_delete -> {
            deletarReceita()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun salvarReceita() {
        with (binding) {
            receita.nome = entradaNome.text.toString()

            receita.uriFoto =
                if (uriFoto != null)
                    uriFoto.toString()
                else
                    receita.uriFoto

            receita.tempoPreparoMilis =
                with (entradaTempoCozimento.text.toString()){
                    if (this.isNotEmpty())
                        Utilidades.stringToMillis(this)
                    else
                        0L
                }

            receita.tempoCozimentoMilis =
                with (entradaTempoCozimento.text.toString()){
                    if (this.isNotEmpty())
                        Utilidades.stringToMillis(this)
                    else
                        0L
                }

            receita.porcoes =
                with (entradaPorcoes.text.toString()) {
                    if (this.isNotEmpty())
                        this.toInt()
                    else
                        0
                }
        }

        try { db.inserirReceita(receita) } catch (erro: SQLiteConstraintException) {
            Log.e(context.toString(), "Erro ao salvar uma receita")
        }

        Snackbar.make(binding.root,"Receita Salva!", Snackbar.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_nav_detalhes_receita_to_nav_home)
    }

    private fun deletarReceita() {

        try { db.deletarReceita(receita) } catch (erro: SQLiteConstraintException) {
            Log.e(erro.toString(), "Exceção ao deletar uma receita.")
        }

        Snackbar.make(binding.root,"Receita Deletada!", Snackbar.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_nav_detalhes_receita_to_nav_home)
    }

    private fun mostrarMenu(view: View) {

        PopupMenu(context, view).apply {
            setOnMenuItemClickListener { item ->
                when (item.itemId) {

                    R.id.action_tirar_foto -> {
                        Utilidades.criaArquivo(requireContext())?.let {
                            uriFoto = it
                            tirarFotoPrato.launch(it)
                        }
                        true
                    }

                    R.id.action_pegar_galeria -> {
                        escolherFotoPrato.launch("image/*")
                        true
                    }

                    else -> false
                }
            }

            menuInflater.inflate(R.menu.menu_contexto, menu)

            show()
        }
    }

}