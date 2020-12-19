package com.bitxflow.funny.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "product")
class Product @Ignore constructor(
    @PrimaryKey(autoGenerate = true) var productID: Int,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "description ") var description : String? = null,
    @ColumnInfo(name = "price") var price: Int? = null,
    @ColumnInfo(name = "type") var type: String? = null,
    @ColumnInfo(name = "memo") var memo: String? = null
) {
    constructor() : this(0)
}