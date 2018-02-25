//Read evaluate print loop
//Forth contains 5 keywords and 6 possible values.
// they are
// ? Import
// # inlnie ams
// @ address of command
// : create new word
// ; end word
// CALLWORD
// push value

//each line should be read in. splint into its lines. and then eval each of them into assembly, storing the result in
//A string list of each command and then run that through the assembler and then run it into the vm.
fun whatIsIt(value:String):String{
    if(value.startsWith(":")){
        return "label"+value
    }else if (value.startsWith("#")){
        return value.removePrefix("#")
    }else if (value.startsWith(";")){
        return "ret"
    }else if (value.startsWith("?")){
        return "nop"
    }else if (value.startsWith("@")){
        return "pusha:" + value
    }else{
        return "call:" + value
    }
}

fun eval(line:String){
    val assembly = mutableListOf<String>()
    val tokens = line.split(" ")
    for(token in tokens){
        if(token.toIntOrNull() == null){
            assembly.add(whatIsIt(token))
        }else{
            assembly.add("pushi " + token)
        }
    }
    val asm = Assembler(assembly)
    asm.assemble()
    val opCodes = asm.opCodes
    val vm = ForthVirtualMachine(opCodes)
    vm.execute()
}

fun Interpret(){
    //Loop
    while(true){
        //Read
        val line = readLine() ?: "nop"
        eval(line)
    }
}