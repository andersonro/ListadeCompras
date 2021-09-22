package br.com.arodevsistemas.listadecompras.domain

import br.com.arodevsistemas.listadecompras.core.UseCase
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.data.repository.ListasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.xml.transform.Source

class ListasByIdUseCase(private val repository: ListasRepository) {

    fun execute(id: Long): Listas{
        return repository.getById(id)
    }
}