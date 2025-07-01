package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits.Benefits
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.BenefitsRepository
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenefitsListViewModel @Inject constructor(
    private val repository: BenefitsRepository,
) : CspAppViewModel() {

    private val _benefitsList = MutableStateFlow<List<Benefits>>(emptyList())
    val benefitsList: StateFlow<List<Benefits>> = _benefitsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        fetchBenefitsList()
    }

    private fun fetchBenefitsList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _benefitsList.value = repository.getAllBenefits()
                _uiState.value = true
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching benefits: ${e.localizedMessage}"
                Log.w(TAG, "Error fetching benefits", e)
                _uiState.value = false
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshBenefitsList() { fetchBenefitsList() }
}