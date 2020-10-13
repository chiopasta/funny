package com.bitxflow.funny.biz.search

class Game {
    var gameID: String? = null
    var title: String? = null
    var contents: String? = null
    var type : String?= null
    lateinit var people : IntArray

    constructor() {}
    constructor(gameID: String?, contents: String?) : super() {
        this.gameID = gameID
        this.contents = contents
    }

}