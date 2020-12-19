package com.bitxflow.funny.DB

import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * from product")
    fun getProducts(): List<Product>

    @Query("SELECT * from product WHERE productID = :id")
    fun getProduct(id: Int): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Update
    fun update(newProduct: Product)

    @Query("DELETE from product")
    fun deleteAll()

    @Delete
    fun deleteProduct(product: Product)
}