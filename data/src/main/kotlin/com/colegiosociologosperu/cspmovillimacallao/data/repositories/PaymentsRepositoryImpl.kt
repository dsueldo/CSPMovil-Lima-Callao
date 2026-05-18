package com.colegiosociologosperu.cspmovillimacallao.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.payment.PaymentsItem
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.PaymentsRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor() : PaymentsRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getAllPayments(): List<PaymentsItem> {
        return try {
            firestore.collection("payments")
                .orderBy("order", Query.Direction.ASCENDING)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document.toObject(PaymentsItem::class.java)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
