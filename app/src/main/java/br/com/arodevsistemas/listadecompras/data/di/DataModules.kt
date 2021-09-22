package br.com.arodevsistemas.listadecompras.data.di

import br.com.arodevsistemas.listadecompras.data.database.AppDatabase
import br.com.arodevsistemas.listadecompras.data.repository.ItensListasRepository
import br.com.arodevsistemas.listadecompras.data.repository.ItensListasRepositoryImpl
import br.com.arodevsistemas.listadecompras.data.repository.ListasRepository
import br.com.arodevsistemas.listadecompras.data.repository.ListasRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModules {

    fun load(){
        loadKoinModules(repositoryModule() + databaseModule())
    }

    private fun repositoryModule(): Module {
        return module {
            single<ListasRepository> {
                ListasRepositoryImpl(get())
            }
            single<ItensListasRepository> {
                ItensListasRepositoryImpl(get())
            }
        }
    }

    private fun databaseModule(): Module {
        return module {
            single {
                AppDatabase.getinstance(androidApplication())
            }
        }
    }





}