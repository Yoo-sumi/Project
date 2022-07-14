fun printArray(){
    print("  ")
    for (i in 0..2)
        print("${i} ")
    println()
    for (i in 0..2){
        print("${i} ")
        for (j in 0..2){
            print("${array[i][j]}")
            if (j < 2){
                print("|")
            }
        }
        if (i < 2){
            println()
            print("  -+-+-")
        }
        println()
    }
}
fun stopArray():Char{
    //cross
    var flag_x_cross_l = true
    var flag_y_cross_l = true
    var flag_x_cross_r = true
    var flag_y_cross_r = true
    for(i in 0..2){
        if (array[i][i] != 'X'){
            flag_x_cross_l = false
        }
        if (array[i][i] != '0'){
            flag_y_cross_l = false
        }
        if (array[2-i][i] != 'X'){
            flag_x_cross_r = false
        }
        if (array[2-i][i] != '0'){
            flag_y_cross_r = false
        }
    }
    if (flag_x_cross_l || flag_x_cross_r) {
        return 'X'
    } else  if (flag_y_cross_l || flag_y_cross_r) {
        return '0'
    }
    // row
    for (i in 0..2) {
        var flag_x = true
        var flag_o = true
        var flag_x_col = true
        var flag_o_col = true
        for (j in 0..2) {
            if (array[i][j] != 'X') {
                flag_x = false
            }
            if (array[i][j] != '0') {
                flag_o = false
            }
            if (array[j][i] != 'X') {
                flag_x_col = false
            }
            if (array[j][i] != '0') {
                flag_o_col = false
            }
        }
        if (flag_o) {
            return '0'
        } else if (flag_x) {
            return 'X'
        } else if (flag_o_col) {
            return '0'
        } else if (flag_x_col) {
            return 'X'
        }
    }
    return ' '
}
var array = arrayOf(arrayOf(' ',' ',' '),arrayOf(' ',' ',' '),arrayOf(' ',' ',' '))
fun main() {
    var turn=0
    while(true) {
        println(" ${turn+1}번째 턴")
        println()
        printArray()
        print("Player ${turn%2+1} 입력(줄,칸): ")
        turn++
        var index = readLine()!!.split(',')
        if (array[index[0].toInt()][index[1].toInt()] == ' ') {
            if(turn % 2 != 0){
                array[index[0].toInt()][index[1].toInt()] = '0'
            }else{
                array[index[0].toInt()][index[1].toInt()] = 'X'
            }
        }
        var result=stopArray()
        if (result!=' '){
            printArray()
            println("Player ${(turn-1)%2+1} 승리!")
            break
        }
    }
}