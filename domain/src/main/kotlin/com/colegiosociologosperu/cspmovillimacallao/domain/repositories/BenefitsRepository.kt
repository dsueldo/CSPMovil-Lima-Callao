package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits.Benefits

interface BenefitsRepository {
    suspend fun getAllBenefits(): List<Benefits>
    suspend fun getBenefitsDetail(benefitsId: String): Benefits
}