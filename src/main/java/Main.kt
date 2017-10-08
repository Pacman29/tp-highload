
fun main(args: Array<String>){
    var port = 80
    var root = "./"
    var iterator = args.iterator()
    while (iterator.hasNext()){
        when(iterator.next()){
            "-p" -> {
                port = iterator.next().toInt()
            }
            "-r" -> {
                root = iterator.next()
            }
            else -> {
                iterator.next()
            }
        }
    }

    Server.start(port,root)
}