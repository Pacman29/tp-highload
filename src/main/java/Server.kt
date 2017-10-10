import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.*
import java.lang.management.ManagementFactory
import java.net.ServerSocket
import java.util.concurrent.*

class Server {
    companion object {
        fun start(port: Int, root: String) {
            val cpus = Runtime.getRuntime().availableProcessors()
            val myCachedThreadPool = ThreadPoolExecutor(cpus, cpus,
                    60L, TimeUnit.SECONDS,
                    SynchronousQueue());
            val cd = myCachedThreadPool.asCoroutineDispatcher()

            val socket = ServerSocket(port)
            println("Server started on port $port")
            println("Pid: ${ManagementFactory.getRuntimeMXBean().getName()}")
            while (true) {
                val accept = socket.accept()
                launch(cd) {
                    Client(accept,root).process()
                }
            }
        }
    }
}