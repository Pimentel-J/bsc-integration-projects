package eapli.shopfloor.infrastructure.bootstrapers;

import eapli.framework.time.util.Calendars;
import eapli.shopfloor.gestaoproducao.domain.FichaTecnicaMaterial;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;

public final class TestDataConstants {

    // Categoria Material
    public static final String CATEGORIA_CODIGO_MADEIRA = "md";
    public static final String CATEGORIA_DESCRICAO_MADEIRA = "madeira";
    public static final String CATEGORIA_CODIGO_PEDRA = "pd";
    public static final String CATEGORIA_DESCRICAO_PEDRA = "pedra";
    public static final String CATEGORIA_CODIGO_COBRE = "cb";
    public static final String CATEGORIA_DESCRICAO_COBRE = "cobre";

    // Material
    public static final String MAT_CODIGO_GRANITO = "grp";
    public static final String MAT_DESCRICAO_GRANITO = "granito preto";
    public static final FichaTecnicaMaterial FICHA_GRANITO = FichaTecnicaMaterial.valueOf("fgranito", "/home/fgfile");
    public static final String MAT_CODIGO_CARVALHO = "crpt";
    public static final String MAT_DESCRICAO_CARVALHO = "carvalho portugues";
    public static final FichaTecnicaMaterial FICHA_CARVALHO = FichaTecnicaMaterial.valueOf("fcarvalho", "/home/fcfile");
    public static final String MAT_CODIGO_COBRE = "tcb";
    public static final String MAT_DESCRICAO_COBRE = "tubo cobre";
    public static final FichaTecnicaMaterial FICHA_COBRE = FichaTecnicaMaterial.valueOf("tcobre", "/home/tcfile");

    // Linha Produção
    public static final String ID_LINHA_PRODUCAO_LP1 = "LP1";
    public static final String ID_LINHA_PRODUCAO_LP2 = "LP2";
    public static final String ID_LINHA_PRODUCAO_LP3 = "LP3";

    //Deposito
    public static final String ID_DEPOSITO_DP1 = "DP001";
    public static final String DESCRICAO_DEPOSITO_DP1 = "depósito de tábuas";
    public static final String ID_DEPOSITO_DP2 = "DP002";
    public static final String DESCRICAO_DEPOSITO_DP2 = "depósito de granito";
    public static final String ID_DEPOSITO_DP3 = "DP003";
    public static final String DESCRICAO_DEPOSITO_DP3 = "depósito de tubos";
    public static final String ID_DEPOSITO_L030 = "L030";
    public static final String DESCRICAO_DEPOSITO_L030 = "depósito de madeira";
    public static final String ID_DEPOSITO_L040 = "L040";
    public static final String DESCRICAO_DEPOSITO_L040 = "depósito de pedra";
    public static final String ID_DEPOSITO_L042 = "L042";
    public static final String DESCRICAO_DEPOSITO_L042 = "depósito de cobre";
    

    //Produto
    public static final String CODIGO_FABRICO_PRD1 = "77000651";
    public static final String CODIGO_COMERCIAL_PRD1 = "PRD1-COM";
    public static final String DESCRICAO_BREVE_PRD1 = "Cadeira";
    public static final String DESCRICAO_COMPLETA_PRD1 = "Cadeira com 4 pernas";
    public static final String UNIDADE_PRD1 = "UN";
    public static final String CATEGORIA_PRD1 = "CAT1";
    public static final String DESCRICAO_CATEGORIA_PRD1 = "Mobiliário";

    public static final String CODIGO_FABRICO_PRD2 = "65000106";
    public static final String CODIGO_COMERCIAL_PRD2 = "PRD2-COM";
    public static final String DESCRICAO_BREVE_PRD2 = "Cimento";
    public static final String DESCRICAO_COMPLETA_PRD2 = "Cimento  construção";
    public static final String UNIDADE_PRD2 = "KG";
    public static final String CATEGORIA_PRD2 = "CAT2";
    public static final String DESCRICAO_CATEGORIA_PRD2 = "Construção";

    public static final String CODIGO_FABRICO_PRD3 = "41000651";
    public static final String CODIGO_COMERCIAL_PRD3 = "PRD3-COM";
    public static final String DESCRICAO_BREVE_PRD3 = "Armário";
    public static final String DESCRICAO_COMPLETA_PRD3 = "Armário ergonómico com arrumação";
    public static final String UNIDADE_PRD3 = "UN";
    public static final String CATEGORIA_PRD3 = "CAT1";
    public static final String DESCRICAO_CATEGORIA_PRD3 = "Mobiliário";

    public static final String CODIGO_FABRICO_PRD4 = "50000106";
    public static final String CODIGO_COMERCIAL_PRD4 = "PRD3-COM";
    public static final String DESCRICAO_BREVE_PRD4 = "Aspirador";
    public static final String DESCRICAO_COMPLETA_PRD4 = "Poder de sucção fenomenal";
    public static final String UNIDADE_PRD4 = "UN";
    public static final String CATEGORIA_PRD4 = "CAT3";
    public static final String DESCRICAO_CATEGORIA_PRD4 = "Eletrodomésticos";

