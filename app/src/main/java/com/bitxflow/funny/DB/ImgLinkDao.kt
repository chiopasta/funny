package com.bitxflow.funny.DB

import androidx.room.*

@Dao
interface ImgLinkDao  {

    @Query("SELECT * from imglink")
    fun getImgLink(): List<ImgLink>

    @Query("SELECT * from imglink WHERE name = :name")
    fun getImgLink(name: String): ImgLink

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imglink: ImgLink)

    @Update
    fun update(newUser: ImgLink)

    @Query("DELETE from imglink")
    fun deleteAll()

    @Delete
    fun deleteProduct(imglink : ImgLink)
}