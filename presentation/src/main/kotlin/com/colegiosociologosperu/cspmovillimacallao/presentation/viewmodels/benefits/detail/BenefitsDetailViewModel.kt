package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.detail

import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits.Benefits
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.BenefitsUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenefitsDetailViewModel @Inject constructor(
    private val benefitsUseCase: BenefitsUseCase,
) : CspAppViewModel() {

    private val _benefitsDetail = MutableStateFlow(Benefits())
    val benefitsDetail: StateFlow<Benefits> = _benefitsDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun fetchBenefitsDetail(benefitsId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _benefitsDetail.value = benefitsUseCase.getBenefitsDetail(benefitsId)
            } catch (e: Exception) {
                _errorMessage.value =
                    "Error al cargar el detalle del beneficio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}