    public static final String CODIGO_FABRICO_PRD5 = "32000142";
    public static final String CODIGO_COMERCIAL_PRD5 = "PRD5-COM";
    public static final String DESCRICAO_BREVE_PRD5 = "Parafuso";
    public static final String DESCRICAO_COMPLETA_PRD5 = "Parafuso calibre 5";
    public static final String UNIDADE_PRD5 = "UN";
    public static final String CATEGORIA_PRD5 = "CAT2";
    public static final String DESCRICAO_CATEGORIA_PRD5 = "Construção";
    
    public static final String CODIGO_FABRICO_PRD6 = "32000150";
    public static final String CODIGO_COMERCIAL_PRD6 = "PRD5-COM";
    public static final String DESCRICAO_BREVE_PRD6 = "Bucha";
    public static final String DESCRICAO_COMPLETA_PRD6 = "Bucha calibre 5";
    public static final String UNIDADE_PRD6 = "UN";
    public static final String CATEGORIA_PRD6 = "CAT2";
    public static final String DESCRICAO_CATEGORIA_PRD6 = "Construção";

    // Ordens de Produção
    public static final String CODIGO_FABRICO_PRD_1 = "50000106";
    public static final String ID_ORDEM_PRD_1 = "100003363";
    public static final Calendar DATA_EMISSAO_1 = Calendars.of(2018, 12, 29);
    public static final Calendar DATA_PREV_EXEC_1 = Calendars.of(2018, 12, 30);
    public static final int QTD_ORDEM_PRD_1 = 65000;
    public static final String ID_ENCOMENDA_1 = "ENC1";
    
    public static final String CODIGO_FABRICO_PRD_2 = "32000150";
    public static final String ID_ORDEM_PRD_2 = "100003470";
    public static final Calendar DATA_EMISSAO_2 = Calendars.of(2020, 02, 20);
    public static final Calendar DATA_PREV_EXEC_2 = Calendars.of(2020, 02, 26);
    public static final int QTD_ORDEM_PRD_2 = 25000;
    public static final String ID_ENCOMENDA_2 = "ENC2";
   

    // Máquinas
    public static final String ID_MAQUINA_M1 = "T3";
    public static final String ID_MAQUINA_M2 = "M2";
    public static final String ID_MAQUINA_M3 = "DD4";
    public static final String ID_MAQUINA_M4 = "T1";
    public static final String ID_MAQUINA_M5 = "T2";
    public static final String ID_MAQUINA_M6 = "DD3";
    public static final String NUM_SERIE_M1 = "10101";
    public static final String NUM_SERIE_M2 = "30303";
    public static final String NUM_SERIE_M3 = "20202";
    public static final String NUM_SERIE_M4 = "40404";
    public static final String NUM_SERIE_M5 = "50505";
    public static final String NUM_SERIE_M6 = "60606";
    public static final String DESCRICAO_MAQUINA_M1 = "Prensa alisamento propulsores";
    public static final String DESCRICAO_MAQUINA_M2 = "Torno polimento metais";
    public static final String DESCRICAO_MAQUINA_M3 = "Soldadura a laser e corte";
    public static final String DESCRICAO_MAQUINA_M4 = "Quinadora de aço";
    public static final String DESCRICAO_MAQUINA_M5 = "Forno de Vidro";
    public static final String DESCRICAO_MAQUINA_M6 = "Transportadora de correia";
    public static final String DATA_INSTALACAO_M1 = "10-05-2020";
    public static final String DATA_INSTALACAO_M2 = "11-05-2020";
    public static final String DATA_INSTALACAO_M3 = "12-05-2020";
    public static final String DATA_INSTALACAO_M4 = "13-05-2020";
    public static final String DATA_INSTALACAO_M5 = "14-05-2020";
    public static final String DATA_INSTALACAO_M6 = "15-05-2020";
    public static final String MARCA_M1 = "AGME";
    public static final String MARCA_M2 = "Rieckermann";
    public static final String MARCA_M3 = "AGME";
    public static final String MARCA_M4 = "Aires da Costa";
    public static final String MARCA_M5 = "Juncker";
    public static final String MARCA_M6 = "Kraftwerk";
    public static final String MODELO_M1 = "A101";
    public static final String MODELO_M2 = "A303";
    public static final String MODELO_M3 = "R202";
    public static final String MODELO_M4 = "R404";
    public static final String MODELO_M5 = "R505";
    public static final String MODELO_M6 = "R606";
    public static final Integer SEQUENCIA_M1 = 1;
    public static final Integer SEQUENCIA_M2 = 2;
    public static final Integer SEQUENCIA_M3 = 3;
    public static final Integer SEQUENCIA_M4 = 1;
    public static final Integer SEQUENCIA_M5 = 2;
    public static final Integer SEQUENCIA_M6 = 3;
    public static final Integer ID_PROTOCOLO_M1 = 101;
    public static final Integer ID_PROTOCOLO_M2 = 303;
    public static final Integer ID_PROTOCOLO_M3 = 202;
    public static final Integer ID_PROTOCOLO_M4 = 404;
    public static final Integer ID_PROTOCOLO_M5 = 505;
    public static final Integer ID_PROTOCOLO_M6 = 606;

    public static final String USER_TEST1 = "user1";

    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD1 = "Password1";

    @SuppressWarnings("squid:S2885")
    public static final Calendar DATE_TO_BOOK = Calendars.of(2017, 12, 01);

    private TestDataConstants() {
        // ensure utility
    }
}
