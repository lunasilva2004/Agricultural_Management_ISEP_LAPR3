SELECT *
FROM (
    SELECT
        FP.Nome_Comercial AS Nome_Comercial,
        FP.ID_FatorProducao AS Fator_Producao,
        COUNT(O.ID_Operacao) AS Numero_Aplicacoes
    FROM
        Fator_Producao FP
    LEFT JOIN
        Operacao O ON FP.ID_FatorProducao = O.Fator_ProducaoID_FatorProducao
    WHERE
        O.Data >= TO_DATE('2010-01-01', 'YYYY-MM-DD')
        AND O.Data <= TO_DATE('2023-12-31', 'YYYY-MM-DD')
    GROUP BY
        FP.Nome_Comercial, FP.ID_FatorProducao
    ORDER BY
        Numero_Aplicacoes DESC
)
WHERE ROWNUM = 1;