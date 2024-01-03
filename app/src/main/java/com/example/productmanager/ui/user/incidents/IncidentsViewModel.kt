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


    /*
    If data is not null call method getToolbyBarcode to check if tool exist.
    If tool exist, create incident with createIncidence method. Post result.
     */
    fun onCreateIncident(email: String, barcode: String, description: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tool = toolService.getToolbyBarcode(barcode)
                if (tool != null) {
                    val incidence = incidencesService.createIncidence(
                        email,
                        tool,
                        description
                    )
                    Log.i("TAG_DATABASE_INCIDENCES", "Viewmodel: incidence created")
                    if (incidence != null) {
                        val res = incidencesService.saveIncidence(incidence)
                        Log.i("TAG_DATABASE_INCIDENCES", "Viewmodel" + res.toString())
                        createIncident.postValue(
                            //incidencesService.saveIncidence(incidence)
                            res
                        )
                    } else {
                        createIncident.postValue(false)
                    }
                } else {
                    createIncident.postValue(false)
                }
            }
        }

    }

}
