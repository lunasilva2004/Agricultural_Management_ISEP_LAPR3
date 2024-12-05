#include <stdio.h>
#include "SaidaDeDados.h"

int main() {
    SaidaDeDados* saida = initializeSaidaDeDados("./intermedio", "./output", 60000);

    if (saida != NULL) {
        printf("Initialization successful.\n");
    } else {
        printf("Initialization failed.\n");
    }

    // Don't forget to free the memory
    free(saida->input_directory);
    free(saida->output_directory);
    free(saida);

    return 0;
}