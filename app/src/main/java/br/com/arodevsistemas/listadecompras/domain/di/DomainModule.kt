package br.com.arodevsistemas.listadecompras.domain.di

import br.com.arodevsistemas.listadecompras.domain.*
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


object DomainModule {

    fun load(){
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { ListasUseCase(get()) }
            factory { SaveListasUseCase(get()) }
            factory { DeleteListsUseCase(get()) }
            factory { ListasByIdUseCase(get()) }
            factory { ItensListasUseCase(get()) }
            factory { SaveItensListasUseCase(get()) }
            factory { DeleteItensListasUseCase(get()) }
        }
    }
}