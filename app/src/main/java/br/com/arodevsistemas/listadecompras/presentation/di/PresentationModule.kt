package br.com.arodevsistemas.listadecompras.presentation.di

import br.com.arodevsistemas.listadecompras.presentation.ItensListasViewModel
import br.com.arodevsistemas.listadecompras.presentation.ListasViewModel
import br.com.arodevsistemas.listadecompras.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load(){
        loadKoinModules(viewModelModule())
    }


    private fun viewModelModule(): Module{
        return module {
            viewModel {
                MainViewModel(get(), get(), get(), get())
            }
            viewModel {
                ListasViewModel(get(), get())
            }
            viewModel {
                ItensListasViewModel(get(), get(), get())
            }
        }
    }


}