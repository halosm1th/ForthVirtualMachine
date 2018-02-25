# ForthVirtualMachine
A virtual machine for the simpleFortth Programming language

As well it includes a simple interpreter.

Simple forth syntax is as follows

:wordName @addressOfWord #pushi:50 ;

where 

:wordName is, the name of the word

@addressOfWord gets the address in memory of a label. Which are used for words

\#pushi:50 pushes the value 50 onto the stack

#Virtual machine spec

##Op codes and assembly
The virtual machine contains 21 opcodes currently. They are
0. Pop __(0/1)__ ; pops the value off the stack into register 0 or register 1
1. Pushi: _number_ ; is the number to push onto the stack
2. pushr: __0/1__ ; pushes the value in register 0 or 1 onto the stack
3. add ; adds the top two values on the stack
4. mul ; mulitply the top two values on the stack
5. div ; divides the top two values on the stack
6. sub ; subtract the top two values on the stack
7. mod ; modulus the top two values on the stack
8. printc ; prints the charcter of the value on the top of the stack
9. printi; prints the numerical value off the top of the stack
10. call:_address_;calls the address and pushes the return address onto the call stack
11. ret ;returns to the top value on the call stack
12. jmpe:_address_ ; jumps if the __FLAG__ register is set to 1
13. jmpz:_address_ ; jumps if the __FLAG__ register is set to 2
14. jmp:_address_ ; jumps to _address_
15. cmp ; compares the top two values and leaves them there and sets the __FLAGS__ register to 1 if equal
16. test; test the top value on the stack to see if it is zero. sets __FLAG__ to 2 if it is
17. getc ; gets a charcter and pushes it onto the stack
18. geti ; gets an int and pushes it onto the stack
19. nop ; no operation
20. halt ;stops execution but setting the __RUNNING__ flag to false


##Registers
The machine offers 2 general purpose storage registers for accessable with pop and pushr they are named __0__ adn __1__
As well the machine has the __FLAG__ register which is set based on test to different values
0. cmp returned false
1. cmp returned true
2. test returned true
3. test returned false

as well as a __RUNNING__ register that simply checks if the machine is running.

##RAM
The machine currently support 0xFFFF memory values. This is totally arbitrary and will later be changed to Kotlins Int.MAXVALUE

Currently it is 0xFFFF becaus its easier to debug

##Opcode legnths
most all opcodes are 1 *byte* long but in this case a *byte* is defined as the length of an int for ease of implimentation

Some opcodes have an argument which takes a second byte. 