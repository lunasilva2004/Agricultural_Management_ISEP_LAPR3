create or replace NONEDITIONABLE PROCEDURE prcRegistrarOperacaoPoda(

    pTipo_Operacao IN VARCHAR2,

    pQuantidade IN NUMBER,

    pData IN DATE,

    pUnidade IN VARCHAR2,

    pDesignacao IN VARCHAR2,

    pNome_Cultura IN VARCHAR2,

    pNome_Comum IN VARCHAR2,

    pVariedade IN VARCHAR2,

    p_Result OUT NUMBER,

    p_ErrorMsg OUT VARCHAR2

)

AS

    v_OperacaoID NUMBER;

    v_ParcelaCount NUMBER;

    v_CulturaCount NUMBER;

    v_PlantaCount NUMBER;

    v_IDPlanta NUMBER;

    v_CulturaID NUMBER;

BEGIN



    BEGIN

        SELECT COUNT(*) INTO v_CulturaCount FROM Cultura WHERE NOMECULTURA = pNome_Cultura;



        IF v_CulturaCount = 0 THEN

            p_Result := 0; -- int status

            p_ErrorMsg := 'A designação de Cultura fornecido não existe.';

            RETURN;

        END IF;

    EXCEPTION

        WHEN NO_DATA_FOUND THEN

            p_Result := 0; -- int status - Cultura não encontrada

            p_ErrorMsg := 'A designação de Cultura fornecido não existe.';

            RETURN;

    END;





    BEGIN

        SELECT COUNT(*) INTO v_PlantaCount FROM Planta WHERE NOME_COMUM = pNome_Comum AND VARIEDADE = pVariedade ;



        IF v_PlantaCount = 0 THEN

            p_Result := 0; -- status

            p_ErrorMsg := 'A designação de Planta fornecido não existe.';

            RETURN;

        END IF;

    EXCEPTION

        WHEN NO_DATA_FOUND THEN

            p_Result := 0; -- int status

            p_ErrorMsg := 'A designação de Planta fornecido não existe.';

            RETURN;

    END;



       -- Verificar se a ParcelaID já existe

    BEGIN

        SELECT COUNT(*) INTO v_ParcelaCount FROM Parcela WHERE Designacao = pDesignacao;



        IF v_ParcelaCount = 0 THEN

            p_Result := 0; -- int status

            p_ErrorMsg := 'A designação da parcela fornecido não existe.';

            RETURN;

        END IF;

    EXCEPTION

        WHEN NO_DATA_FOUND THEN

            p_Result := 0; -- int status - Cultura não encontrada

            p_ErrorMsg := 'A designação da parcela fornecido não existe.';

            RETURN;

    END;







    INSERT INTO Operacao_Agricola(Tipo_OperaçãoDesignação, Quantidade, Data, Unidade)

    VALUES (pTipo_Operacao, pQuantidade, pData, pUnidade)

    RETURNING ID_OperacaoAgricola INTO v_OperacaoID;



    INSERT INTO Operacao_Agricola_Parcela(Operacao_AgricolaID_OperacaoAgricola, ParcelaID_Parcela)

    VALUES (v_OperacaoID, (SELECT ID_Parcela FROM Parcela WHERE Designacao = pDesignacao));



    SELECT ID_PLANTA INTO v_IDPlanta FROM Planta WHERE NOME_COMUM = pNome_Comum AND VARIEDADE = pVariedade;

    SELECT ID_CULTURA INTO v_CulturaID FROM Cultura WHERE NOMECULTURA = pNome_Cultura;



    INSERT INTO Operacao_Agricola_Cultura(Operacao_AgricolaID_OperacaoAgricola, CulturaID_Cultura,CulturaPlantaID_Planta,CulturaParcelaID_Parcela)

    VALUES (v_OperacaoID,v_CulturaID,v_IDPlanta,(SELECT ID_Parcela FROM Parcela WHERE Designacao = pDesignacao));





END prcRegistrarOperacaoPoda;