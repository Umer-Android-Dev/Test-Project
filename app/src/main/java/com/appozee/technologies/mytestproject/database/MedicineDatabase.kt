package com.appozee.technologies.mytestproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appozee.technologies.mytestproject.model.MedicineModelItem

@Database(entities = [MedicineModelItem::class], version = 1)
abstract class MedicineDatabase : RoomDatabase() {

    abstract val medicineDao: MedicineDao
}