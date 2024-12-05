#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>
#include "SaidaDeDados.h"

SaidaDeDados* initializeSaidaDeDados(const char* input_directory, const char* output_directory, int frequency) {
    struct stat st = {0};

        //cria e inicializa uma estrutura SaidaDeDados com diretórios de entrada e saída. se nao estiverem criados ele vai cria-los com as permissoes necessárias


    if (stat(input_directory, &st) == -1) {
        mkdir(input_directory, 0700);
    }

    if (stat(output_directory, &st) == -1) {
        mkdir(output_directory, 0700);
    }

    //Aloca dinamicamente uma instância da estrutura SaidaDeDados, copia os diretórios de entrada e saída e define a frequência. Retorna o ponteiro para a instância alocada.

    SaidaDeDados* saida = malloc(sizeof(SaidaDeDados));
    saida->input_directory = strdup(input_directory);
    saida->output_directory = strdup(output_directory);
    saida->frequency = frequency;

    return saida;
}

void processSensorData(SaidaDeDados* saida) {

        //processa os dados dos sensores em um loop infinito. Abre o diretório de entrada e declara uma tabela (latest_data) para armazenar os dados mais recentes de cada sensor.

    while (1) {
        DIR* dir = opendir(saida->input_directory);
        struct dirent* entry;

        // Hash table to store the latest data from each sensor
        SensorData* latest_data[10] = {0};

        //Itera sobre os arquivos no diretório de entrada. Se o tipo de entrada for regular (DT_REG), constrói o caminho completo do arquivo, abre o arquivo e declara uma variável para armazenar cada linha do arquivo.

        while ((entry = readdir(dir)) != NULL) {
            if (entry->d_type == DT_REG) {
                char file_path[256];
                sprintf(file_path, "%s/%s", saida->input_directory, entry->d_name);

                FILE* file = fopen(file_path, "r");
                char line[256];

            //Lê cada linha do arquivo, analisa os dados e armazena na tabela hash latest_data. Fecha o arquivo após a leitura.
                while (fgets(line, sizeof(line), file)) {
                    SensorData sensor;
                    sscanf(line, "%d,%d,%[^,],%[^,],%lf#", &sensor.sensor_id, &sensor.write_counter, sensor.type, sensor.unit, &sensor.buffer.array_buffer[0]);

                    // Convert the sensor value into a real number with two decimal places
                    //Constrói o caminho completo do arquivo de saída, abre o arquivo em modo de escrita.
                    char value[256];
                    sprintf(value, "%.2f", sensor.buffer.array_buffer[0]);

                    // Store the latest data from the sensor
                    latest_data[sensor.sensor_id] = sensor;
                }

                fclose(file);
            }
        }

        closedir(dir);

        // Create a new text file with the latest data from each sensor
        char output_file[256];
        sprintf(output_file, "%s/sensors.txt", saida->output_directory);

        FILE* file = fopen(output_file, "w");
        //Itera sobre a tabela hash latest_data e escreve os dados mais recentes de cada sensor no arquivo de saída.
        for (int i = 0; i < 10; i++) {
            if (latest_data[i] != NULL) {
                fprintf(file, "%d,%d,%s,%s,%.2f#\n", latest_data[i]->sensor_id, latest_data[i]->write_counter, latest_data[i]->type, latest_data[i]->unit, latest_data[i]->buffer.array_buffer[0]);
            }
        }

        //Fecha o arquivo de saída e aguarda a frequência especificada (em milissegundos) antes de processar os dados novamente usando usleep. O loop é infinito, garantindo que o processamento seja contínuo.
        fclose(file);

        // Wait for the specified frequency before processing the data again
        usleep(saida->frequency * 1000);
    }
}