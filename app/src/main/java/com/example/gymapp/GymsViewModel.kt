import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.Gym
import com.example.gymapp.GymApplication
import com.example.gymapp.GymFavouriteState
import com.example.gymapp.GymsApiService
import com.example.gymapp.GymsDAO
import com.example.gymapp.GymsDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class GymsViewModel(
    private val stateHandle:SavedStateHandle
):ViewModel(){

    var state by mutableStateOf(emptyList<Gym>())
    private var apiService:GymsApiService
    private var gymsDAO = GymsDatabase.getDaoInstance(GymApplication.getApplicationContext())
    private val errorHandler = CoroutineExceptionHandler{ _,throwable->
        throwable.printStackTrace()
    }
    companion object{
        const val FAV_IDS = "favouriteGymsIDs"
    }
    init {
        val retrofit: Retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fcm-pushnotification-7a8f7-default-rtdb.firebaseio.com/")
            .build()

        apiService = retrofit.create(GymsApiService::class.java)
        getGyms()
    }

    private fun getGyms(){
        //lifeCycle and viewModel scope uses dispatchers.Main by default but global scope uses dispatchers.Default by default
        viewModelScope.launch(errorHandler) {// errorHandler instead of try and catch
            Log.d("gyms", "getGyms: ")
            val gyms = getGymsInSpecificDispatchers()
            state = gyms.restoreSelectedGyms()
        }
    }
    private suspend fun getGymsInSpecificDispatchers() = withContext(Dispatchers.IO){
        try {
            val gyms = apiService.getGyms()
            gymsDAO.addGyms(gyms)
            return@withContext gyms
        }catch (e:Exception){
            gymsDAO.getGyms() //can return it without the return keyword
        }
    } // this withContext can be removed because this retrofit interface call wrapping the network call with withContext behind(we don't see it)
    fun toggleFavouriteState(id:Int){
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id==id }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavourite = !gyms[itemIndex].isFavourite)
        storeSelectedGyms(gyms[itemIndex])
        state = gyms
        viewModelScope.launch {
            toggleFavouriteGym(id, gyms[itemIndex].isFavourite)
        }
    }
    suspend fun toggleFavouriteGym(gymId:Int, currentFavouriteState:Boolean) = withContext(Dispatchers.IO){
        gymsDAO.updateGym(
            GymFavouriteState(
                id = gymId,
                isFavourite = currentFavouriteState
            )
        )
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
            val gymsMap = this.associateBy { it.id }.toMutableMap()
            savedIds.forEach{gymId->
//                this.find { it.id==gymId }?.isFavourite=true
                val gym = gymsMap[gymId]?: return@forEach
                gymsMap[gymId] = gym.copy(isFavourite = true)
            }
            return gymsMap.values.toList()
        }
        return this
    }
    suspend fun getGymsFromDB() = withContext(Dispatchers.IO){
        gymsDAO.getGyms()
    }

}
