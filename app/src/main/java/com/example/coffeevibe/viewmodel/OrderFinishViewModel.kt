package com.example.coffeevibe.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coffeevibe.database.CartEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.time.Instant
import kotlin.random.Random

@SuppressLint("SimpleDateFormat")
class OrderFinishViewModel : ViewModel() {
    private val firestore = Firebase.firestore


    fun createOrder(idUser: String,
                    idAddress: Int,
                    totalPrice: Int,
                    items: List<CartEntity>,
                    idPickupTime: Int,
                    onCompleted: () -> Unit) {
        val idOrder = Random.nextInt(0, 9999).toString()
        val timestamp = Timestamp.from(Instant.now())

        try {
            firestore.collection("Order").document(idOrder).set(
                mapOf(
                    "Date" to timestamp,
                    "IdClient" to idUser,
                    "IdLocation" to idAddress,
                    "Status" to "Создан",
                    "TotalPrice" to totalPrice,
                    "PickupTime" to when (idPickupTime){
                        0 -> Timestamp.from(timestamp.toInstant().plusSeconds(5 * 60))
                        1 -> Timestamp.from(timestamp.toInstant().plusSeconds(15 * 60))
                        2 -> Timestamp.from(timestamp.toInstant().plusSeconds(30 * 60))
                        3 -> Timestamp.from(timestamp.toInstant().plusSeconds(60 * 60))
                        else -> timestamp.toInstant()
                    },
                )
            )
                .addOnSuccessListener {
                    onCompleted()

                    items.forEach {
                        val idOrder2 = Random.nextInt(0, 9999).toString()
                        firestore.collection("OrderItem").document(idOrder2).set(
                            mapOf(
                                "IdOrder" to idOrder,
                                "IdGood" to it.idItem,
                                "Quantity" to it.quantity,
                            )
                        )
                    }
                }
                .addOnFailureListener{
                    onCompleted()
                }
        }
        catch (e: Exception) {
            Log.d("ErrorCreateOrder", e.toString())
        }
    }
}