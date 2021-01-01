package com.bitxflow.funny.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User @Ignore constructor(
    @PrimaryKey(autoGenerate = true) var ID: Int,
    @ColumnInfo(name = "userID") var userID: String? = null,
    @ColumnInfo(name = "pass") var pass : String? = null,
    @ColumnInfo(name = "memo") var memo: String? = null
) {
    constructor() : this(0)
}