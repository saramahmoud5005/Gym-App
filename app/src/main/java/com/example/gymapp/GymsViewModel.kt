import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.domain.GetAllGymsUseCase
import com.example.gymapp.Gym
import com.example.gymapp.GymsScreenState
import com.example.gymapp.domain.ToggleFavouriteStateUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class GymsViewModel():ViewModel(){

    private var _state by mutableStateOf(GymsScreenState(
        gyms = emptyList<Gym>(),
        isLoading = true,
    ))
    val state: State<GymsScreenState>
        get() = derivedStateOf { _state }
    private val errorHandler = CoroutineExceptionHandler{ _,throwable->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }
    private val getAllGymsUseCase = GetAllGymsUseCase()
    private val toggleFavouriteStateUseCase = ToggleFavouriteStateUseCase()
    init {
        getGyms()
    }

    private fun getGyms(){
        viewModelScope.launch(errorHandler) {
            val receivedGyms = getAllGymsUseCase()
            _state = _state.copy(
                gyms = receivedGyms,
                isLoading = false
            )
        }
    }

    fun toggleFavouriteState(id:Int){
        val gyms = _state.gyms.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id==id }

        viewModelScope.launch {
            _state = _state.copy(
                gyms = toggleFavouriteStateUseCase(id, gyms[itemIndex].isFavourite)
            )
        }
    }

}
