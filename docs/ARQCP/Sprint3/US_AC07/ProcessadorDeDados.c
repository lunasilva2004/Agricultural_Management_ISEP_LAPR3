    #include <stdlib.h>
    #include <stdio.h>
    #include <string.h>
    #include <sys/stat.h>
    #include "ProcessadorDeDados.h"

    CircularBuffer createCircularBuffer(int size) {
        CircularBuffer buffer;
        buffer.size = size;
        buffer.read = 0;
        buffer.write = 0;
        buffer.array_buffer = malloc(sizeof(int) * size);
        return buffer;
    }

    void destroyCircularBuffer(CircularBuffer* buffer) {
        free(buffer->array_buffer);
    }

    void reallocCircularBuffer(CircularBuffer* buffer, int new_size) {
        buffer->array_buffer = realloc(buffer->array_buffer, sizeof(int) * new_size);
        buffer->size = new_size;
    }

    SensorData* createSensorData(int sensor_id, const char* type, const char* unit, int buffer_size, int timeout) {
        SensorData* sensor = malloc(sizeof(SensorData));
        sensor->sensor_id = sensor_id;
        strcpy(sensor->type, type);
        strcpy(sensor->unit, unit);
        sensor->buffer = createCircularBuffer(buffer_size);
        sensor->median_array = createMedianArray(buffer_size);
        sensor->last_reading_timestamp = 0;
        sensor->timeout = timeout;
        sensor->write_counter = 0;
        return sensor;
    }

    void destroySensorData(SensorData* sensor) {
        destroyCircularBuffer(&sensor->buffer);
        destroyMedianArray(sensor->median_array);
        free(sensor);
    }

    SensorColetor* createSensorColetor(int sensor_id, int time, int value) {
            SensorColetor* sensor = malloc(sizeof(SensorColetor));
            sensor->sensor_id = sensor_id;
            sensor->time = time;
            sensor->value = value;
            return sensor;
        }

    void destroySensorColetor(SensorColetor* sensor) {
            free(sensor);
        }

    void createDirectories(const char* output_directory) {
        struct stat st = {0};
        if (stat(output_directory, &st) == -1) {
            mkdir(output_directory, 0700);
        }
    }

    SensorData* initializeDataProcessor(const char* output_directory) {
        FILE* config_file = fopen("config", "r");
        if (config_file == NULL) {
            printf("Failed to open the configuration file.\n");
            return NULL;
        }

        SensorData* sensors = malloc(sizeof(SensorData) * 10);

        char line[256];
        int i = 0;
        while (fgets(line, sizeof(line), config_file)) {
            int sensor_id = atoi(strtok(line, "#"));
            char* type = strtok(NULL, "#");
            char* unit = strtok(NULL, "#");
            int buffer_size = atoi(strtok(NULL, "#"));
            int timeout = atoi(strtok(NULL, "#"));

            sensors[i] = *createSensorData(sensor_id, type, unit, buffer_size, timeout);
            i++;
        }

        fclose(config_file);

        createDirectories(output_directory);

        return sensors;
    }

    MedianArray* createMedianArray(int size) {
        MedianArray* median_array = malloc(sizeof(MedianArray));
        median_array->vec = malloc(sizeof(int) * size);
        median_array->num = 0;
        return median_array;
    }

    void destroyMedianArray(MedianArray* median_array) {
        free(median_array->vec);
        free(median_array);
    }

    void reallocMedianArray(MedianArray* median_array, int new_size) {
        median_array->vec = realloc(median_array->vec, sizeof(int) * new_size);
    }

    double calculateMedian(MedianArray* median_array) {
        qsort(median_array->vec, median_array->num, sizeof(int), compare);
        if (median_array->num % 2 == 0) {
            return (median_array->vec[median_array->num / 2 - 1] + median_array->vec[median_array->num / 2]) / 2.0;
        } else {
            return median_array->vec[median_array->num / 2];
        }
    }

    int compare(const void* a, const void* b) {
        return (*(int*)a - *(int*)b);
    }

void processDataProcessorComponent(char* data_file_path, char* config_file_path, char* output_directory, int num_readings) {
    SensorData* sensors = initializeDataProcessor(config_file_path);
    int num_sensors = 10; // Assuming we have 10 sensors
        int reading_counter = 0;

        while (reading_counter < num_readings) {
			SensorColetor* sensores = processarDadosColetorArquivo(data_file_path, reading_counter);
			processSensorData(sensors, sensores[0], num_sensors);
            reading_counter++;
        }
    // Don't forget to free the memory
    for (int i = 0; i < num_sensors; i++) {
        destroySensorData(&sensors[i]);
    }
    free(sensors);
}

SensorColetor* processarDadosColetorArquivo(const char *nomeArquivo,int d) {
    SensorColetor* data;
    FILE *arquivo = fopen(nomeArquivo, "r");

    if (arquivo == NULL) {
        perror("Erro ao abrir o arquivo de configuração");
        exit(EXIT_FAILURE);
    }

    int aux_line = d;

           char line[256];

           while (aux_line > 0 && fgets(line, sizeof(line), arquivo) != NULL) {
               aux_line--;
           }

           SensorColetor* sensors = malloc(sizeof(SensorColetor) * 10);
           if (aux_line == 0 && fgets(line, sizeof(line), arquivo) != NULL) {

               int sensor_id;
               int value_sensor;
               long int time;

               extract_token(line, "sensor_id:", &sensor_id);
               extract_token(line, "#type:", NULL); // Ignora este token
               extract_token(line, "#value:", &value_sensor); // Converte double para int (apenas a parte inteira)
               extract_token(line, "#unit:", NULL); // Ignora este token
               extract_token(line, "#time:", &time);

               data->sensor_id = sensor_id;
               data->time = time;
               data->value = value_sensor;

               sensors[0] = *createSensorColetor(sensor_id, time, value_sensor);

           } else {

               printf("A linha %d não foi encontrada.\n", d);

           }

    fclose(arquivo);

    return sensors;
}

void processSensorData(SensorData* sensor,SensorColetor sensorColetor, int arraySize) {

        for (int i = 0; i < arraySize; i++) {

         if (sensor[i].sensor_id == sensorColetor.sensor_id) {

            addValueCircularBuffer(sensor,sensorColetor.value);
            sensor->last_reading_timestamp = time(NULL);

            sensor->write_counter++;

            printBufferValues(&sensor->buffer);

                time_t currentTimestamp = time(NULL);
                    if (currentTimestamp - sensor->last_reading_timestamp > sensor->timeout) {
                    // Lidar com a situação de erro, por exemplo, marcando o sensor ou fazendo alguma ação específica
                    // Exemplo: Marcar o sensor como em erro
                    printf("Sensor %d em situação de erro!\n", sensor->sensor_id);
                 }

             }
        }

    }

    // Função para inicializar o buffer e chamar a função em Assembly
    void addValueCircularBuffer(SensorData* sensor, int value) {
    // Ensure that the buffer has a valid array
    if (sensor->buffer.array_buffer == NULL) {
		// Call the enqueue_value function with the sensor's buffer data
		enqueue_value(sensor->buffer.array_buffer, sensor->buffer.size, &(sensor->buffer.read), &(sensor->buffer.write), value);
    }

}

void printBufferValues(CircularBuffer* buffer) {
    printf("Buffer values: ");
    for (int i = 0; i < buffer->size; i++) {
        printf("%f ", buffer->array_buffer[i]);
    }
    printf("\n");
}

