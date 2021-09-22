package br.com.arodevsistemas.listadecompras.domain

import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.data.repository.ItensListasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteItensListasUseCase(private val repository: ItensListasRepository) {

    suspend fun delete(itensListas: ItensListas): Flow<Unit> {
        return flow {
            try {
                val delete = repository.delete(itensListas)
                emit(delete)
            }catch (ex: Exception){
                ex
            }
        }
    }
}