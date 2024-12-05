#include <stdio.h>
#include "asm.h"

//define uma constante para definir o tamanho do array/buffer
#define ARRAY_LENGHT 5

int main() {
    int array[ARRAY_LENGHT]; // circular buffer of size ARRAY_LENGHT
    int read = 0; // read index of the circular buffer
	int write = 0; // write index of the circular buffer
	
	//void enqueue_value(int* array, int length, int* read, int* write, int value);
    // insert value into the circular buffer
    enqueue_value(array, ARRAY_LENGHT, &read, &write, 10);
    enqueue_value(array, ARRAY_LENGHT, &read, &write, 20);
	enqueue_value(array, ARRAY_LENGHT, &read, &write, 30);
    enqueue_value(array, ARRAY_LENGHT, &read, &write, 40);
    enqueue_value(array, ARRAY_LENGHT, &read, &write, 50);
    //este substitui o 1º, uma vez que vai preencher de novo a posiçao 0 do buffer
    enqueue_value(array, ARRAY_LENGHT, &read, &write, 60);

    // print the contents of the circular buffer
    printf("Circular buffer contents: ");
    for (int i = 0; i < ARRAY_LENGHT; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
   
    return 0;
}
