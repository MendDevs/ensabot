import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

// Extension function to create MediaType from string
fun String.toMediaTypeOrNull(): MediaType? {
    return try {
        this.toMediaType()
    } catch (e: IllegalArgumentException) {
        null
    }
}
