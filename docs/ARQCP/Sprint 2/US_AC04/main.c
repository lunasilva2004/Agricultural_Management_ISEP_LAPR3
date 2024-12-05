#include <stdio.h>
#include "sort_array.h"

int main(void){
	int array[] = {1,1,1,1,2};
	int* ptr = array;
	int num = sizeof(array)/sizeof(array[0]);
	printf("\n");
	printf("Array na ordem crescente:\n");
	sort_array(ptr, num);
	for (int i = 0; i < num; i++){
		printf("%d ", array[i]);
	}
	printf("\n");
	return 0;
}
