prog_avg: ProcessadorDeDados.o extract_token.o enqueue_value.o move_num_vec.o main.o
	gcc ProcessadorDeDados.o extract_token.o enqueue_value.o move_num_vec.o main.o -o prog_avg

ProcessadorDeDados.o: ProcessadorDeDados.c
	gcc -g -Wall -Wextra -fanalyzer -c ProcessadorDeDados.c -o ProcessadorDeDados.o

main.o: main.c
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

extract_token.o : extract_token.s
	gcc -g -Wall -Wextra -fanalyzer -c extract_token.s -o extract_token.o

enqueue_value.o : enqueue_value.s
	gcc -g -Wall -Wextra -fanalyzer -c enqueue_value.s -o enqueue_value.o

move_num_vec.o : move_num_vec.s
	gcc -g -Wall -Wextra -fanalyzer -c move_num_vec.s -o move_num_vec.o

clean:
	rm -f *.o prog_avg

run: prog_avg
	./prog_avg /media/sf_shared/sprint3/leitura config intermedio 50
