import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {
    @Headers("Content-Type: application/json")
    @POST("completions")
    fun getCompletion(@Body request: CompletionRequest): Call<CompletionResponse>
}

data class CompletionRequest(
    val model: String,
    val prompt: String,
    val max_tokens: Int,
    val temperature: Double
)

data class CompletionResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val text: String
)
