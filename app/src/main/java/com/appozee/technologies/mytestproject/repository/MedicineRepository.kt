package com.appozee.technologies.mytestproject.repository

import com.appozee.technologies.mytestproject.api.MedicineApi
import com.appozee.technologies.mytestproject.database.MedicineDao
import com.appozee.technologies.mytestproject.database.MedicineDatabase
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val medicineApi: MedicineApi,
    private val medicineDao: MedicineDao
) {

    private val _medicine = MutableStateFlow<List<MedicineModelItem>>(emptyList())
    val medicine: StateFlow<List<MedicineModelItem>>
        get() = _medicine


    private val _medicineDbList = MutableStateFlow<List<MedicineModelItem>>(emptyList())
    val medicineDbList: StateFlow<List<MedicineModelItem>>
        get() = _medicineDbList


    suspend fun getMedicines() {
        val result = medicineApi.getMedicine()
        if (result.isSuccessful && result.body() != null) {
            _medicine.emit(result.body()!!)
        }
    }

    suspend fun addMedicine(medicineModelItem: MedicineModelItem){
        medicineDao.upsert(medicineModelItem)
    }

    suspend fun removeMedicine(medicineModelItem: MedicineModelItem) {
        medicineDao.delete(medicineModelItem)
    }

    suspend fun isMedicineAdded(medicineName: String): Boolean {
        return withContext(Dispatchers.IO) {
            medicineDao.isMedicineAdded(medicineName) > 0
        }
    }

    suspend fun getDataList(){
        medicineDao.getMedicineListFlow().collect { medicineList ->
            _medicineDbList.value = medicineList
        }

    }
}