package br.com.arodevsistemas.listadecompras.domain

import br.com.arodevsistemas.listadecompras.R
import br.com.arodevsistemas.listadecompras.core.Either
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.data.repository.ListasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveListasUseCase(private val repository: ListasRepository) {

    suspend fun execute(params: Listas): Flow<Unit>{

        return flow {

            if (params==null) throw IllegalArgumentException()
            if (params.dtCadastro.isNullOrEmpty()) throw Exception("Data de Cadastro invalido!")
            if (params.descricao.isNullOrEmpty()) throw Exception("Descrição invalida!")

            try {
                if (params.id == 0){
                    repository.save(params)
                }else{
                    repository.update(params)
                }
                kotlinx.coroutines.delay(2000L)
                emit(Unit)
            }catch (e: Exception){
                e
            }

        }

    }
}