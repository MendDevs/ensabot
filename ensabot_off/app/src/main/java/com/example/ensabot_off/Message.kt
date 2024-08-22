package com.example.ensabot_off

data class Message(val text: String, val sentBy: String) {
    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "bot"
    }
}
