CREATE OR REPLACE FUNCTION listaOperacao
RETURN SYS_REFCURSOR
AS
  lista_operacaoParcela SYS_REFCURSOR;
BEGIN
OPEN lista_operacaoParcela FOR
SELECT
    TO_CHAR(Data, 'DD/MM/YYYY') AS DataOperacao,
    Tipo_OperacaoDesignacao AS TipoOperacao,
    Quantidade,
    Unidade
FROM
    Operacao_Agricola_Parcela
        JOIN Operacao_Agricola ON Operacao_Agricola_Parcela.Operacao_AgricolaID_OperacaoAgricola = Operacao_Agricola.ID_OperacaoAgricola
        JOIN Parcela ON Operacao_Agricola_Parcela.ParcelaID_Parcela = Parcela.ID_Parcela
WHERE
        Parcela.Designacao = 'Campo Novo' AND
    Data BETWEEN TO_DATE('01/07/2023', 'DD/MM/YYYY')
        AND TO_DATE('31/12/2023', 'DD/MM/YYYY')
ORDER BY
    Tipo_OperacaoDesignacao;

RETURN lista_operacaoParcela;
END;
/


-------BLOCO__ANÓNIMO--------------

DECLARE
lista_operacaoParcela      SYS_REFCURSOR;

    DataOperacao              VARCHAR2(20);
    TipoOperacao              VARCHAR2(100);
    Quantidade                NUMBER;
    Unidade                   VARCHAR2(50);
    operacoesInexistente      EXCEPTION;
BEGIN
    lista_operacaoParcela := listaOperacao;

FETCH lista_operacaoParcela INTO DataOperacao, TipoOperacao, Quantidade, Unidade;

IF lista_operacaoParcela%ROWCOUNT = 0 THEN
        RAISE operacoesInexistente;
ELSE
        DBMS_OUTPUT.PUT_LINE( RPAD('Tipo de Operacao ', 30, ' ') || '|  ' || RPAD('Quantidade ', 20, ' ') || '|  ' || RPAD('Unidade ', 20, ' ') || '|  ' || RPAD('Data da Operacao ', 30, ' '));

        LOOP
EXIT WHEN lista_operacaoParcela%NOTFOUND;
            IF(Quantidade < 1) THEN
                DBMS_OUTPUT.PUT_LINE(RPAD(TipoOperacao, 30, ' ') || '|  ' || RPAD(TO_CHAR(Quantidade, 'FM99999990.9999'), 20, ' ') || '|  ' || RPAD(Unidade, 20, ' ') || '|  ' || RPAD(DataOperacao, 20, ' '));
ELSE
                DBMS_OUTPUT.PUT_LINE(RPAD(TipoOperacao, 30, ' ') || '|  ' || RPAD(Quantidade, 20, ' ') || '|  ' || RPAD(Unidade, 20, ' ')|| '|  ' || RPAD(DataOperacao, 20, ' '));
END IF;

FETCH lista_operacaoParcela INTO DataOperacao, TipoOperacao, Quantidade, Unidade;
END LOOP;
END IF;

CLOSE lista_operacaoParcela;
EXCEPTION
    WHEN operacoesInexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Não existem operações no período indicado.');
END;
/