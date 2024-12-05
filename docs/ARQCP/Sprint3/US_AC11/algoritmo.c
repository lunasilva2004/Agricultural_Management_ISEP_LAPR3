// Função principal que implementa o algoritmo desejado
void implementarAlgoritmo() {
    int numeroLeituras=10;
    int contadorLeitura = 0;

    while (1) {
        contadorLeitura = 0;
//Loop principal infinito. Inicialização do contador de leitura e loop do-while para realizar leituras do sensor.
        do {
            // Ler dados do sensor
            double sensorData = readSensorData();

            // Extrair informações dos dados do sensor
            int sensorId;
            char sensorType[50];
            char sensorUnit[20];
            extractInfo(sensorData, &sensorId, sensorType, sensorUnit);

            // Criar e configurar a estrutura de dados do sensor
            //Chama a função fill_SensorData para criar e configurar a estrutura de dados do sensor com valores específico
            fill_SensorData(sensor, sensorId, sensorType, sensorUnit, 10, 60, 1);

            // Calcular a mediana
            //Chama a função computeMedian para calcular a mediana dos dados do sensor.
            double median = computeMedian(sensor);

            // Incrementar o contador de escritas
            //Incrementa o contador de escritas e chama a função escreveESerializa para serializar as informações do sensor e escrever no arquivo.
            sensor->writeCounter++;

            // Serializar informações e escrever no arquivo
            escreveESerializa(sensor, sensorsFile);
//Incrementa o contador de leitura e continua o loop do-while até atingir o número desejado de leituras.
            contadorLeitura++;
        } while (contadorLeitura < numeroLeituras);

        // Fechar o arquivo de sensores
        //Fecha o arquivo de sensores e interrompe o loop principal após uma iteração.
        fclose(sensorsFile);

        break;  // Sair do loop após uma iteração por enquanto
    }

    // Limpar e liberar memória
    //Chama a função destroySensorData para limpar e liberar a memória alocada para a estrutura de dados do sensor ao final da execução da função.
    destroySensorData(sensor);
}