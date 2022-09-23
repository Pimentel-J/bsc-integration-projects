/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.shopfloor.app.backoffice.console.presentation;

import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import eapli.shopfloor.app.backoffice.console.presentation.authz.AddUserUI;
import eapli.shopfloor.app.backoffice.console.presentation.authz.DeactivateUserAction;
import eapli.shopfloor.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.shopfloor.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import eapli.shopfloor.app.backoffice.console.presentation.depositos.RegistarDepositoAction;
import eapli.shopfloor.app.backoffice.console.presentation.exportacao.ExportarFicheiroReportAction;
import eapli.shopfloor.app.backoffice.console.presentation.maquinas.ImportarConfigMaquinaAction;
import eapli.shopfloor.app.backoffice.console.presentation.maquinas.RegistarLinhaProducaoAction;
import eapli.shopfloor.app.backoffice.console.presentation.maquinas.RegistarMaquinaAction;
import eapli.shopfloor.app.backoffice.console.presentation.materiais.RegistarCategoriaMaterialAction;
import eapli.shopfloor.app.backoffice.console.presentation.materiais.RegistarMaterialAction;
import eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.ArquivarNotificacoesErroProcessAction;
import eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting.NotificacoesErrosProcessamentoArquivadasAction;
import eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting.NotificacoesErrosProcessamentoAtivasAction;
import eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.IntroduzirOrdemProducaoAction;
import eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.reporting.ReportOrdensProducaoEncomendaAction;
import eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.reporting.ReportOrdensProducaoEstadoAction;
import eapli.shopfloor.app.backoffice.console.presentation.produtos.*;
import eapli.shopfloor.app.backoffice.console.presentation.produtos.reporting.ReportProdutosSemFichaAction;
import eapli.shopfloor.app.common.console.presentation.authz.MyUserMenu;
import eapli.shopfloor.core.Application;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 * TODO split this class in more specialized classes for each menu
 *
 *
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

    // SETTINGS
    private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

    /* SUBMENUS */
    // DEPOSITOS
    private static final int REGISTAR_DEPOSITO_OPTION = 1;

    // FICHAS DE PRODUCAO
    private static final int REGISTAR_FICHA_PRODUCAO_OPTION = 1;

    // LINHAS DE PRODUCAO
    private static final int REGISTAR_LINHAS_PRODUCAO_OPTION = 1;

    // MAQUINAS
    private static final int REGISTAR_MAQUINA_OPTION = 1;
    private static final int IMPORTAR_FICHEIRO_CONFIG_MAQUINA_OPTION = 2;

    // PRODUTOS
    private static final int ADICIONAR_PRODUTO_OPTION = 1;
    private static final int IMPORTAR_CATALOGO_OPTION = 2;

    // ORDEM PRODUCAO
    private static final int INSERIR_ORDENS_PRODUCAO_OPTION = 1;

    // MATERIAIS
    private static final int REGISTAR_CATEGORIA_MATERIAL_OPTION = 1;
    private static final int REGISTAR_MATERIAL_OPTION = 2;

    // CONSULTAS
    private static final int CONSULTAR_PRODUTOS_SEM_FP_OPTION = 1;
    private static final int CONSULTAR_ORDENS_PRODUCAO_ESTADO_OPTION = 2;
    private static final int CONSULTAR_ORDENS_PRODUCAO_ENCOMENDA_OPTION = 3;

    // EXPORTAÇÂO
    private static final int EXPORTAR_XML_OPTION = 1;

    // NOTIFICAÇÕES ERROS PROCESSAMENTO
    private static final int ARQUIVAR_NOTIFICACOES_ERRO_ATIVAS_OPTION = 1;
    private static final int CONSULTAR_NOTIFICACOES_ERRO_ATIVAS_OPTION = 2;
    private static final int CONSULTAR_NOTIFICACOES_ERRO_ARQUIVADAS_OPTION = 3;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int SETTINGS_OPTION = 3;
    // MAIN MENU - GESTAO CHAO FABRICA
    private static final int MAQUINAS_OPTION = 2;
    private static final int DEPOSITOS_OPTION = 3;
    private static final int LINHAS_PRODUCAO_OPTION = 4;
    private static final int NOTIFICACOES_ERRO_PROCESS_OPTION = 5;
    // MAIN MENU - GESTAO PRODUCAO
    private static final int CONSULTAS_OPTION = 2;
    private static final int MATERIAIS_OPTION = 3;
    private static final int FICHAS_PRODUCAO_OPTION = 4;
    private static final int PRODUTOS_OPTION = 5;
    private static final int ORDENS_PRODUCAO_OPTION = 6;
    private static final int EXPORTAR_INFO_OPTION = 7;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Shop Floor [ @" + s.authenticatedUser().identity() +
                " ]").orElse("Shop Floor [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA)) {
            final Menu maquinasMenu = buildMaquinasMenu();
            mainMenu.addSubMenu(MAQUINAS_OPTION, maquinasMenu);
            final Menu depositosMenu = buildDepositosMenu();
            mainMenu.addSubMenu(DEPOSITOS_OPTION, depositosMenu);
            final Menu linhaProducaoMenu = buildLinhasProducaoMenu();
            mainMenu.addSubMenu(LINHAS_PRODUCAO_OPTION, linhaProducaoMenu);
            final Menu notificacoesErrosProcessamentoMenu = buildNotificacoesErrosProcessamentoMenu();
            mainMenu.addSubMenu(NOTIFICACOES_ERRO_PROCESS_OPTION, notificacoesErrosProcessamentoMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.GESTOR_PRODUCAO)) {
            final Menu consultasMenu = buildConsultasMenu();
            mainMenu.addSubMenu(CONSULTAS_OPTION, consultasMenu);
            final Menu materiaisMenu = buildMateriaisMenu();
            mainMenu.addSubMenu(MATERIAIS_OPTION, materiaisMenu);
            final Menu fichasProducaoMenu = buildFichasProducaoMenu();
            mainMenu.addSubMenu(FICHAS_PRODUCAO_OPTION, fichasProducaoMenu);
            final Menu produtosMenu = buildProdutosMenu();
            mainMenu.addSubMenu(PRODUTOS_OPTION, produtosMenu);
            final Menu ordensProducaoMenu = buildOrdensProducaoMenu();
            mainMenu.addSubMenu(ORDENS_PRODUCAO_OPTION, ordensProducaoMenu);
            final Menu exportarXmlMenu = buildExportarXmlMenu();
            mainMenu.addSubMenu(EXPORTAR_INFO_OPTION, exportarXmlMenu);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Farewell!"));

        return mainMenu;
    }


    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");

        menu.addItem(SET_KITCHEN_ALERT_LIMIT_OPTION, "Set kitchen alert limit", new ShowMessageAction(
                "Not " + "implemented yet"));
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;

    }

    private Menu buildMaquinasMenu() {
        final Menu menu = new Menu("Máquinas >");

        menu.addItem(REGISTAR_MAQUINA_OPTION, "Registar nova Máquina", new RegistarMaquinaAction());
        menu.addItem(IMPORTAR_FICHEIRO_CONFIG_MAQUINA_OPTION, "Novo Ficheiro Config. Máquina",
                new ImportarConfigMaquinaAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildLinhasProducaoMenu() {
        final Menu menu = new Menu("Linhas de Produção >");

        menu.addItem(REGISTAR_LINHAS_PRODUCAO_OPTION,
                "Registar nova Linha de Produção", new RegistarLinhaProducaoAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildDepositosMenu() {
        final Menu menu = new Menu("Depositos >");

        menu.addItem(REGISTAR_DEPOSITO_OPTION, "Registar novo Depósito", new RegistarDepositoAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildFichasProducaoMenu() {
        final Menu menu = new Menu("Fichas Producao >");

        menu.addItem(REGISTAR_FICHA_PRODUCAO_OPTION, "Registar Ficha de Produção", new RegistarFichaProducaoAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    // GESTAO PRODUCAO - NOVA CATEGORIA MATERIAL
    private Menu buildMateriaisMenu() {
        final Menu menu = new Menu("Materiais >");

        menu.addItem(REGISTAR_CATEGORIA_MATERIAL_OPTION, "Definir Nova Categoria Material",
                new RegistarCategoriaMaterialAction());
        menu.addItem(REGISTAR_MATERIAL_OPTION, "Adicionar Novo Material", new RegistarMaterialAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    // GESTAO CHAO FABRICA - CONSULTAS
    private Menu buildConsultasMenu() {
        final Menu menu = new Menu("Consultas >");

        menu.addItem(CONSULTAR_PRODUTOS_SEM_FP_OPTION, "Consultar Produtos Sem Ficha Produção",
                new ReportProdutosSemFichaAction());
        menu.addItem(CONSULTAR_ORDENS_PRODUCAO_ESTADO_OPTION, "Consultar Ordens Produção Por Estado",
                new ReportOrdensProducaoEstadoAction());
        menu.addItem(CONSULTAR_ORDENS_PRODUCAO_ENCOMENDA_OPTION, "Consultar Ordens Produção Por Encomenda",
                new ReportOrdensProducaoEncomendaAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }


    private Menu buildProdutosMenu() {
        final Menu menu = new Menu("Produtos >");

        menu.addItem(ADICIONAR_PRODUTO_OPTION, "Adicionar Novo Produto", new AdicionarProdutoAction());
        menu.addItem(IMPORTAR_CATALOGO_OPTION, "Importar Catálogo de Produtos", new ImportarProdutosAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildOrdensProducaoMenu() {
        final Menu menu = new Menu("Ordens de Produção >");

        menu.addItem(INSERIR_ORDENS_PRODUCAO_OPTION, "Inserir Nova Ordem de Produção",
                new IntroduzirOrdemProducaoAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    // GESTÃO PRODUÇÃO - EXPORTAÇÃO
    private Menu buildExportarXmlMenu() {
        final Menu menu = new Menu("Exportar Report do Chão de Fábrica >");

        menu.addItem(EXPORTAR_XML_OPTION, "Exportar para Ficheiro", new ExportarFicheiroReportAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    // GESTAO CHAO FABRICA - NOTIFICAÇÕES ERROS PROCESSAMENTO
    private Menu buildNotificacoesErrosProcessamentoMenu() {
        final Menu menu = new Menu("Notificações de Erros de Processamento >");

        menu.addItem(ARQUIVAR_NOTIFICACOES_ERRO_ATIVAS_OPTION, "Arquivar Notificações Ativas", new ArquivarNotificacoesErroProcessAction());
        menu.addItem(CONSULTAR_NOTIFICACOES_ERRO_ATIVAS_OPTION, "Consultar Notificações Ativas", new NotificacoesErrosProcessamentoAtivasAction());
        menu.addItem(CONSULTAR_NOTIFICACOES_ERRO_ARQUIVADAS_OPTION, "Consultar Notificações Arquivadas", new NotificacoesErrosProcessamentoArquivadasAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

}
