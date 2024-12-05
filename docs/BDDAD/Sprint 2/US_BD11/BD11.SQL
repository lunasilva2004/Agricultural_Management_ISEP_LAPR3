create or replace NONEDITIONABLE PROCEDURE PRCREGISTAROPERACAOSEMEADURA(
    --p_ID_OperacaoAgricola IN NUMBER,
    p_TipoOperacaoDesignacao IN VARCHAR2,
    p_Quantidade IN NUMBER,
    p_Area IN NUMBER,
    p_Data IN VARCHAR2,
    p_Unidade IN VARCHAR2,
    p_CulturaID IN NUMBER,
    p_PlantaID IN NUMBER,
    p_ParcelaID IN NUMBER,
    p_Result OUT NUMBER,
    p_ErrorMsg OUT VARCHAR2
) AS
    v_OperacaoID NUMBER;
    v_CulturaCount NUMBER;
    v_PlantaCount NUMBER;
    v_ParcelaCount NUMBER;

BEGIN
    -- Inserir dados na tabela Operacao_Agricola
INSERT INTO Operacao_Agricola(Tipo_OperaçãoDesignação, Quantidade, Data, Unidade)
VALUES (p_TipoOperacaoDesignacao, p_Quantidade, p_Data, p_Unidade)
    RETURNING ID_OperacaoAgricola INTO v_OperacaoID;

-- Verificar se a culturaID já existe
BEGIN
SELECT COUNT(*) INTO v_CulturaCount FROM Cultura WHERE ID_Cultura = p_CulturaID;

IF v_CulturaCount = 0 THEN
            p_Result := 0; -- int status
            p_ErrorMsg := 'O ID de Cultura fornecido não existe.';
            RETURN;
END IF;
EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_Result := 0; -- int status - Cultura não encontrada
            p_ErrorMsg := 'O ID de Cultura fornecido não existe.';
            RETURN;
END;

    -- Verificar se a PlantaID já existe
BEGIN
SELECT COUNT(*) INTO v_PlantaCount FROM Planta WHERE ID_Planta = p_PlantaID;

IF v_PlantaCount = 0 THEN
            p_Result := 0; -- status
            p_ErrorMsg := 'O ID de Planta fornecido não existe.';
            RETURN;
END IF;
EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_Result := 0; -- int status
            p_ErrorMsg := 'O ID de Planta fornecido não existe.';
            RETURN;
END;

    -- Verificar se a ParcelaID já existe
BEGIN
SELECT COUNT(*) INTO v_ParcelaCount FROM Parcela WHERE ID_Parcela = p_ParcelaID;

IF v_ParcelaCount = 0 THEN
            p_Result := 0; -- int status
            p_ErrorMsg := 'O ID de Parcela fornecido não existe.';
            RETURN;
END IF;
EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_Result := 0; -- int status
            p_ErrorMsg := 'O ID de Parcela fornecido não existe.';
            RETURN;
END;

    -- Inserir dados na tabela Cultura
INSERT INTO Cultura(ID_Cultura, PlantaID_Planta, ParcelaID_Parcela, NomeCultura, Data_Inicial, Data_Final, Quantidade, Unidade)
VALUES (
           v_OperacaoID,
           p_PlantaID,
           p_ParcelaID,
           (SELECT Nome_Comum FROM Planta WHERE ID_Planta = p_PlantaID),
           p_Data,
           NULL,
           p_Quantidade,
           p_Unidade
       );


-- int status 1 (operação bem-sucedida)
p_Result := 1;
    p_ErrorMsg := NULL; -- Não há erros


    --COMMIT;
END PRCREGISTAROPERACAOSEMEADURA;
