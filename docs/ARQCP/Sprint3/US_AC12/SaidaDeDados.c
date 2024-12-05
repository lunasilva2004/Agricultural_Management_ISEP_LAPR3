#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include "SaidaDeDados.h"

SaidaDeDados* initializeSaidaDeDados(const char* input_directory, const char* output_directory, int frequency) {
    struct stat st = {0};

    if (stat(input_directory, &st) == -1) {
        mkdir(input_directory, 0700);
    }

    if (stat(output_directory, &st) == -1) {
        mkdir(output_directory, 0700);
    }

    SaidaDeDados* saida = malloc(sizeof(SaidaDeDados));
    saida->input_directory = strdup(input_directory);
    saida->output_directory = strdup(output_directory);
    saida->frequency = frequency;

    return saida;
}