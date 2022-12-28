package com.todokanai.busstop.viewmodel

import androidx.lifecycle.ViewModel
import com.todokanai.busstop.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val stationRepository: StationRepository):ViewModel(){


}