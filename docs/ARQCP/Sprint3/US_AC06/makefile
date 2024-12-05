us06: main.o asm.o 
	gcc main.o asm.o -o us06

main.o : main.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

asm.o : asm.s 
	gcc -g -Wall -Wextra -fanalyzer -c asm.s -o asm.o 

run : us06
	./us06

clean: 
	rm -fv *.o us06
