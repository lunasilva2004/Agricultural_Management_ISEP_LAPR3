create or replace function fncSailors
return sys_refcursor
as
  l_refcur_sailors sys_refcursor;
begin
    open l_refcur_sailors for
        SELECT
            Planta.Especie,
            Produto.Designação AS Produto,
            Operacao_Agricola.Data AS Data_Colheita,
            Operacao_Agricola.Quantidade AS Quantidade_Colhida,
            Operacao_Agricola.Unidade AS Unidade
        FROM
            Operacao_Agricola_Parcela
        JOIN
            Operacao_Agricola ON Operacao_Agricola_Parcela.Operacao_AgricolaID_OperacaoAgricola = Operacao_Agricola.ID_OperacaoAgricola
        JOIN
            Produto ON Operacao_Agricola.ID_OperacaoAgricola = Produto.Operacao_AgricolaID_OperacaoAgricola
        JOIN
            Planta ON Produto.PlantaID_Planta = Planta.ID_Planta
        WHERE
            Operacao_Agricola_Parcela.ParcelaID_Parcela = 100
            AND Operacao_Agricola.Data BETWEEN TO_DATE('2023-05-20', 'YYYY-MM-DD') AND TO_DATE('2023-11-06', 'YYYY-MM-DD');
    return l_refcur_sailors;
end;
/