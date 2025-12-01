package com.example.coffeevibe.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cartItems")
    fun getAllItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(password: CartEntity)

    @Delete
    suspend fun deleteItem(password: CartEntity)

    @Query("DELETE FROM cartItems WHERE idItem = :id")
    suspend fun deleteItemById(id: Int)

    @Update
    suspend fun updateItem(password: CartEntity)

    @Query("DELETE FROM cartItems")
    suspend fun deleteAllItems()

    @Query("SELECT * FROM cartItems WHERE id = :id LIMIT 1")
    fun getItemById(id: Int): CartEntity
}