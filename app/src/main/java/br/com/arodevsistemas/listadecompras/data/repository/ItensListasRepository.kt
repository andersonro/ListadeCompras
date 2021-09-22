package br.com.arodevsistemas.listadecompras.data.repository

import br.com.arodevsistemas.listadecompras.core.Either
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import kotlinx.coroutines.flow.Flow

interface ItensListasRepository {

    suspend fun save(itensListas: ItensListas) : Either<Long, Exception>

    suspend fun update(itensListas: ItensListas)

    suspend fun delete(itensListas: ItensListas)

    suspend fun getItensListasById(id: Long): Flow<List<ItensListas>>
}