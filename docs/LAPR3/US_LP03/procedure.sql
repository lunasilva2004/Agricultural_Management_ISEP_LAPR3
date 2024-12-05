create or replace NONEDITIONABLE PROCEDURE PRCWATERINGREGISTER(
    p_Data IN VARCHAR2,
    p_Hora_Inicio IN VARCHAR2,
    p_Hora_Fim IN VARCHAR2,
    p_Duracao IN NUMBER,
    p_Sector IN NUMBER,
    p_ErrorMsg OUT VARCHAR2,
    p_Status OUT NUMBER
) AS
    v_IdOperacao NUMBER;
    v_CountOperacao NUMBER;
    v_CountRega NUMBER;
BEGIN
    p_Status := 0; -- Inicialize a variável de status

    -- Verificar se a operação já existe
SELECT COUNT(*)
INTO v_CountOperacao
FROM operacao_agricola oa
         INNER JOIN Rega r ON oa.id_operacaoagricola = r.operacao_agricolaid_operacaoagricola
WHERE oa.tipo_operaçãodesignação = 'Rega'
  AND oa.quantidade = p_Duracao
  AND oa.data = TO_DATE(p_Data, 'YYYY-MM-DD')
  AND oa.unidade = 'min'
  AND r.hora_inicial = p_Hora_Inicio
  AND r.hora_final = p_Hora_Fim;


-- Se não existir, inserir dados na tabela Operação
IF v_CountOperacao = 0 THEN
        INSERT INTO operacao_agricola (tipo_operaçãodesignação, quantidade, data, unidade)
        VALUES ('Rega', p_duracao, TO_DATE(p_data, 'YYYY-MM-DD'), 'min')
        RETURNING id_operacaoagricola INTO v_IdOperacao;

        -- Verificar se a rega já existe
SELECT COUNT(*)
INTO v_CountRega
FROM Rega
WHERE operacao_agricolaid_operacaoagricola = v_IdOperacao
  AND hora_inicial = p_Hora_Inicio
  AND hora_final = p_Hora_Fim;

-- Se não existir, inserir dados na tabela Rega
IF v_CountRega = 0 THEN
            INSERT INTO Rega (operacao_agricolaid_operacaoagricola, hora_inicial, hora_final, SetorID_setor)
            VALUES (v_IdOperacao, p_Hora_Inicio, p_Hora_Fim, p_Sector);

            -- Definir mensagem de sucesso
            p_Status := 1;
            --p_errormsg := 'Insert with sucess';
ELSE
            -- Definir mensagem de erro se já existir na tabela Rega
            --p_ErrorMsg := 'Duplicate records found in Rega table';
            p_Status := 0;
END IF;
ELSE
        -- Definir mensagem de erro se já existir na tabela Operacao_Agricola
        --p_ErrorMsg := 'Duplicate records found in Operacao_Agricola table';
        p_Status := 0;
END IF;

EXCEPTION
    WHEN OTHERS THEN
        -- Em caso de exceção, definir mensagem de erro apropriada
        p_ErrorMsg := SQLERRM;
END PRCWATERINGREGISTER;