package com.bitxflow.funny.DB

import android.util.Log
import androidx.room.*

@Dao
interface GameDao {

    @Query("SELECT * from game")
    fun getGames() : List<GameDB>

    @Query("SELECT * from game WHERE id = :id")
    fun getGame(id : String) : GameDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game : GameDB)

    @Update
    fun update(newGame : GameDB)

    @Query("DELETE from game")
    fun deleteAll()

    @Delete
    fun deleteGame(game : GameDB)
}