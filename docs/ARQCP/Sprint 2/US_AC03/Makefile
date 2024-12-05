main: main.o move_num_vec.o enqueue_value.o
	gcc main.o move_num_vec.o enqueue_value.o -z noexecstack -o main

main.o: main.c asm.h asm.h
	gcc -g -Wall -Wextra -fno-analyzer -c main.c -o main.o

move_num_vec.o: move_num_vec.s
	gcc -g -Wall -Wextra -fno-analyzer -c move_num_vec.s -o move_num_vec.o

enqueue_value.o: enqueue_value.s
	gcc -g -Wall -Wextra -fno-analyzer -c enqueue_value.s -o enqueue_value.o

run: main
	./main

clean:
	rm -f *.o main

