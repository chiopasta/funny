package com.bitxflow.funny.DB

import androidx.room.*

@Dao
interface UserDao  {

    @Query("SELECT * from user")
    fun getUsers(): List<User>

    @Query("SELECT * from user WHERE userID = :id")
    fun getUser(id: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(newUser: User)

    @Query("DELETE from user")
    fun deleteAll()

    @Delete
    fun deleteProduct(user : User)
}