package br.com.arodevsistemas.listadecompras.data.repository

import br.com.arodevsistemas.listadecompras.core.Either
import br.com.arodevsistemas.listadecompras.data.model.Listas
import kotlinx.coroutines.flow.Flow
import kotlin.Exception

interface ListasRepository {

    suspend fun save(listas: Listas) : Either<Long, Exception>

    suspend fun update(listas: Listas)

    suspend fun delete(listas: Listas)

    suspend fun deleteById(id: Long)

    suspend fun getAll() : Flow<List<Listas>>

    fun getById(id: Long): Listas

}