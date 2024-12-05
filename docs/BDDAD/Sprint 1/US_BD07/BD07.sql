SELECT
    operacao.Tipo_Operacao,
    COUNT(operacao.ID_Operacao) AS Numero_De_Operacoes
FROM
    Operacao operacao
WHERE
        operacao.ParcelaID_Parcela =  AND
        operacao.Data >= TO_DATE('2012-01-08', 'YYYY-MM-DD') AND
        operacao.Data <= TO_DATE('2023-07-10', 'YYYY-MM-DD')
GROUP BY
    operacao.Tipo_Operacao;