package br.com.arodevsistemas.listadecompras.domain


import android.util.Log
import br.com.arodevsistemas.listadecompras.core.Either
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.data.repository.ListasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteListsUseCase(private val repository: ListasRepository) {

    suspend fun delete(listas: Listas): Flow<Unit> {
        return flow {
            try {
                val delete = repository.delete(listas)
                emit(delete)
            }catch (ex: Exception){
                ex
            }
        }
    }

    /*
    suspend fun deleteById(id: Long): Flow<Unit> {
        return flow {
            try {
                val delete = repository.deleteById(id)
                emit(delete)
            }catch (ex: Exception){
                ex
            }
        }
    }
    */

}