package com.appozee.technologies.mytestproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import kotlinx.coroutines.flow.Flow


@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(medicineModelItem: MedicineModelItem)

    @Delete
    suspend fun delete(medicineModelItem: MedicineModelItem)

    @Query("DELETE FROM Medicine")
    suspend fun deleteAll()

    @Query("SELECT * FROM Medicine")
    fun getMedicineListFlow(): Flow<List<MedicineModelItem>>

    @Query("SELECT COUNT(*) FROM Medicine WHERE name = :medicineName")
    suspend fun isMedicineAdded(medicineName: String): Int
}