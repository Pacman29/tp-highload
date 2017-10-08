import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.net.ServerSocket

class Server {
    companion object {
        fun start(port: Int, root: String) {
            val socket = ServerSocket(port)
            println("Server started on port $port")
            while (true) {
                val accept = socket.accept()
                launch(CommonPool) {
                    Client(accept,root).process()
                }
            }
        }
    }
}