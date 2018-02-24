import java.io.File

fun loadProgram(): List<String> {
       val fileName = "program.fvmasm"
        val fileReader = File(fileName)
        return fileReader.readLines()
}

fun main(args: Array<String>) {
        val sourceCode = loadProgram()
        val assembler = Assembler(sourceCode)
        assembler.assemble()
        val program = assembler.opCodes
        val fvm = ForthVirtualMachine(program)
        fvm.Execute()
        println("\nDone")
}