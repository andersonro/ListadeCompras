package br.com.arodevsistemas.listadecompras.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.arodevsistemas.listadecompras.data.database.dao.ItensListasDao
import br.com.arodevsistemas.listadecompras.data.database.dao.ListasDao
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.data.model.Listas

@Database(entities = [Listas::class, ItensListas::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun listasDao(): ListasDao
    abstract fun itensListasDao(): ItensListasDao

    companion object {
        fun getinstance(context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "db_app_listas_compras"
            ).build()
        }
    }
}