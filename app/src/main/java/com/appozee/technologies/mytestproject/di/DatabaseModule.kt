package com.appozee.technologies.mytestproject.di

import android.app.Application
import androidx.room.Room
import com.appozee.technologies.mytestproject.database.MedicineDao
import com.appozee.technologies.mytestproject.database.MedicineDatabase
import com.appozee.technologies.mytestproject.utils.Constants.MEDICINE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMedicineDatabase(
        application: Application
    ): MedicineDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = MedicineDatabase::class.java,
            name = MEDICINE_DATABASE_NAME
        ).build()
    }


    @Singleton
    @Provides
    fun provideMedicineDao(
        medicineDatabase: MedicineDatabase
    ):MedicineDao = medicineDatabase.medicineDao
}