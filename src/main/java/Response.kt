import java.io.File
import java.io.OutputStream
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

class Response(var outputStream: OutputStream) {
    var response: StringBuilder = StringBuilder()

    val dateFormat by lazy {
        val format = SimpleDateFormat("EEE, dd MMM, yyyy HH:mm:ss z", Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("GMT");
        format
    }

    constructor(socket: Socket) : this(socket.getOutputStream()) {
    }

    private fun commonResponse(code: Int, status: String, date: Date){
        response.append("HTTP/1.1 ${code} ${status}\r\n")
                .append("Date: ${dateFormat.format(date)}\r\n")
                .append("Server: tp-highload\r\n")
                .append("Connection: Close\r\n")
    }

    fun sendError(serverException: ServerException){
        send(serverException.code,serverException.description,Date())
    }

    fun send(code: Int, status: String, date: Date) {
        commonResponse(code,status,date)
        outputStream.write(response.toString().toByteArray())
    }

    fun send(code: Int, status: String, date: Date, file: File) {
        sendHeader(code,status,date,file)
        file.inputStream().use { it.copyTo(outputStream) }

}


    fun sendHeader(code: Int, status: String, date: Date, file: File){
        commonResponse(code,status,date)
        response.append("Content-Length: ${file.length()}\r\n")
        getContentType(file.extension)?.let { response.append("Content-Type: $it\r\n") }
        response.append("\r\n")
        outputStream.write(response.toString().toByteArray())
    }

    private fun getContentType(extension: String): String? = when (extension) {
        "txt" -> "text/plain"
        "html" -> "text/html"
        "css" -> "text/css"
        "js" -> "text/javascript"
        "jpg" -> "image/jpeg"
        "jpeg" -> "image/jpeg"
        "gif" -> "image/gif"
        "png" -> "image/png"
        "swf" -> "application/x-shockwave-flash"
        else -> null
    }
}