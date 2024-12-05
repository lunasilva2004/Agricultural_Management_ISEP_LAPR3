#ifndef PROCESSADOR_DE_DADOS_H
#define PROCESSADOR_DE_DADOS_H

#include <time.h>

typedef struct {
    int size;
    int read;
    int write;
    double* array_buffer;
} CircularBuffer;

typedef struct {
    int* vec;
    int num;
} MedianArray;

typedef struct {
    int sensor_id;
    char type[50];
    char unit[20];
    CircularBuffer buffer;
    MedianArray* median_array;
    time_t last_reading_timestamp;
    int timeout;
    int write_counter;
} SensorData;

CircularBuffer createCircularBuffer(int size);
void destroyCircularBuffer(CircularBuffer* buffer);
void reallocCircularBuffer(CircularBuffer* buffer, int new_size);
SensorData* createSensorData(int sensor_id, const char* type, const char* unit, int buffer_size, int timeout);
void destroySensorData(SensorData* sensor);
void createDirectories(const char* output_directory);
SensorData* initializeDataProcessor(const char* output_directory);
MedianArray* createMedianArray(int size);
void destroyMedianArray(MedianArray* median_array);
void reallocMedianArray(MedianArray* median_array, int new_size);
double calculateMedian(MedianArray* median_array);
int compare(const void* a, const void* b);
void receiveDataFromCollector(SensorData* sensors, char* data);
void insertDataIntoSensor(SensorData* sensors, int sensor_id, double value);
void serializeAndWriteToFile(SensorData* sensors, int num_sensors, const char* filename);
void processDataProcessorComponent(char* data_file_path, char* config_file_path, char* output_directory, int num_readings);

#endif // PROCESSADOR_DE_DADOS_H