import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import java.net.URLDecoder

class Request() {

    var method: String = ""
        get() = field
    var url: String = ""
        get() = field

    constructor(socket: Socket) : this() {
        val request = BufferedReader(InputStreamReader(socket.getInputStream())).readLine()
        println(request);
        if(request == null){
            throw Exception400();
        }
        requestParsing(request)
    }

    private fun requestParsing(request: String){
        val split = request.split(" ")

        if(split[0] == ""){
            throw Exception404()
        }
        this.method = split[0]
        this.url = URLDecoder.decode(split[1], "UTF-8")
    }
}