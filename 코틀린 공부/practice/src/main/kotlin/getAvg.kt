import java.lang.Exception

fun avgFunc(initial: Float=0f, vararg numbers: Float): Double {
    var result = 0f
    for (num in numbers) {
        result+=num
    }
    println("result: $result, numbers.size: ${numbers.size}")
    val avg = result/numbers.size
    return (avg+initial).toDouble()
}

fun main() {
    val result = avgFunc(5f, 100f, 90f, 80f)  // 첫번째 인자: 초기값, 이후 인자는 가변인자
    //println("avg result: $result")
    fun labelBreak() {
        println("labelBreak")
        first@ for(i in 1..5) {
            second@ for (j in 1..5) {
                if (j == 3) break@first
                println("i:$i, j:$j")
            }
            println("after for j")
        }
        println("after for i")
    }
    //labelBreak()

    var amount = 600

    try{
        amount -= 100
        checkAmount(amount)
    }catch (e: Exception){
        println(e.message)
    }
    println("amount: $amount")
}

fun checkAmount(amount: Int){
    if (amount < 1000)
        throw Exception("잔고가 $amount 으로 1000 이하입니다.")
}