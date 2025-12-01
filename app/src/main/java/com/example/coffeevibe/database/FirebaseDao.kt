package com.example.coffeevibe.database

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.coffeevibe.model.MenuItem
import com.example.coffeevibe.model.OrderItem
import com.example.coffeevibe.utils.NotificationUtils

interface FirebaseDaoInterface {
    fun getAllPositions() : List<MenuItem>
//    fun addOrder(
//        id: Int,
//        idClient: Int,
//        orderNum: Int,
//        quantity: Int,
//        date: String
//    )
    fun getUserId() : String
}

class FirebaseDao(val context: Context) : FirebaseDaoInterface {
    private val db = Firestore.getInstance()

    override fun getAllPositions() : List<MenuItem> {
        val items = mutableListOf<MenuItem>()

        db.collection("Good")
            .get()
        .addOnSuccessListener { result ->
            for (document in result.documents) {
                val item = MenuItem(
                    id = document.get("Id").toString().toInt(),
                    name = document.get("Name").toString(),
                    price = document.get("Price").toString().toInt(),
                    image = document.get("Image").toString(),
                    description = document.get("Description").toString(),
                    category = document.get("Category").toString(),
                    status = document.get("Status").toString()
                )
                items.add(item)
            }
        }
        .addOnFailureListener { exception ->
            Log.d("Error loading", exception.toString())
        }
        return items
    }

//    override fun addOrder(
//        id: Int,
//        idClient: Int,
//        orderNum: Int,
//        quantity: Int,
//        date: String
//    ) {
//        val item = OrderItem(
//            id = id,
//            idClient = idClient,
//            orderNum = orderNum,
//            quantity = quantity,
//            date = date
//        )
//        db.collection("Order")
//            .add(item)
//            .addOnSuccessListener { result ->
//                NotificationUtils.sendSimpleNotification(context, "Order", "Order added" )
//            }
//        .addOnFailureListener { exception ->
//            Log.d("Error adding order", exception.toString())
//        }
//    }

    override fun getUserId() : String {
        var userId = ""
        db.collection("Client")
            .get()
            .addOnSuccessListener { result ->
                userId = result.documents[0].id
            }
        .addOnFailureListener { exception ->
            Log.d("Error getting user id", exception.toString())
        }
        return userId
    }

}