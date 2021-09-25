package br.com.arodevsistemas.listadecompras.domain

import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.data.repository.ItensListasRepository
import br.com.arodevsistemas.listadecompras.data.repository.ListasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteListsUseCase(
    private val repository: ListasRepository,
    private val repositoryItens: ItensListasRepository
    ) {

    suspend fun delete(listas: Listas): Flow<Unit> {
        return flow {
            try {
                val deleteItens = repositoryItens.deleteItensListas(listas.id)
                val delete = repository.delete(listas)
                emit(delete)
            }catch (ex: Exception){
                ex
            }
        }
    }

}