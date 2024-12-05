prog_avg: ProcessadorDeDados.o main.o
	gcc ProcessadorDeDados.o main.o -o prog_avg

ProcessadorDeDados.o: ProcessadorDeDados.c
	gcc -g -Wall -Wextra -fanalyzer -c ProcessadorDeDados.c -o ProcessadorDeDados.o

main.o: main.c
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

clean:
	rm -f *.o prog_avg

run: prog_avg
	./prog_avg