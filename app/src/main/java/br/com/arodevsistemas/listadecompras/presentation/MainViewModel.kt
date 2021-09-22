package br.com.arodevsistemas.listadecompras.presentation

import android.util.Log
import androidx.lifecycle.*
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveListasUseCase: SaveListasUseCase,
    private val deleteListsUseCase: DeleteListsUseCase,
    private val listasUseCase: ListasUseCase,
    private val listasByIdUseCase: ListasByIdUseCase
): ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _stateMessage = MutableLiveData<String>()
    val stateMessage: LiveData<String> = _stateMessage

    fun saveListas(listas: Listas){
        viewModelScope.launch {
            saveListasUseCase.execute(listas).flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    Log.e("MAIN", State.Saved.toString())
                    _state.value = State.Saved
                }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getAll(){
        viewModelScope.launch {
            listasUseCase.execute().flowOn(Dispatchers.Main)
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

    sealed class State{
        object Loading: State()
        object Saved: State()
        object Deleted: State()
        data class SuccessLista(val value: Listas): State()
        data class Success(val value: List<Listas>): State()
        data class Error(val throwable: Throwable): State()
    }
}