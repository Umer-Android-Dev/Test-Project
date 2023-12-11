package com.appozee.technologies.mytestproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appozee.technologies.mytestproject.database.MedicineDao
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import com.appozee.technologies.mytestproject.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineDetailViewModel @Inject constructor(private val repository: MedicineRepository)
    :ViewModel(){

    val medicinesDataList: StateFlow<List<MedicineModelItem>>
        get() = repository.medicineDbList

    init {
        viewModelScope.launch {
            repository.getDataList()
        }
    }

    fun addMedicine(medicineModelItem: MedicineModelItem) {
        viewModelScope.launch {
            repository.addMedicine(medicineModelItem)
        }
    }


    fun removeMedicine(medicineModelItem: MedicineModelItem) {
        viewModelScope.launch {
            repository.removeMedicine(medicineModelItem)
        }
    }

    suspend fun isMedicineAdded(medicineModelItem: MedicineModelItem): Boolean {
        return repository.isMedicineAdded(medicineModelItem.name)
    }
}