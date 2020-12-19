package com.bitxflow.funny.DB

import androidx.room.*

@Dao
interface CartDao {

    @Query("SELECT * from cart")
    fun getCarts(): List<Cart>

    @Query("SELECT * from cart WHERE cartID = :id")
    fun getCart(id: Int): Cart

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart)

    @Update
    fun update(newCart: Cart)

    @Query("DELETE from cart")
    fun deleteAll()

    @Delete
    fun deleteGame(cart: Cart)
}