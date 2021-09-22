package br.com.arodevsistemas.listadecompras.domain

import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.data.repository.ItensListasRepository
import kotlinx.coroutines.flow.Flow

class ItensListasUseCase(private val repository: ItensListasRepository) {

    suspend fun execute(idLista: Long): Flow<List<ItensListas>> {
        return repository.getItensListasById(idLista)
    }
}