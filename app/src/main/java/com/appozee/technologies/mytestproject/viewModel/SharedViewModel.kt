package com.appozee.technologies.mytestproject.viewModel

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.appozee.technologies.mytestproject.model.MedicineModelItem

class SharedViewModel : ViewModel() {

    var medicine by mutableStateOf<MedicineModelItem?>(null)
        private set

    fun addCurrentMedicine(newMedicine: MedicineModelItem){
        medicine = newMedicine
    }
}