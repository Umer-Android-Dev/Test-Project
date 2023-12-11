package com.appozee.technologies.mytestproject.api

import com.appozee.technologies.mytestproject.model.MedicineModelItem
import com.appozee.technologies.mytestproject.utils.Constants.GET_MEDICINE_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MedicineApi {

    @GET(GET_MEDICINE_LIST)
    suspend fun getMedicine() : Response<List<MedicineModelItem>>
}