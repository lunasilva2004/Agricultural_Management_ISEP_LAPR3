--FUNCTION
CREATE OR REPLACE FUNCTION obter_dados_rega(
    p_data_inicio DATE,
    p_data_fim DATE
) RETURN SYS_REFCURSOR IS
    resultado_cursor SYS_REFCURSOR;
BEGIN
    -- Caso de Sucesso
OPEN resultado_cursor FOR
SELECT
    Parcela.ID_Parcela,
    EXTRACT(MONTH FROM Rega.Data) AS Mes,
    EXTRACT(YEAR FROM Rega.Data) AS Ano,
    SUM(Rega.Quantidade) AS Total_Rega
FROM
    Parcela
        JOIN Parcela_Setor ON Parcela.ID_Parcela = Parcela_Setor.ParcelaID_Parcela
        JOIN Setor ON Parcela_Setor.SetorID_Setor = Setor.ID_Setor
        JOIN Rega ON Setor.RegaOperacao_AgricolaID_OperacaoAgricola = Rega.Operacao_AgricolaID_OperacaoAgricola
WHERE
    Rega.Data BETWEEN p_data_inicio AND p_data_fim
GROUP BY
    Parcela.ID_Parcela,
    EXTRACT(MONTH FROM Rega.Data),
    EXTRACT(YEAR FROM Rega.Data)
ORDER BY
    Parcela.ID_Parcela,
    EXTRACT(YEAR FROM Rega.Data),
    EXTRACT(MONTH FROM Rega.Data);

-- Verifica se o cursor tem dados
IF resultado_cursor%NOTFOUND THEN
        -- Caso de Insucesso
        CLOSE resultado_cursor;
OPEN resultado_cursor FOR
SELECT 'Nenhum dado encontrado' AS mensagem FROM DUAL;
END IF;

RETURN resultado_cursor;
END obter_dados_rega;
/

--CALL FUNCTION
DECLARE
resultado_cursor SYS_REFCURSOR;
BEGIN
    resultado_cursor := obter_dados_rega(TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2023-02-31', 'YYYY-MM-DD'));

CLOSE resultado_cursor;
END;
/

