package br.com.arodevsistemas.listadecompras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.arodevsistemas.listadecompras.R
import br.com.arodevsistemas.listadecompras.core.extensions.createDialog
import br.com.arodevsistemas.listadecompras.core.extensions.createProgressDialog
import br.com.arodevsistemas.listadecompras.core.extensions.formatCurrency
import br.com.arodevsistemas.listadecompras.core.extensions.text
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.databinding.ActivityItensListasBinding
import br.com.arodevsistemas.listadecompras.presentation.ItensListasViewModel
import br.com.arodevsistemas.listadecompras.ui.ItensListaRegisterActivity.Companion.ITEM_LISTAS
import br.com.arodevsistemas.listadecompras.ui.ItensListaRegisterActivity.Companion.LISTA_OBJ
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class ItensListasActivity : AppCompatActivity() {

    private val binding by lazy { ActivityItensListasBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<ItensListasViewModel>()
    private val adapter by lazy { ItensListasAdapter(this) }
    private var lista: Listas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(intent.hasExtra(OBJ_LISTA_EXTRA)){
            lista = intent.getSerializableExtra(OBJ_LISTA_EXTRA) as? Listas

            lista?.let {
                viewModel.getIdListasAll(lista?.id?:0)
            }
        }

        binding.rvItensListas.adapter = adapter
        binding.rvItensListas.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )

        toolbarOnBackPressed()
        bindObserver()
        bindActions()

        adapter.listenerCard = {

            adapter.listenerItensListas = { itensListas->

                var intent = Intent(this, ItensListaRegisterActivity::class.java)
                var extras = Bundle()
                extras.putSerializable("LISTA", lista)
                extras.putSerializable(ITEM_LISTAS, itensListas)
                intent.putExtras(extras)

                startActivity(intent)

            }

        }

        adapter.listenerCheckbox = {
            viewModel.saveItensListas(it)
        }

        adapter.listenerRefresh = {
            viewModel.saveItensListas(it)
        }

    }

    private fun bindActions() {
        binding.fabItensListasRegister.setOnClickListener {
            var intent = Intent(this, ItensListaRegisterActivity::class.java)
            var extras = Bundle()
            extras.putSerializable("LISTA", lista)
            intent.putExtras(extras)
            startActivity(intent)
        }

    }

    private fun bindObserver(){

        viewModel.state.observe(this){
            when(it){
                ItensListasViewModel.State.Loading -> dialog.show()
                is ItensListasViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.throwable.message)
                    }
                }
                is ItensListasViewModel.State.Success -> {
                    dialog.dismiss()
                    var total = 0.00

                    if (it.value.isEmpty()){
                        binding.rvItensListas.isVisible = false
                        binding.tvTitleEmptyListas.isVisible = true
                    }else{
                        binding.rvItensListas.isVisible = true
                        binding.tvTitleEmptyListas.isVisible = false

                        total = it.value.sumByDouble { itensLista ->
                            itensLista.quantidade * itensLista.valor
                        }
                    }

                    binding.tvItensListasTotalValor.text = total.formatCurrency()
                    adapter.submitList(it.value)
                }
            }
        }

    }

    private fun toolbarOnBackPressed(){

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = getString(R.string.title_activity_itens_list)
        toolbar.subtitle = "${lista?.dtCadastro} - ${lista?.descricao}"
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        const val OBJ_LISTA_EXTRA = ""
    }
}