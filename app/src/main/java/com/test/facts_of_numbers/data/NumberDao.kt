package com.test.facts_of_numbers.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.facts_of_numbers.model.Numbers

@Dao
interface NumberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNumber(numbers: Numbers)

    @Delete
    suspend fun deleteNumber(number: Numbers)

    @Query("DELETE FROM number_table")
    suspend fun deleteAllNumbers()

    @Query("SELECT * FROM number_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Numbers>>
}