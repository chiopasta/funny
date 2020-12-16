package com.bitxflow.funny.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "game")
class GameDB @Ignore constructor(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "engName") var engName: String? = null,
    @ColumnInfo(name = "gameImgUrl ") var gameImgUrl : String? = null,
    @ColumnInfo(name = "gameTime") var gameTime: String? = null,
    @ColumnInfo(name = "expTime") var expTime: String? = null,
    @ColumnInfo(name = "expText") var expText: String? = null,
    @ColumnInfo(name = "expImg") var expImg: String? = null,
    @ColumnInfo(name = "expUrl") var expUrl: String? = null,
    @ColumnInfo(name = "type") var type: String? = null,
    @ColumnInfo(name = "level") var level: String? = null,
    @ColumnInfo(name = "people") var people: String? = null,
    @ColumnInfo(name = "recommend") var recommend: String? = null,
    @ColumnInfo(name = "memo") var memo: String? = null,
    @ColumnInfo(name = "hit") var hit: Int? = null
) {
    constructor() : this(0)
}