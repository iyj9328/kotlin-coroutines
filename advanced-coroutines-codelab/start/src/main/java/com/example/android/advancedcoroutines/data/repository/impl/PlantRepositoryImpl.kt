package com.example.android.advancedcoroutines.data.repository.impl

import androidx.lifecycle.LiveData
import com.example.android.advancedcoroutines.GrowZone
import com.example.android.advancedcoroutines.Plant
import com.example.android.advancedcoroutines.data.repository.PlantRepository
import com.example.android.advancedcoroutines.data.service.SunflowerService
import com.example.android.advancedcoroutines.database.PlantDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantDao: PlantDao,
    private val sunflowerService: SunflowerService,
) : PlantRepository {
    /**
     * Fetch a list of [Plant]s from the database.
     * Returns a LiveData-wrapped List of Plants.
     */
    override fun getPlants() : LiveData<List<Plant>> {
        return plantDao.getPlants()
    }

    override fun getPlantsWithGrowZone(growZone: GrowZone): LiveData<List<Plant>> =
        plantDao.getPlantsWithGrowZoneNumber(growZone.number)

    /**
     * Returns true if we should make a network request.
     */
    override fun shouldUpdatePlantsCache(): Boolean {
        // suspending function, so you can e.g. check the status of the database here
        return true
    }

    /**
     * Update the plants cache.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    override suspend fun tryUpdateRecentPlantsCache() {
        if (shouldUpdatePlantsCache()) fetchRecentPlants()
    }

    /**
     * Update the plants cache for a specific grow zone.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    override suspend fun tryUpdateRecentPlantsForGrowZoneCache(growZoneNumber: GrowZone) {
        if (shouldUpdatePlantsCache()) fetchPlantsForGrowZone(growZoneNumber)
    }

    /**
     * Fetch a new list of plants from the network, and append them to [plantDao]
     */
    override suspend fun fetchRecentPlants() {
        val plants = sunflowerService.getAllPlants()
        plantDao.insertAll(plants)
    }

    /**
     * Fetch a list of plants for a grow zone from the network, and append them to [plantDao]
     */
    override suspend fun fetchPlantsForGrowZone(growZone: GrowZone) {
        plantDao.insertAll(plantsByGrowZone(growZone))
    }

    override suspend fun customPlantSortOrder(): List<String> {
        val result = sunflowerService.getCustomPlantSortOrder()
        return result.map { plant -> plant.plantId }
    }

    override suspend fun plantsByGrowZone(growZone: GrowZone) = withContext(Dispatchers.Default) {
        val result = sunflowerService.getAllPlants()
        result.filter { it.growZoneNumber == growZone.number }.shuffled()
    }

    override suspend fun allPlants(): List<Plant> = withContext(Dispatchers.Default) {
        val result = sunflowerService.getAllPlants()
        result.shuffled()
    }
}