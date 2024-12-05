#include <stdio.h>
#include "asm.h"

int main(void) {

	int array[] = {0};
	int* ptr = array;
	int num = sizeof(array)/sizeof(array[0]);
	int result;

	result = mediana(ptr,num);

	printf("Result = %d\n", result);

	return 0;
}
