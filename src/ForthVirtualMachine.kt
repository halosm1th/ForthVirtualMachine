/**
@property Command A FVM Command
 **/
enum class Command(val commandValue:Int){
    POP(0), //arg (register)
    PUSHI(1), //arg value
    PUSHR(2), //arg register
    ADD(3),
    MUL(4),
    DIV(5),
    SUB(6),
    MOD(7),
    PRINTC(8),
    PRINTI(9),
    CALL(10), //arg address
    RET(11),
    JMPE(12), //address
    JMPZ(13), //address
    JMP(14), //address
    CMP(15),
    TEST(16),
    GETC(17),
    GETI(18),
    NOP(19),
    HALT(20)
}

class ForthVirtualMachine(private var ram:IntArray){

    //Variables
    //region variables
    private var instructionPointer = -1
    private var stackPointer = 0xfff/2
    private var functionCallList = mutableListOf<Int>()
    private var eax:Int =0
    private var ebx:Int = 0
    private var running = true

    //1 means the top two values are equal 0 means no equal. 2 means the top value is equal to 0. 3 means not zero
    private var flag:Int = 0
    //endregion

    //region commands
    //region direct_stack_ops
    //Commands for the vm
    private fun popR(){
        val reg = getNext()
        when (reg){
            0-> eax = ram[stackPointer]
            else -> ebx = ram[stackPointer]
        }
    }

    private fun pop():Int{
        val value = ram[stackPointer]
        stackPointer--
        return value
    }

    //region push
    private fun push(value:Int) {
        stackPointer++
        ram[stackPointer] = value
    }

    private fun pushi(){
        val value = getNext()
        push(value)
    }

    private fun pushr(){
        val reg = getNext()
        when(reg){
            0 -> push(eax)
            else -> push(ebx)
        }
    }
    //endregion push
    //endregion direct_stack_ops
    //region math_ops
    private fun mathOp(mathCommand: (num1:Int, num2:Int) -> Int){
        val num1 = pop()
        val num2 = pop()
        val result = mathCommand(num1,num2)
        push(result)
    }

    private fun add(){
        mathOp({num1,num2 -> num1 + num2})
    }

    private fun sub(){
        mathOp({num1,num2 -> num1 - num2})
    }

    private fun mul(){
        mathOp({num1,num2 -> num1 * num2})
    }
    private fun div(){
        mathOp({num1,num2 -> num1 / num2})
    }
    private fun mod(){
        mathOp({num1,num2 -> num1 % num2})
    }
    //endregion math_ops
    //region IO
    private fun getC(){
        val input = readLine()
        if(input != null){
            push(input[0].toInt())
        }else{
            print("Error input invalid!?!?")
        }
    }

    private fun getI(){
        val input = readLine()
        if(input != null){
            push(input.toInt())
        }else{
            print("Error input invalid!?!?")
        }
    }

    private fun printC(){
        val value = pop()
        print(value.toChar())
    }

    private fun printI() {
        val value = pop()
        print(value)
    }
    //endregion IO
    //region Comparison
    private fun test(){
        val num1 = pop()
        flag = if (num1 == 0){
            2
        }else{
            3
        }
        push(num1)
    }

    private fun cmp(){
        val num1 = pop()
        val num2 = pop()
        push(num2)
        push(num1)
        flag = if(num1 == num2){
            1
        }else{
            0
        }
    }
    //endregion Comparison
    //region ipChange
    //region jump
    private fun jmp(){
        val newIP = getNext()
        instructionPointer = newIP
    }

    private fun jmpe(){
        val ip = getNext()
        if(flag == 1){
            instructionPointer = ip
        }
    }

    private fun jmpz(){
        val ip = getNext()
        if(flag == 3){
            instructionPointer = ip
        }
    }

    //endregion jump
    //region CallRet
    private fun call(){
        val currentIp = instructionPointer + 1
        functionCallList.add(currentIp)
        instructionPointer = getNext()
    }

    private fun ret(){
        val retValue = functionCallList.last()
        functionCallList.removeAt(functionCallList.size-1)
        instructionPointer = retValue
    }
    //endregion CallRet

    //endregion ipChange
    //region otherOps
    private fun nop(){

    }

    private fun halt(){
        running = false
    }
    //endregion otherOps
    //endregion commands

    private fun error(location:Int) {
        println("Error with evaluation, at $location with opcode ${Command.values()[ram[instructionPointer]]}")
    }

    //Execution code for the vm
    private fun executeCommand(command:Command){
        when (command){
            Command.POP -> popR()
            Command.PUSHI -> pushi()
            Command.PUSHR -> pushr()
            Command.GETC -> getC()
            Command.GETI -> getI()
            Command.HALT -> halt()
            Command.NOP -> nop()
            Command.ADD -> add()
            Command.SUB -> sub()
            Command.MUL -> mul()
            Command.DIV -> div()
            Command.MOD -> mod()
            Command.TEST -> test()
            Command.CMP -> cmp()
            Command.PRINTC -> printC()
            Command.PRINTI -> printI()
            Command.JMP -> jmp()
            Command.JMPE -> jmpe()
            Command.JMPZ -> jmpz()
            Command.CALL -> call()
            Command.RET -> ret()
            else -> error(instructionPointer)
        }
    }

    private fun getNext():Int{
        instructionPointer++
        return ram[instructionPointer]
    }

    fun execute(){
        while(running) {
            val command = getNext()
            executeCommand(Command.values()[command])
        }
    }
}
