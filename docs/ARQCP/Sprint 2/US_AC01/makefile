us01: main.o asm.o 
	gcc main.o asm.o -o us01

main.o : main.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

asm.o : asm.s 
	gcc -g -Wall -Wextra -fanalyzer -c asm.s -o asm.o 

run : us01
	./us01

clean: 
	rm -fv *.o us01
