#ifndef SAIDA_DE_DADOS_H
#define SAIDA_DE_DADOS_H

#include <time.h>

typedef struct {
    char* input_directory;
    char* output_directory;
    int frequency;
} SaidaDeDados;

SaidaDeDados* initializeSaidaDeDados(const char* input_directory, const char* output_directory, int frequency);
void processSensorData(SaidaDeDados* saida);

#endif // SAIDA_DE_DADOS_H