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
//    private val stateHandle:SavedStateHandle
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
            state = getGymsInSpecificDispatchers()
        }
    }
    private suspend fun getGymsInSpecificDispatchers() = withContext(Dispatchers.IO){
        try {
            updateLocalDatabase()
        }catch (e:Exception){
            if(gymsDAO.getGyms().isEmpty())
                throw Exception("something went wrong")
        }
        gymsDAO.getGyms()
    } // this withContext can be removed because this retrofit interface call wrapping the network call with withContext behind(we don't see it)
    fun toggleFavouriteState(id:Int){
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id==id }

        viewModelScope.launch {
            state = toggleFavouriteGym(id, !gyms[itemIndex].isFavourite)
        }
    }
    suspend fun toggleFavouriteGym(gymId:Int, currentFavouriteState:Boolean) = withContext(Dispatchers.IO){
        gymsDAO.updateGym(
            GymFavouriteState(
                id = gymId,
                isFavourite = currentFavouriteState
            )
        )
        gymsDAO.getGyms()
    }

    private suspend fun updateLocalDatabase(){
        val gyms = apiService.getGyms()
        val favouroteGymsList = gymsDAO.getFavouriteGyms()
        gymsDAO.addGyms(gyms)
        gymsDAO.updateGyms(
            favouroteGymsList.map{GymFavouriteState(id = it.id, true)}
        )
    }

    suspend fun getGymsFromDB() = withContext(Dispatchers.IO){
        gymsDAO.getGyms()
    }

}
