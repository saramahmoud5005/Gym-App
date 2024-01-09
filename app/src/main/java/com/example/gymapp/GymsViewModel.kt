import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gymapp.Gym
import com.example.gymapp.GymsApiService
import com.example.gymapp.listOfGym
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(
    private val stateHandle:SavedStateHandle
):ViewModel(){

    companion object{
        const val FAV_IDS = "favouriteGymsIDs"
    }
    fun getGyms(){
        val response = apiService.getGyms().execute().body()
        response?.let {
            state = it.restoreSelectedGyms()
        }
    }
    private var apiService:GymsApiService
    init {
        val retrofit: Retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https...")
            .build()

        apiService = retrofit.create(GymsApiService::class.java)
    }

    var state by mutableStateOf(emptyList<Gym>())

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
