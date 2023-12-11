package com.appozee.technologies.mytestproject.di

import android.content.Context
import androidx.room.Room
import com.appozee.technologies.mytestproject.database.MedicineDao
import com.appozee.technologies.mytestproject.database.MedicineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun providesTestDB(@ApplicationContext context: Context) : MedicineDatabase{
        return Room.inMemoryDatabaseBuilder(
            context,
            MedicineDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideTestDao(
        medicineDatabase: MedicineDatabase
    ): MedicineDao = medicineDatabase.medicineDao
}