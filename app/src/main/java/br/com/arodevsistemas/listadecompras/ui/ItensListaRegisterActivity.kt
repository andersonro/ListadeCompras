package br.com.arodevsistemas.listadecompras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.arodevsistemas.listadecompras.R
import br.com.arodevsistemas.listadecompras.core.extensions.createProgressDialog
import br.com.arodevsistemas.listadecompras.core.extensions.text
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.databinding.ActivityItensListaRegisterBinding
import br.com.arodevsistemas.listadecompras.presentation.ItensListasViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItensListaRegisterActivity : AppCompatActivity() {

    private val binding by lazy { ActivityItensListaRegisterBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<ItensListasViewModel>()
    private var lista: Listas? = null
    private var item_lista: ItensListas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lista = intent.extras?.getSerializable("LISTA") as? Listas
        item_lista = intent.extras?.getSerializable(ITEM_LISTAS) as? ItensListas

        item_lista.let {
            if (it != null){
                getBindItensListas(it)
            }
        }

        toolbarOnBackPressed(item_lista?.id ?: 0)
        bindAction()
        bindObserve()

    }

    private fun bindAction() {
        binding.btnItensListasSave.setOnClickListener {
            bindSaveItensListas()
        }

        binding.btnItensListasDelete.setOnClickListener {
            item_lista?.let { it1 -> viewModel.deleteItensListas(it1) }
        }
    }

    private fun getBindItensListas(itensListas: ItensListas){
        binding.tilItensListasDescricao.text = itensListas.descricao
        binding.tilItensListasQtd.text = itensListas.quantidade.toString()
        binding.tilItensListasValor.text = itensListas.valor.toString()

        binding.btnItensListasDelete.visibility = if (itensListas.id != null && itensListas.id > 0){
            View.VISIBLE
        }else {
            View.GONE
        }
    }

    private fun bindObserve() {

        viewModel.state.observe(this){
            when(it){
                is ItensListasViewModel.State.Loading -> dialog.show()
                is ItensListasViewModel.State.Error -> {
                    dialog.dismiss()
                    Toast.makeText(this, it.throwable.message, Toast.LENGTH_LONG).show()
                }
                is ItensListasViewModel.State.Saved -> {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.message_register_sucess), Toast.LENGTH_LONG).show()
                    var intent = Intent(this, ItensListasActivity::class.java)
                    intent.putExtra(ItensListasActivity.OBJ_LISTA_EXTRA, lista)
                    startActivity(intent)
                    finish()
                }
                is ItensListasViewModel.State.Deleted -> {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.message_deletar_sucess), Toast.LENGTH_LONG).show()
                    var intent = Intent(this, ItensListasActivity::class.java)
                    intent.putExtra(ItensListasActivity.OBJ_LISTA_EXTRA, lista)
                    startActivity(intent)
                    finish()
                }

            }
        }
    }

    private fun bindSaveItensListas() {

        var quantidade: Double = if (binding.tilItensListasQtd.text.isNullOrEmpty()){
            0.0
        }else {
            binding.tilItensListasQtd.text.toDouble()
        }

        var valor: Double = if (binding.tilItensListasValor.text.isNullOrEmpty()){
            0.0
        }else {
            binding.tilItensListasValor.text.toDouble()
        }

        viewModel.saveItensListas(ItensListas(id = item_lista?.id?:0,
                                              descricao = binding.tilItensListasDescricao.text,
                                              quantidade = quantidade,
                                              valor = valor,
                                              idListas = lista?.id?:0
                                             )
                                  )
    }

    private fun toolbarOnBackPressed(id: Long){

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = if(id > 0){
            getString(R.string.toolbar_title_itens_lista_edit)
        }else{
            getString(R.string.toolbar_title_itens_lista_add)
        }

        var subtitle = if (lista != null) {
            "${lista?.dtCadastro} - ${lista?.descricao}"
        }else{
            ""
        }

        toolbar.subtitle = subtitle
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object{
        const val ITEM_LISTAS = "ITEM_LISTA"
        const val LISTA_OBJ = "LISTA_OBJ"
    }
}