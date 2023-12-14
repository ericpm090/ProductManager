package com.example.productmanager.ui.admin.ui.incidences

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.Incidence
import com.example.productmanager.domain.model.services.IncidencesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IncidencesViewModel @Inject constructor(
    private val incidencesService: IncidencesService
) : ViewModel() {

    val updateIncidents =  MutableLiveData<MutableList<Incidence>?>()
    val solveInvident = MutableLiveData<Boolean?>()

    fun onUpdateSelected(){
        viewModelScope.launch {
            updateIncidents.postValue(withContext(Dispatchers.IO) {
                incidencesService.getIncidences()})
        }
    }

    fun onDeleteItem(incidence: Incidence){
        viewModelScope.launch {
            solveInvident.postValue(
                withContext(Dispatchers.IO){
                    incidencesService.solveIncidence(incidence)
                }

            )
        }
    }




}