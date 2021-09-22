package br.com.arodevsistemas.listadecompras.domain

import android.util.Log
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.data.repository.ItensListasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveItensListasUseCase(private val repository: ItensListasRepository) {

    suspend fun saveItensListas(itensListas: ItensListas): Flow<Unit>{

        return flow {

            if (itensListas==null) throw IllegalArgumentException()
            if (itensListas.descricao.isNullOrEmpty()) throw Exception("Descrição invalida!")
            if (itensListas.quantidade.toString().isNullOrEmpty()) throw Exception("Quantidade invalida!")

            try {
                if (itensListas.id == 0){
                    repository.save(itensListas)
                }else{
                    repository.update(itensListas)
                }
                kotlinx.coroutines.delay(2000L)
                emit(Unit)
            }catch (e: Exception){
                e
            }
        }

    }
}