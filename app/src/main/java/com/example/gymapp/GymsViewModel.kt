import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.Gym
import com.example.gymapp.GymsApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(
    private val stateHandle:SavedStateHandle
):ViewModel(){

    var state by mutableStateOf(emptyList<Gym>())
    private var apiService:GymsApiService
    private val errorHandler = CoroutineExceptionHandler{ _,throwable->
        throwable.printStackTrace()
    }
    companion object{
        const val FAV_IDS = "favouriteGymsIDs"
    }
    init {
        val retrofit: Retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https...")
            .build()

        apiService = retrofit.create(GymsApiService::class.java)
        getGyms()
    }
    private fun getGyms(){
        //lifeCycle and viewModel scope uses dispatchers.Main by default but global scope uses dispatchers.Default by default
        viewModelScope.launch(Dispatchers.IO + errorHandler) {// errorHandler instead of try and catch
            val gyms = apiService.getGyms()
            withContext(Dispatchers.Main){//updating UI
                state = gyms.restoreSelectedGyms()
            }
        }
    }
    fun toggleFavouriteState(id:Int){
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id==id }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavourite = !gyms[itemIndex].isFavourite)
        storeSelectedGyms(gyms[itemIndex])
        state = gyms
    }
    private fun storeSelectedGyms(gym: Gym){
        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
        if(gym.isFavourite) savedHandleList.add(gym.id)
        else savedHandleList.remove(gym.id)
        stateHandle[FAV_IDS] = savedHandleList
    }
    private fun List<Gym>.restoreSelectedGyms(): List<Gym> {
        //val gyms = getGyms()
        stateHandle.get<List<Int>?>(FAV_IDS)?.let {savedIds->
            savedIds.forEach{gymId->
                this.find { it.id==gymId }?.isFavourite=true
            }
        }
        return this
    }

}
