package com.appozee.technologies.mytestproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.appozee.technologies.mytestproject.database.MedicineDao
import com.appozee.technologies.mytestproject.database.MedicineDatabase
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MedicineDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var medicineDatabase: MedicineDatabase
    @Inject
    lateinit var medicineDao: MedicineDao

    @Before
    fun setUp(){
        hiltAndroidRule.inject()

        medicineDao = medicineDatabase.medicineDao
    }

    @Test
    fun insertMedicine_expectedSingleMedicine() = runBlocking {
        val medicine = MedicineModelItem("10mg","Medicine","High")
        medicineDao.upsert(medicine)
        val result = medicineDao.getMedicineListFlow().first()

        Assert.assertEquals(1,result.size)
        Assert.assertEquals("Medicine",result[0].name)
    }

    @Test
    fun deleteQuote_expectedNoResult() = runBlocking {
        val medicine = MedicineModelItem("10mg","Medicine","High")
        medicineDao.upsert(medicine)

        medicineDao.deleteAll()

        val result = medicineDao.getMedicineListFlow().first()

        Assert.assertEquals(0,result.size)
    }



    @After
    fun tearDown(){
        medicineDatabase.close()
    }
}