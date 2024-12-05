SELECT *
FROM (
    SELECT p.ID_Parcela, p.Designacao, COUNT(*) AS Num_Operacoes_Rega
    FROM Operacao o
    JOIN Parcela p ON o.ParcelaID_Parcela = p.ID_Parcela
    WHERE o.Data >= TO_DATE('2010-01-01', 'YYYY-MM-DD')  AND o.Data <= TO_DATE('2023-12-31', 'YYYY-MM-DD')
    AND o.Tipo_Operacao = 'Rega'
    GROUP BY p.ID_Parcela, p.Designacao
    ORDER BY Num_Operacoes_Rega DESC
)
WHERE ROWNUM = 1;