package com.bitxflow.funny.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class Cart @Ignore constructor(
    @PrimaryKey(autoGenerate = true) var cartID: Int,
    @ColumnInfo(name = "productID") var productID: Int? = null,
    @ColumnInfo(name = "amount") var amount : Int? = null,
    @ColumnInfo(name = "check") var check: Boolean? = null

) {
    constructor() : this(0)
}