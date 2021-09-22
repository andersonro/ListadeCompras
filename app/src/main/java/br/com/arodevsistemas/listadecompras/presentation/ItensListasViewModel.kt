package br.com.arodevsistemas.listadecompras.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.domain.DeleteItensListasUseCase
import br.com.arodevsistemas.listadecompras.domain.ItensListasUseCase
import br.com.arodevsistemas.listadecompras.domain.ListasByIdUseCase
import br.com.arodevsistemas.listadecompras.domain.SaveItensListasUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ItensListasViewModel(
    private val itensListasUseCase: ItensListasUseCase,
    private val saveItensListasUseCase: SaveItensListasUseCase,
    private val deleteItensListasUseCase: DeleteItensListasUseCase
): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getIdListasAll(id: Long){
        viewModelScope.launch {
            itensListasUseCase.execute(id).flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    fun saveItensListas(itensListas: ItensListas){
        viewModelScope.launch {
            saveItensListasUseCase.saveItensListas(itensListas).flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Saved
                }
        }
    }

    fun deleteItensListas(itensListas: ItensListas){
        viewModelScope.launch {
            deleteItensListasUseCase.delete(itensListas).flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Deleted
                }
        }
    }


    sealed class State{
        object Loading: State()
        object Deleted: State()
        object Saved: State()
        data class Success(val value: List<ItensListas>): State()
        data class Error(val throwable: Throwable): State()
    }
}