SELECT
    p.Designacao AS Parcela,
    f.Nome_Comercial AS Fator_Producao,
    s.ComponenteQuímico AS Substancia_Componente,
    o.Quantidade AS Quantidade,
    o.Data AS Data_Operacao
FROM Operacao_Agricola o
         JOIN Operacao_Agricola_Fator_Producao ofp ON o.ID_OperacaoAgricola = ofp.Operacao_AgricolaID_OperacaoAgricola
         JOIN Fator_Producao f ON ofp.Fator_ProducaoID_FatorProducao = f.ID_FatorProducao
         JOIN Ficha_Tecnica ft ON f.ID_FatorProducao = ft.Fator_ProducaoID_FatorProducao
         JOIN Substância s ON ft.ID_FichaTecnica = s.Ficha_TecnicaID_FichaTecnica
         JOIN Operacao_Agricola_Parcela op ON o.ID_OperacaoAgricola = op.Operacao_AgricolaID_OperacaoAgricola
         JOIN Parcela p ON op.ParcelaID_Parcela = p.ID_Parcela
WHERE p.Designacao = 'Lameiro do Moinho'
  AND o.Data BETWEEN TO_DATE('2019-01-01', 'YYYY-MM-DD') AND TO_DATE('2023-07-06', 'YYYY-MM-DD');
