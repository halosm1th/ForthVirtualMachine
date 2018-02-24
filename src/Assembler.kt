
class Assembler(private val sourceCode:List<String>){

    private var labels = mutableMapOf<String,Int>()
    var opCodes = IntArray(0xFFFF)
    private var iterator:Int = 0

    private fun label(arg:String):IntArray{
        labels[arg] = iterator
        return intArrayOf(Command.NOP.commandValue)
    }


    private fun generateLabel(arg:String):Int{
        val id = labels[arg] ?: return 0
        return id
    }
    private fun spaceDelim(line:String):IntArray{
        val parts = line.split(" ")
        val command = parts[0]
        val arg = parts[1]
        return when(command.toLowerCase()){
            "pop" -> intArrayOf(Command.POP.commandValue,arg.toInt())
            "pushi" -> intArrayOf(Command.PUSHI.commandValue,arg.toInt())
            "pushr" -> intArrayOf(Command.PUSHR.commandValue,arg.toInt())
            "call" -> intArrayOf(Command.CALL.commandValue,generateLabel(arg))
            "jmpe" -> intArrayOf(Command.JMPE.commandValue,generateLabel(arg))
            "jmpz" -> intArrayOf(Command.JMPZ.commandValue,generateLabel(arg))
            "jmp" -> intArrayOf(Command.JMP.commandValue,generateLabel(arg))
            "label" -> label(arg)
            else -> intArrayOf(Command.NOP.commandValue)
        }
    }

    private fun normal(line:String):Int{
        when(line.toLowerCase()){
            "add" -> return Command.ADD.commandValue
            "mul" -> return Command.MUL.commandValue
            "div" -> return Command.DIV.commandValue
            "sub" -> return Command.SUB.commandValue
            "mod" -> return Command.MOD.commandValue
            "printc" -> return Command.PRINTC.commandValue
            "printi" -> return Command.PRINTI.commandValue
            "ret" -> return Command.RET.commandValue
            "cmp" -> return Command.CMP.commandValue
            "test" -> return Command.TEST.commandValue
            "getc" -> return Command.GETC.commandValue
            "geti" -> return Command.GETI.commandValue
            "nop" -> return Command.NOP.commandValue
            "halt" -> return Command.HALT.commandValue
            else -> return Command.NOP.commandValue
        }
    }

    fun assemble(){
       for(s in sourceCode){
           if(s.contains(" ")){
               val values= spaceDelim(s)
               for(value in values){
                   opCodes[iterator] = value
                   iterator++
               }
           }else{
               opCodes[iterator]= normal(s)
               iterator++
           }
       }
    }
}