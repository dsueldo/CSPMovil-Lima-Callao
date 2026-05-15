package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.BenefitsUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.BenefitsListViewModel
import javax.inject.Inject

class BenefitsListViewModelFactory @Inject constructor(
    private val benefitsUseCase: BenefitsUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BenefitsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BenefitsListViewModel(benefitsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}