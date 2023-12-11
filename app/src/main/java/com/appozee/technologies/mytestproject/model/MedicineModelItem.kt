package com.appozee.technologies.mytestproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appozee.technologies.mytestproject.utils.Constants.MEDICINE_TABLE_NAME

@Entity(tableName = MEDICINE_TABLE_NAME)
data class MedicineModelItem(
    val dose: String,
    @PrimaryKey val name: String,
    val strength: String
)