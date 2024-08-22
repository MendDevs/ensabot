package com.example.ensachatbot

class Message(var message: String, var sentBy: String) {

    companion object {
        @JvmField
        var SENT_BY_ME = "me"
        @JvmField
        var SENT_BY_BOT = "bot"
    }
}
