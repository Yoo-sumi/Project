fun main() {
    var str1: String?
    str1 = null
    val len= str1?.length ?: -1
    println("str1: $str1, length: ${len}")
} 