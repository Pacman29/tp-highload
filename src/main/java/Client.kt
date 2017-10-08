import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.CacheRequest
import java.net.Socket
import java.net.URLDecoder
import java.util.*

class Client(val socket: Socket, val root: String){
    fun process(){
        try {
            val request = Request(socket)
            checkMethod(request.method)
            checkUrl(request.url)
            var url_without_query = root+request.url.substringBefore("?")

            val isIndex = url_without_query.endsWith('/')
            if (isIndex) {
                url_without_query += "index.html"
            }

            val file = File(url_without_query)
            if (file.isFile) {
                if(request.method == "HEAD"){
                    Response(socket).sendHeader(200,"OK", Date(),file)
                    return
                }
                Response(socket).send(200,"OK", Date(),file)
                return
            }
            if(isIndex){
                throw Exception403()
            }
            throw Exception404()

        } catch (e : ServerException) {
            Response(socket).sendError(e)
        }
        finally {
            socket.close()
        }
    }

    private fun checkMethod(method: String) {
        if(method != "GET" && method != "HEAD"){
            throw Exception405()
        }
    }

    private fun checkUrl(url: String){
        if(url.contains("../")){
            throw Exception400()
        }
    }
}