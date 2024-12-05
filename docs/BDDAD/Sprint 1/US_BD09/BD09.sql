-- Antes de correr o script, por favor substitua as variáveis DATA_INICIAL e DATA_INICIAL.

SELECT
    f.Tipo_Formulacao AS Tipo_Fator_Producao,
    COUNT(o.Fator_ProducaoID_FatorProducao) AS Numero_Aplicacoes
FROM
    Fator_Producao f
        LEFT JOIN
    Operacao o ON f.ID_FatorProducao = o.Fator_ProducaoID_FatorProducao
WHERE
    o.Data BETWEEN TO_DATE('DATA_INICIAL', 'YYYY-MM-DD') AND TO_DATE('DATA_INICIAL', 'YYYY-MM-DD')
GROUP BY
    f.Tipo_Formulacao
ORDER BY
    Tipo_Fator_Producao;