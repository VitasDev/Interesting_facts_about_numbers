package com.test.facts_of_numbers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.test.facts_of_numbers.data.NumberDatabase
import com.test.facts_of_numbers.repository.NumberRepository
import com.test.facts_of_numbers.model.Numbers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumberViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Numbers>>
    private val repository: NumberRepository

    init {
        val numberDao = NumberDatabase.getDatabase(application).numberDao()
        repository = NumberRepository(numberDao)
        readAllData = repository.readAllData
    }
    
    fun addNumber(numbers: Numbers) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNumber(numbers)
        }
    }

    fun deleteNumber(numbers: Numbers) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNumber(numbers)
        }
    }

    fun deleteAllNumbers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNumbers()
        }
    }
}