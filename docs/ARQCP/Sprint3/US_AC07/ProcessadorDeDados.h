#ifndef PROCESSADOR_DE_DADOS_H
#define PROCESSADOR_DE_DADOS_H

#include <time.h>

typedef struct {
    int size;
    int read;
    int write;
    int* array_buffer;
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

typedef struct {
    int sensor_id;
    int time;
    int value;
} SensorColetor;

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
int extract_token(char* input, char* token, int* output);
SensorColetor* processarDadosColetorArquivo(const char *nomeArquivo,int d);
void processDataProcessorComponent(char* data_file_path, char* config_file_path, char* output_directory, int num_readings);
SensorColetor* createSensorColetor(int sensor_id, int time, int value);
void destroySensorColetor(SensorColetor* sensor);
int move_num_vec(int* array, int length, int *read, int *write, int num, int *vec);
void enqueue_value(int* array, int length, int* read, int* write, int value);
void processSensorData(SensorData* sensor,SensorColetor sensorColetor, int arraySize);
void addValueCircularBuffer(SensorData* sensor, int value);
void printBufferValues(CircularBuffer* buffer);

#endif // PROCESSADOR_DE_DADOS_H
