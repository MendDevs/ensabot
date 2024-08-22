package com.example.ensabot_off

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class chat : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeTextView: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private var messageList: MutableList<Message> = ArrayList()
    private lateinit var messageAdapter: MessageAdapter
    private val JSON = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        messageList = ArrayList()

        recyclerView = findViewById(R.id.recycler_view)
        welcomeTextView = findViewById(R.id.welcome_text)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_btn)

        // RecyclerView setup
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter
        val llm = LinearLayoutManager(this)
        llm.stackFromEnd = true
        recyclerView.layoutManager = llm

        // Retrieve the name from the Intent and display a welcome message
        val sessionName = intent.getStringExtra("SessionNameExtra")
        welcomeTextView.text = "Welcome, $sessionName! Ask your questions."

        // Setting onClickListener for the send button
        sendButton.setOnClickListener {
            val question = messageEditText.text.toString().trim()
            if (question.isNotEmpty()) {
                addToChat(question, Message.SENT_BY_ME)
                messageEditText.setText("")
                callAPI(question)
                welcomeTextView.visibility = View.GONE
            }
        }
    }

    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messageList.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) {
        runOnUiThread {
            messageList.removeAt(messageList.size - 1)
            addToChat(response, Message.SENT_BY_BOT)
        }
    }

    private fun callAPI(question: String?) {
        // Typing indication
        runOnUiThread {
            messageList.add(Message("Typing...", Message.SENT_BY_BOT))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }

        // Create JSON object
        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "gpt-3.5-turbo")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body = RequestBody.create(JSON, jsonBody.toString())

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer sk-proj-fL5Oe22MArl5gbzK4jLDT3BlbkFJgYE7XW5DBR0jRmkDLKOJ") //Insert your API
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("API Error: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")

                        addResponse(result.trim())
                    } catch (e: JSONException) {
                        addResponse("Failed to parse response: " + e.message)
                    }
                } else {
                    addResponse("Failed to load response due to " + response.body!!.string())
                }
            }
        })
    }

    companion object {
        val JSON = "application/json; charset=utf-8".toMediaType()
    }
}
