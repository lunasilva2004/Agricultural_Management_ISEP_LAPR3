#include <stdio.h>
#include "ProcessadorDeDados.h"

int main(int argc, char *argv[]) {
    if (argc != 5) {
        printf("Usage: %s <data_file_path> <config_file_path> <output_directory> <num_readings>\n", argv[0]);
        return 1;
    }

    char* data_file_path = argv[1];
    char* config_file_path = argv[2];
    char* output_directory = argv[3];
    int num_readings = atoi(argv[4]);

    SensorData* sensors = initializeDataProcessor(config_file_path);

    if (sensors != NULL) {
        printf("Initialization successful.\n");

        // Call the processDataProcessorComponent function
        processDataProcessorComponent(data_file_path, config_file_path, output_directory, num_readings);
    } else {
        printf("Initialization failed.\n");
    }

    // Don't forget to free the memory
    for (int i = 0; i < 10; i++) {
        destroySensorData(&sensors[i]);
    }
    free(sensors);

    return 0;
}