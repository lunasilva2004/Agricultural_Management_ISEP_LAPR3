#ifndef SAIDA_DE_DADOS_H
#define SAIDA_DE_DADOS_H

#include <time.h>

typedef struct {
    char* input_directory;
    char* output_directory;
    int frequency;
} SaidaDeDados;

//Declaração da função initializeSaidaDeDados que cria e inicializa uma instância da estrutura SaidaDeDados com os diretórios de entrada e saída, e uma frequência. Retorna um ponteiro para a instância alocada.
SaidaDeDados* initializeSaidaDeDados(const char* input_directory, const char* output_directory, int frequency);
//Declaração da função processSensorData que processa os dados dos sensores em um loop infinito. Recebe uma instância da estrutura SaidaDeDados como parâmetro.
void processSensorData(SaidaDeDados* saida);

#endif // SAIDA_DE_DADOS_H