open class ServerException : Exception {
    var code: Int
        get() = field
    var description: String
        get() = field

    constructor(code: Int, description: String) : super(description) {
        this.code = code
        this.description = description
    }

}