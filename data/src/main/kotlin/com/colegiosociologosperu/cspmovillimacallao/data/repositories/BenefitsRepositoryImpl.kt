package com.colegiosociologosperu.cspmovillimacallao.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits.Benefits
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.BenefitsRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BenefitsRepositoryImpl @Inject constructor() : BenefitsRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getAllBenefits(): List<Benefits> {
        return try {
            firestore.collection("benefits")
                .orderBy("order", Query.Direction.ASCENDING)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document.toObject(Benefits::class.java)?.apply {
                        id = document.id
                        Log.d(TAG, "Current data image: $image")
                        Log.d(TAG, "Current data title: $title")
                        Log.d(TAG, "Current data content: $content")
                    }
                }
        } catch (e: Exception) {
            println("Error fetching all benefits: ${e.localizedMessage}")
            emptyList()
        }
    }

    override suspend fun getBenefitsDetail(benefitsId: String): Benefits {
        return try {
            firestore.collection("benefits")
                .document(benefitsId)
                .get()
                .await()
                .toObject(Benefits::class.java) ?: throw Exception("Benefits not found")
        } catch (e: Exception) {
            println("Error fetching benefits detail for $benefitsId: ${e.localizedMessage}")
            throw e
        }
    }
}