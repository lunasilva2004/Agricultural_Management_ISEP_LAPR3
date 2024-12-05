void processSensorData(SensorData* sensor,SensorColetor sensorColetor, int arraySize) {

        for (int i = 0; i < arraySize; i++) {

         if (sensor[i].sensor_id == sensorColetor.sensor_id) {

            addValueCircularBuffer(sensor,sensorColetor.value);
            sensor->last_reading_timestamp = time(NULL);

            sensor->write_counter++;

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
