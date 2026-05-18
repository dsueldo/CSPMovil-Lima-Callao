package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.payment

import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.payment.PaymentsItem
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.PaymentsUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val paymentsUseCase: PaymentsUseCase
) : CspAppViewModel() {

    private val _paymentsList = MutableStateFlow<List<PaymentsItem>>(emptyList())
    val paymentsList: StateFlow<List<PaymentsItem>> = _paymentsList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    init {
        fetchPaymentsList()
    }

    private fun fetchPaymentsList(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _isRefreshing.value = true
            } else {
                _isLoading.value = true
            }
            try {
                _paymentsList.value = paymentsUseCase.getAllPayments()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar los pagos: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
                _isRefreshing.value = false
            }
        }
    }

    fun refreshPaymentsList() {
        fetchPaymentsList(isRefresh = true)
    }
}
