package com.test.facts_of_numbers.repository

import androidx.lifecycle.LiveData
import com.test.facts_of_numbers.data.NumberDao
import com.test.facts_of_numbers.model.Numbers

class NumberRepository(private val numberDao: NumberDao) {

    val readAllData: LiveData<List<Numbers>> = numberDao.readAllData()

    suspend fun addNumber(number: Numbers) {
        numberDao.addNumber(number)
    }

    suspend fun deleteNumber(number: Numbers) {
        numberDao.deleteNumber(number)
    }

    suspend fun deleteAllNumbers() {
        numberDao.deleteAllNumbers()
    }
}