package com.example.productmanager.ui.admin.ui.incidences

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmanager.domain.model.entities.Incidence
import com.example.productmanager.domain.model.services.IncidencesService
import com.example.productmanager.domain.model.services.NotificationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IncidencesViewModel @Inject constructor(
    private val incidencesService: IncidencesService,
    private val notificationService: NotificationService
) : ViewModel() {

    val updateIncidents = MutableLiveData<MutableList<Incidence>?>()
    val solveInvident = MutableLiveData<Boolean?>()

    /*
      Call to method get to obtain all incidences created by users. Post result
    */
    fun onUpdateSelected() {
        viewModelScope.launch {
            updateIncidents.postValue(withContext(Dispatchers.IO) {
                incidencesService.getIncidences()
            })
        }
    }
    /*
       Call to method solveIncidence to solve an incidences created by user
     */
    fun onDeleteItem(incidence: Incidence) {
        viewModelScope.launch {
            solveInvident.postValue(
                withContext(Dispatchers.IO) {
                    incidencesService.solveIncidence(incidence)
                }

            )

        }
    }

    /*
    Call notify method to start a notification service
     */
    fun notifySolveIncident(
        incidence: Incidence,
        notification: String,
        subject: String,
        context: Context?
    ) {
        viewModelScope.launch {
            if (context != null) {
                notificationService.notify(incidence.employee, notification, subject, context)
            }
        }
    }

}




