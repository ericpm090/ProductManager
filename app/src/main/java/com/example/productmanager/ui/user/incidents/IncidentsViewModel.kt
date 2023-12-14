package com.example.productmanager.ui.user.incidents

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.services.IncidencesService
import com.example.productmanager.domain.model.services.ToolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IncidentsViewModel @Inject constructor(
    private val incidencesService: IncidencesService,
    private val toolService: ToolService
) : ViewModel() {

    val createIncident = MutableLiveData<Boolean>()


    fun onCreateIncident(email: String, barcode: String, description: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tool = toolService.getToolbyBarcode(barcode)
                if(tool!=null){
                    Log.i("IncidentsViewModel", "Tool founded")
                    Log.i("IncidentsViewModel", "Creating incidence..")
                    val incidence = incidencesService.createIncidence(
                        email,
                        tool,
                        description
                    )
                    Log.i("IncidentsViewModel", "Incidence created")
                    if (incidence != null) {
                        createIncident.postValue(
                            incidencesService.saveIncidence(incidence)
                        )
                    } else {
                        createIncident.postValue(false)
                    }
                }


            }


        }

    }
}
