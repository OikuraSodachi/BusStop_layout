package com.todokanai.busstop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todokanai.busstop.CsvHelper
import com.todokanai.busstop.repository.DataStoreRepository
import com.todokanai.busstop.repository.UserRepository
import com.todokanai.busstop.room.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
    ) : ViewModel() {

    private val FILE_NAME_1 = "bus.csv"        // csv 파일의 이름

    fun insertBtn() {
        viewModelScope.launch {
            CsvHelper(FILE_NAME_1).csvToRoom()
        }
    }

    private val dataStoreRepository = DataStoreRepository()

    fun getAll() = userRepository.getUsers()

    fun insert(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.insert(user)
        }
    }

    suspend fun deleteAll(){
        userRepository.deleteAll()
    }

    suspend fun save(value:String){
        dataStoreRepository.save(value)
    }

    val load = dataStoreRepository.loadAsLiveData
    }