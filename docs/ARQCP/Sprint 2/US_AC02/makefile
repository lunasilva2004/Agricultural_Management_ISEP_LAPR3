us02: main.o asm.o
	gcc main.o asm.o -o us02

main.o : main.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

asm.o : asm.s 
	gcc -g -Wall -Wextra -fanalyzer -c asm.s -o asm.o
	
run : us02
	./us02

clean:
	rm -fv *.o us02
