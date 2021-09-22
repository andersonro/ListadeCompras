package br.com.arodevsistemas.listadecompras.domain

import androidx.lifecycle.LiveData
import br.com.arodevsistemas.listadecompras.core.Either
import br.com.arodevsistemas.listadecompras.core.UseCase
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.data.repository.ListasRepository
import kotlinx.coroutines.flow.Flow

class ListasUseCase(private val repository: ListasRepository) {

    suspend fun execute(): Flow<List<Listas>> {
        return repository.getAll()
    }

}