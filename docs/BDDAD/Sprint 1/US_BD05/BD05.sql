-- Antes de correr o scrip, por favor substitua as variáveis NOME_PARCELA, DATA_INICIAL e DATA_FINAL.

SELECT
    Cultura.NomeCultura AS Produto,
    SUM(Operacao.Quantidade) AS Quantidade_Colhida,
    Operacao.Unidade AS Unidade
FROM
    Cultura
        JOIN Parcela ON Cultura.ParcelaID_Parcela = Parcela.ID_Parcela
        JOIN Operacao ON Cultura.ID_Cultura = Operacao.CulturaID_Cultura
WHERE
        Parcela.Designacao = 'NOME_PARCELA'
  AND Operacao.Data >= TO_DATE('DATA_INICIAL', 'YYYY-MM-DD')
  AND Operacao.Data <= TO_DATE('DATA_FINAL', 'YYYY-MM-DD')
  AND Operacao.Tipo_Operacao = 'Colheita'
GROUP BY
    Cultura.NomeCultura, Operacao.Unidade;