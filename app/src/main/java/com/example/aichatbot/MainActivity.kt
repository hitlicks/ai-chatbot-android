import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val messages = mutableListOf<ChatMessage>()
    private val adapter = ChatAdapter(messages)
    private val chatbotRepository = ChatbotRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonSend.setOnClickListener {
            val userMessage = editTextMessage.text.toString()
            if (userMessage.isNotEmpty()) {
                addMessage(userMessage, true)
                editTextMessage.text.clear()
                getChatbotResponse(userMessage)
            }
        }
    }

    private fun addMessage(message: String, isUser: Boolean) {
        messages.add(ChatMessage(message, isUser))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun getChatbotResponse(prompt: String) {
        val refinedPrompt = "You are a charming, flirty, and respectful AI. Respond to the following message: $prompt"
        chatbotRepository.getChatbotResponse(refinedPrompt) { response ->
            runOnUiThread {
                addMessage(response, false)
            }
        }
    }
}
