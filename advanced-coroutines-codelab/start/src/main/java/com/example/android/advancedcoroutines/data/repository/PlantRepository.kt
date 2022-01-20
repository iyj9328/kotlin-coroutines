/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.advancedcoroutines.data.repository

import androidx.lifecycle.LiveData
import com.example.android.advancedcoroutines.GrowZone
import com.example.android.advancedcoroutines.Plant

/**
 * Repository module for handling data operations.
 *
 * This PlantRepository exposes two UI-observable database queries [plants] and
 * [getPlantsWithGrowZone].
 *
 * To update the plants cache, call [tryUpdateRecentPlantsForGrowZoneCache] or
 * [tryUpdateRecentPlantsCache].
 */
interface PlantRepository {

    /**
     * Fetch a list of [Plant]s from the database that matches a given [GrowZone].
     * Returns a LiveData-wrapped List of Plants.
     */
    fun getPlants() : LiveData<List<Plant>>
    fun getPlantsWithGrowZone(growZone: GrowZone): LiveData<List<Plant>>
    fun shouldUpdatePlantsCache() : Boolean
    suspend fun tryUpdateRecentPlantsCache()
    suspend fun tryUpdateRecentPlantsForGrowZoneCache(growZoneNumber: GrowZone)
    suspend fun fetchRecentPlants()
    suspend fun fetchPlantsForGrowZone(growZone: GrowZone)

    suspend fun customPlantSortOrder(): List<String>
    suspend fun plantsByGrowZone(growZone: GrowZone) : List<Plant>
    suspend fun allPlants(): List<Plant>
}
