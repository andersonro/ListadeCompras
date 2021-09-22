package br.com.arodevsistemas.listadecompras.data.repository

import android.util.Log
import br.com.arodevsistemas.listadecompras.core.Either
import br.com.arodevsistemas.listadecompras.data.database.AppDatabase
import br.com.arodevsistemas.listadecompras.data.model.Listas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext

class ListasRepositoryImpl(private val appDatabase: AppDatabase): ListasRepository {

    private val dao = appDatabase.listasDao()

    override suspend fun save(listas: Listas): Either<Long, Exception> =
        withContext(Dispatchers.IO) {
            try {
                val save = dao.insert(listas)
                Either.Success(save)
            } catch (e: Exception) {
                Either.Failure(e)
            }
        }


    override suspend fun update(listas: Listas): Unit =
        withContext(Dispatchers.IO) {
            try {
                dao.update(listas)
            } catch (e: Exception) {
                e
                Log.e("UPDATE", e.toString())
            }
        }

    override suspend fun delete(listas: Listas): Unit =
        withContext(Dispatchers.IO){
            try {
                dao.delete(listas)
            } catch (e: Exception) {
                e
            }
        }

    override suspend fun deleteById(id: Long): Unit =
        withContext(Dispatchers.IO){
            try {
                dao.deleteById(id)
            } catch (e: Exception) {
                e
                Log.e("DELETE", "${e}")
            }
        }

    override suspend fun getAll(): Flow<List<Listas>> = dao.getAll()

    override fun getById(id: Long): Listas {
        return dao.getById(id)
    }


}