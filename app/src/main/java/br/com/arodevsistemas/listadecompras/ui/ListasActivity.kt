package br.com.arodevsistemas.listadecompras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import br.com.arodevsistemas.listadecompras.R
import br.com.arodevsistemas.listadecompras.core.extensions.createProgressDialog
import br.com.arodevsistemas.listadecompras.core.extensions.format
import br.com.arodevsistemas.listadecompras.core.extensions.text
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.databinding.ActivityListasBinding
import br.com.arodevsistemas.listadecompras.presentation.ListasViewModel
import br.com.arodevsistemas.listadecompras.presentation.MainViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ListasActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListasBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<ListasViewModel>()
    private var lista: Listas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(intent.hasExtra(LISTA_EXTRA)){
            lista = intent.getSerializableExtra(LISTA_EXTRA) as? Listas
            lista?.let { getBusinessListas(it) }
        }

        toolbarOnBackPressed(lista?.id?:0)
        bindListerners()
        bindObserve()
    }

    private fun getBusinessListas(lista: Listas) {
        if (lista.id != null){
            binding.tilListasDtcadastro.text = lista.dtCadastro
            binding.tilListasDescricao.text = lista.descricao
            binding.btnListasDelete.isVisible = true
        }
    }

    private fun bindListerners(){

        binding.btnListasSave.setOnClickListener {

            var handler = Handler()
            handler.postDelayed({
                viewModel.updateListas(Listas(dtCadastro = binding.tilListasDtcadastro.text,
                                              descricao = binding.tilListasDescricao.text,
                                              id = lista?.id?:0))
            }, 2000)
        }


        binding.btnListasDelete.setOnClickListener {
            if (lista != null) {
                viewModel.deleteListas(lista!!)
            }
        }

        binding.tilListasDtcadastro.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilListasDtcadastro.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
    }

    private fun bindObserve() {

        viewModel.success.observe(this){
            when(it){
                is ListasViewModel.State.Loading -> dialog.show()
                is ListasViewModel.State.Error -> {
                    dialog.dismiss()
                    Toast.makeText(this, it.throwable.message, Toast.LENGTH_LONG).show()
                }
                is ListasViewModel.State.Saved -> {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.message_register_sucess), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ListasViewModel.State.Deleted -> {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.message_deletar_sucess), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            }
        }
    }

    private fun toolbarOnBackPressed(id: Long){

        val toolbar = binding.toolbarListas
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = if(id > 0){
            getString(R.string.toolbar_listas_edit)
        }else {
            getString(R.string.toolbar_listas_add)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val LISTA_EXTRA = ""
    }
}