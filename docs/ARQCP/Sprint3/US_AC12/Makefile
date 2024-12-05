prog_avg: SaidaDeDados.o main.o
	gcc SaidaDeDados.o main.o -o prog_avg

SaidaDeDados.o: SaidaDeDados.c
	gcc -g -Wall -Wextra -fanalyzer -c SaidaDeDados.c -o SaidaDeDados.o

main.o: main.c
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

clean:
	rm -f *.o prog_avg

run: prog_avg
	./prog_avg

