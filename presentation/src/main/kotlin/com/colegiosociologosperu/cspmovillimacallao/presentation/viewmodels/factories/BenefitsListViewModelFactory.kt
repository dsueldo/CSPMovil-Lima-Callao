package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.BenefitsRepository
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.BenefitsListViewModel
import javax.inject.Inject

class BenefitsListViewModelFactory @Inject constructor(
    private val benefitsRepository: BenefitsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BenefitsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BenefitsListViewModel(benefitsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}