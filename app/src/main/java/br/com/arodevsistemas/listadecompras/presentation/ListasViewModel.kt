package br.com.arodevsistemas.listadecompras.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.domain.SaveListasUseCase
import br.com.arodevsistemas.listadecompras.domain.DeleteListsUseCase
import br.com.arodevsistemas.listadecompras.domain.ListasByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ListasViewModel(
    private val deleteListsUseCase: DeleteListsUseCase,
    private val saveListasUseCase: SaveListasUseCase
): ViewModel() {

    private val _success = MutableLiveData<State>()
    val success: LiveData<State> = _success

    fun updateListas(listas: Listas){
        viewModelScope.launch {
            saveListasUseCase.execute(listas).flowOn(Dispatchers.Main)
                .onStart {
                    _success.value = State.Loading
                }
                .catch {
                    _success.value = State.Error(it)
                }
                .collect {
                    Log.e("MAIN", State.Saved.toString())
                    _success.value = State.Saved
                }
        }
    }

    fun deleteListas(listas: Listas): Unit{
        viewModelScope.launch {

            deleteListsUseCase.delete(listas).flowOn(Dispatchers.Main)
                .onStart {
                    _success.value = State.Loading
                }
                .catch {
                    _success.value = State.Error(it)
                }
                .collect {
                    _success.value = State.Deleted
                }


        }
    }

    sealed class State{
        object Loading: State()
        object Deleted: State()
        object Saved: State()
        data class SuccessLista(val value: Listas): State()
        data class Error(val throwable: Throwable): State()
    }
}