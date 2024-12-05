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





