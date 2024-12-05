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
    buffer.array_buffer = malloc(sizeof(double) * size);
    return buffer;
}

void destroyCircularBuffer(CircularBuffer* buffer) {
    free(buffer->array_buffer);
}

void reallocCircularBuffer(CircularBuffer* buffer, int new_size) {
    buffer->array_buffer = realloc(buffer->array_buffer, sizeof(double) * new_size);
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

void receiveDataFromCollector(SensorData* sensors, char* data) {
    // Assuming data is a string in the format "sensor_id#value"
    int sensor_id = atoi(strtok(data, "#"));
    double value = atof(strtok(NULL, "#"));

    // Find the corresponding sensor
    for (int i = 0; i < 10; i++) {
        if (sensors[i].sensor_id == sensor_id) {
            // Insert the value into the sensor's circular buffer
            sensors[i].buffer.array_buffer[sensors[i].buffer.write] = value;
            sensors[i].buffer.write = (sensors[i].buffer.write + 1) % sensors[i].buffer.size;

            // Update the last reading timestamp
            sensors[i].last_reading_timestamp = time(NULL);

            // Increment the write counter
            sensors[i].write_counter++;

            break;
        }
    }
}

void insertDataIntoSensor(SensorData* sensors, int sensor_id, double value) {
    // Find the corresponding sensor
    for (int i = 0; i < 10; i++) {
        if (sensors[i].sensor_id == sensor_id) {
            // Insert the value into the sensor's circular buffer
            sensors[i].buffer.array_buffer[sensors[i].buffer.write] = value;
            sensors[i].buffer.write = (sensors[i].buffer.write + 1) % sensors[i].buffer.size;

            // Update the last reading timestamp
            sensors[i].last_reading_timestamp = time(NULL);

            // Increment the write counter
            sensors[i].write_counter++;

            break;
        }
    }
}

void serializeAndWriteToFile(SensorData* sensors, int num_sensors, const char* filename) {
    FILE* file = fopen(filename, "w");
    if (file == NULL) {
        printf("Failed to open the file.\n");
        return;
    }

    for (int i = 0; i < num_sensors; i++) {
        SensorData sensor = sensors[i];
        fprintf(file, "%d,%d,%s,%s,%f#\n", sensor.sensor_id, sensor.write_counter, sensor.type, sensor.unit, calculateMedian(sensor.median_array));
    }

    fclose(file);
}

void processDataProcessorComponent(char* data_file_path, char* config_file_path, char* output_directory, int num_readings) {
    SensorData* sensors = initializeDataProcessor(config_file_path);
    int num_sensors = 10; // Assuming we have 10 sensors

    while (1) {
        int reading_counter = 0;

        while (reading_counter < num_readings) {
            char data[256];
            // Assume that getDataFromCollector() is a function that gets data from the Data Collector Component
            getDataFromCollector(data_file_path, data);

            // Assuming data is a string in the format "sensor_id#value"
            int sensor_id = atoi(strtok(data, "#"));
            double value = atof(strtok(NULL, "#"));

            insertDataIntoSensor(sensors, sensor_id, value);

            reading_counter++;
        }

        for (int i = 0; i < num_sensors; i++) {
            // Compute median
            double median = calculateMedian(sensors[i].median_array);

            // Increment the write counter
            sensors[i].write_counter++;

            // Serialize info
            char filename[256];
            time_t t = time(NULL);
            struct tm tm = *localtime(&t);
            sprintf(filename, "%s/%04d%02d%02d%02d%02d%02d_sensors.txt", output_directory, tm.tm_year + 1900, tm.tm_mon + 1, tm.tm_mday, tm.tm_hour, tm.tm_min, tm.tm_sec);

            serializeAndWriteToFile(sensors, num_sensors, filename);
        }
    }

    // Don't forget to free the memory
    for (int i = 0; i < num_sensors; i++) {
        destroySensorData(&sensors[i]);
    }
    free(sensors);
}