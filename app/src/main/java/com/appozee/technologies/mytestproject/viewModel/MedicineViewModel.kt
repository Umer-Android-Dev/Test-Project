package com.appozee.technologies.mytestproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import com.appozee.technologies.mytestproject.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(private val repository: MedicineRepository) : ViewModel()  {

    val medicines: StateFlow<List<MedicineModelItem>>
        get() = repository.medicine

    init {
        viewModelScope.launch {
            repository.getMedicines()
        }
    }


    fun addMedicine(medicineModelItem: MedicineModelItem) {
        viewModelScope.launch {
            repository.addMedicine(medicineModelItem)
        }
    }
}