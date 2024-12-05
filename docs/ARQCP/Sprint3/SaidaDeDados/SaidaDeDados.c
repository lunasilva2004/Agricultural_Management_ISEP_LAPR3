#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>
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

void processSensorData(SaidaDeDados* saida) {
    while (1) {
        DIR* dir = opendir(saida->input_directory);
        struct dirent* entry;

        // Hash table to store the latest data from each sensor
        SensorData* latest_data[10] = {0};

        while ((entry = readdir(dir)) != NULL) {
            if (entry->d_type == DT_REG) {
                char file_path[256];
                sprintf(file_path, "%s/%s", saida->input_directory, entry->d_name);

                FILE* file = fopen(file_path, "r");
                char line[256];

                while (fgets(line, sizeof(line), file)) {
                    SensorData sensor;
                    sscanf(line, "%d,%d,%[^,],%[^,],%lf#", &sensor.sensor_id, &sensor.write_counter, sensor.type, sensor.unit, &sensor.buffer.array_buffer[0]);

                    // Convert the sensor value into a real number with two decimal places
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

        for (int i = 0; i < 10; i++) {
            if (latest_data[i] != NULL) {
                fprintf(file, "%d,%d,%s,%s,%.2f#\n", latest_data[i]->sensor_id, latest_data[i]->write_counter, latest_data[i]->type, latest_data[i]->unit, latest_data[i]->buffer.array_buffer[0]);
            }
        }

        fclose(file);

        // Wait for the specified frequency before processing the data again
        usleep(saida->frequency * 1000);
    }
}