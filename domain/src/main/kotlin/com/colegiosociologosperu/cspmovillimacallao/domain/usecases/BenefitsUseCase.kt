package com.colegiosociologosperu.cspmovillimacallao.domain.usecases

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits.Benefits
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.BenefitsRepository
import javax.inject.Inject

class BenefitsUseCase @Inject constructor(
    private val repository: BenefitsRepository
) {
    suspend fun getAllBenefits(): List<Benefits> = repository.getAllBenefits()
    
    suspend fun getBenefitsDetail(id: String): Benefits = repository.getBenefitsDetail(id)
}
