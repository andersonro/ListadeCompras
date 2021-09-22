package br.com.arodevsistemas.listadecompras

import android.app.Application
import android.app.Presentation
import br.com.arodevsistemas.listadecompras.data.di.DataModules
import br.com.arodevsistemas.listadecompras.domain.di.DomainModule
import br.com.arodevsistemas.listadecompras.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModules.load()
        DomainModule.load()
        PresentationModule.load()

    }
}