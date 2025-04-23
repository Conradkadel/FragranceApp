package com.example.fragranceapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fragranceapp.model.Fragrance
import com.example.fragranceapp.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FragranceViewModel : ViewModel() {
    private val _fragrances = MutableStateFlow<List<Fragrance>>(emptyList())
    val fragrances: StateFlow<List<Fragrance>> = _fragrances.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadFragrances()
    }

    private fun loadFragrances() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getFragrances()
                _fragrances.value = response
            } catch (e: Exception) {
                Log.e("FragranceVM", "loadFragrances failed", e)
            }
        }
    }
    fun getFragranceByName(name: String): Fragrance? {
        return _fragrances.value.find { it.fragrance_name == name }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


}