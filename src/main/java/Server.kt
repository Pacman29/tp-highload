import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.*
import java.net.ServerSocket
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Server {
    companion object {
        fun start(port: Int, root: String) {
            val cpus = Runtime.getRuntime().availableProcessors()
            val cd = Executors.newCachedThreadPool().asCoroutineDispatcher()

            val socket = ServerSocket(port)
            println("Server started on port $port")
            while (true) {
                val accept = socket.accept()
                launch(cd) {
                    Client(accept,root).process()
                }
            }
        }
    }
}