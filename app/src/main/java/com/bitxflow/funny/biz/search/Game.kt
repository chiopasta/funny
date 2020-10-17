package com.bitxflow.funny.biz.search

class Game {
    var gameID: String? = null
    var title: String? = null
    var contents: String? = null
    lateinit var type : List<String>
    lateinit var people : IntArray
    var img_url : String? = null

    constructor() {}
    constructor(gameID: String?, contents: String?) : super() {
        this.gameID = gameID
        this.contents = contents
    }

}