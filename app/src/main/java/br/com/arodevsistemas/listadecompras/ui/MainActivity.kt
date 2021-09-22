package br.com.arodevsistemas.listadecompras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.arodevsistemas.listadecompras.R
import br.com.arodevsistemas.listadecompras.core.extensions.createDialog
import br.com.arodevsistemas.listadecompras.core.extensions.createProgressDialog
import br.com.arodevsistemas.listadecompras.databinding.ActivityMainBinding
import br.com.arodevsistemas.listadecompras.domain.ListasExceptions
import br.com.arodevsistemas.listadecompras.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { ListasAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dialog.show()

        binding.rvMain.adapter = adapter
        binding.rvMain.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )

        bindObserver()
        lifecycle.addObserver(viewModel)

        bindAction()

    }

    private fun bindObserver(){

        viewModel.state.observe(this){
            when(it){
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.throwable.message)
                    }
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()

                    if (it.value.isEmpty()){
                        binding.rvMain.isVisible = false
                        binding.tvTitleEmptyListas.isVisible = true
                    }else{
                        binding.rvMain.isVisible = true
                        binding.tvTitleEmptyListas.isVisible = false
                    }

                    adapter.submitList(it.value)
                }
            }
        }

    }

    private fun bindAction(){
        binding.fabListas.setOnClickListener {
            startActivity(Intent(this, ListasActivity::class.java))
        }
    }
}