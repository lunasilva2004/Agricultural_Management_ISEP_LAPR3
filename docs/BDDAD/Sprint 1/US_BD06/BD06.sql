-- Antes de correr o script, por favor substitua as variáveis 'NOME_PARCELA', DATA_INICIAL e DATA_FINAL.

SELECT
    p.ID_Parcela AS ID_Parcela,
    p.Designacao AS Nome_Parcela,
    f.Tipo_Formulacao AS Tipo_Fator_Producao,
    COUNT(o.ID_Operacao) AS Numero_Fatores_Aplicados
FROM
    Parcela p
        JOIN
    Operacao o ON p.ID_Parcela = o.ParcelaID_Parcela
         JOIN
    Fator_Producao f ON o.Fator_ProducaoID_FatorProducao = f.ID_FatorProducao
WHERE
    f.Tipo_Formulacao = 'NOME_PARCELA'
    AND o.Data >= TO_DATE('DATA_INICIAL', 'YYYY-MM-DD')
    AND o.Data <= TO_DATE('DATA_FINAL', 'YYYY-MM-DD')
    AND f.Tipo_Formulacao IS NOT NULL
GROUP BY
    p.ID_Parcela, p.Designacao, f.Tipo_Formulacao
ORDER BY
    ID_Parcela, Tipo_Fator_Producao;