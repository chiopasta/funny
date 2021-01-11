package com.bitxflow.funny.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "imglink")
class ImgLink @Ignore constructor(
    @PrimaryKey(autoGenerate = true) var ID: Int,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "link") var link : String? = null,
    @ColumnInfo(name = "memo") var memo: String? = null
) {
    constructor() : this(0)
}