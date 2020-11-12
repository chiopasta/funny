package com.bitxflow.funny.biz.search

class Game {
    var gameID: String? = null
    var name: String? = null
    var engName: String? = null
    var gameTime : String? = null
    var expTime : String? = null
    var expText : String? = null
    var expImg : String? = null
    var expUrl : String? = null
    lateinit var type : List<String>
    var level : String? = null
    lateinit var people : IntArray
    var recommend : String? = null
    var memo : String? = null
    var hit : Int = 0

    constructor() {}

}