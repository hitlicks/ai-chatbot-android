import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatbotRepository {
    private val api = ApiClient.retrofit.create(OpenAIApi::class.java)

    fun getChatbotResponse(prompt: String, callback: (String) -> Unit) {
        val request = CompletionRequest(
            model = "text-davinci-003",
            prompt = prompt,
            max_tokens = 150,
            temperature = 0.7
        )

        api.getCompletion(request).enqueue(object : Callback<CompletionResponse> {
            override fun onResponse(call: Call<CompletionResponse>, response: Response<CompletionResponse>) {
                if (response.isSuccessful) {
                    val chatbotResponse = response.body()?.choices?.firstOrNull()?.text ?: "No response"
                    callback(chatbotResponse)
                } else {
                    callback("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CompletionResponse>, t: Throwable) {
                callback("Failure: ${t.message}")
            }
        })
    }
}
