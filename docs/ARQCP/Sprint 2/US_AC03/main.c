#include <stdio.h>
#include "asm.h"

#define ARRAY_LENGTH 4
#define NUM_LENGTH 3

int main() {
    int array[ARRAY_LENGTH]; // circular buffer of size ARRAY_LENGTH
    int enqueue_read_ptr = 0; // read index for enqueue_value
    int enqueue_write_ptr = 0; // write index for enqueue_value
    int move_num_read_ptr = 3; // read index for move_num_vec
    int move_num_write_ptr = 2; // write index for move_num_vec

    // Separate writes and reads for enqueue_value
    enqueue_value(array, ARRAY_LENGTH, &enqueue_write_ptr, &enqueue_read_ptr, 1);
    enqueue_value(array, ARRAY_LENGTH, &enqueue_write_ptr, &enqueue_read_ptr, 2);
    enqueue_value(array, ARRAY_LENGTH, &enqueue_write_ptr, &enqueue_read_ptr, 3);
    enqueue_value(array, ARRAY_LENGTH, &enqueue_write_ptr, &enqueue_read_ptr, 4);



    // Print the contents of the circular buffer after enqueue_value
    printf("Circular buffer contents after enqueue_value: ");
    for (int i = 0; i < ARRAY_LENGTH; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");

    int vec[NUM_LENGTH]; // array to store moved values

    // Separate read and write pointers for move_num_vec
    if (move_num_vec(array, ARRAY_LENGTH, &move_num_read_ptr, &move_num_write_ptr, NUM_LENGTH, vec) == 0) {
        printf("Could not copy values to vec.\n");
    } else {


        // Print the contents of the moved values by move_num_vec
        printf("Moved values by move_num_vec: ");
        for (int i = 0; i < NUM_LENGTH; i++) {
            printf("%d ", vec[i]);
        }
        printf("\n");

        // Print the contents of the circular buffer after move_num_vec
		printf("Circular buffer contents after move_num_vec: ");
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			printf("%d ", array[i]);
		}
		printf("\n");

    }

    return 0;

}

<>><>>>>>>>>