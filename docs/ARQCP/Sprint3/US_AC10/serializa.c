#include <stdlib.h>

void escreveESerializa(SensorData *sensor, const char filename){
    //serializa a informação do sensor
    File *file = fopen(filename, "wb");

    if(file == NULL){
        perror("Erro a abrir o ficheiro");
        exist(EXIT_FAILURE);
    }

   // Escrever os cabeçalhos no arquivo (pode ajustar conforme necessário)
       fwrite(file, "Sensor_ID,Type,Unit,Buffer_Size,Timeout,Last_Reading_Timestamp,Write_Counter\n");

       // Escrever os dados do sensor no arquivo
       fwrite(file, "%d,%s,%s,%d,%d,%ld,%d\n",
               sensor->sensor_id,
               sensor->type,
               sensor->unit,
               sensor->buffer.size,
               sensor->timeout,
               (long)sensor->last_reading_timestamp,
               sensor->write_counter);

       // Fechar o arquivo
       fclose(file);
   }
}

void serializa(SensorData *sensor, FILE *fileName, int mediana){
    fprint(fileName, "%d, %d, %s, %d#\n",  sensor->sensor_id, sensor->buffer->write, sensor->type, sensor->unit, mediana)
}