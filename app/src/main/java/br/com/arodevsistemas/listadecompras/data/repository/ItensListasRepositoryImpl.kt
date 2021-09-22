package br.com.arodevsistemas.listadecompras.data.repository

import android.util.Log
import br.com.arodevsistemas.listadecompras.core.Either
import br.com.arodevsistemas.listadecompras.data.database.AppDatabase
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext

class ItensListasRepositoryImpl(private val appDatabase: AppDatabase): ItensListasRepository {

    private val dao = appDatabase.itensListasDao()

    override suspend fun save(itensListas: ItensListas): Either<Long, Exception> =
        withContext(Dispatchers.IO) {
            try {
                var save = dao.insert(itensListas)
                Either.Success(save)
            } catch (e: Exception) {
                Either.Failure(e)
            }
        }

    override suspend fun update(itensListas: ItensListas): Unit =
        withContext(Dispatchers.IO) {
            try {
                dao.update(itensListas)
            } catch (e: Exception) {
                e
                Log.e("UPDATE", e.toString())
            }
        }

    override suspend fun delete(itensListas: ItensListas): Unit =
        withContext(Dispatchers.IO){
            try {
                dao.delete(itensListas)
            } catch (e: Exception) {
                e
            }
        }

    override suspend fun getItensListasById(id: Long): Flow<List<ItensListas>> =
        withContext(Dispatchers.IO){
            try {
                dao.getItensListas(id)
            }catch (e: Exception){
                emptyFlow()
            }
        }
}