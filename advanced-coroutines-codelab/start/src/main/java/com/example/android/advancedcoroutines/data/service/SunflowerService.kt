package com.example.android.advancedcoroutines.data.service

import com.example.android.advancedcoroutines.Plant
import retrofit2.http.GET

interface SunflowerService {
    @GET("googlecodelabs/kotlin-coroutines/master/advanced-coroutines-codelab/sunflower/src/main/assets/plants.json")
    suspend fun getAllPlants(): List<Plant>

    @GET("googlecodelabs/kotlin-coroutines/master/advanced-coroutines-codelab/sunflower/src/main/assets/custom_plant_sort_order.json")
    suspend fun getCustomPlantSortOrder(): List<Plant>
}

