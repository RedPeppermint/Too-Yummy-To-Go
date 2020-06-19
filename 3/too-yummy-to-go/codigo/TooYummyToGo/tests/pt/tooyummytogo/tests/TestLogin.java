package pt.tooyummytogo.tests;

import org.junit.Test;
import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.NoPermissionsException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


/*
Deverá escrever um teste JUnit que cobra o caso de uso login (UC2),
bem como a verificação dos handlers a que tem acesso.
 */
public class TestLogin {

    @Test
    public void test() {
        testLoginCom();
        testLoginUt();

        testPermissoesCom();
        testPermissoesUt();
    }

    @Test
    public void testLoginUt() {
        TooYummyToGo ty2g = new TooYummyToGo();
        ty2g.getRegistarUtilizadorHandler().registarUtilizador("Silvino", "bardoc2");

        Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");

        talvezSessao.ifPresent((Sessao s) -> {
            assertEquals(s.getNomeAtual(), "Silvino");
        });
    }

    @Test
    public void testLoginCom() {
        TooYummyToGo ty2g = new TooYummyToGo();
        ty2g.getRegistarComercianteHandler().registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));

        Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");

        talvezSessao.ifPresent((Sessao s) -> {
            assertEquals(s.getNomeAtual(), "Silvino");
        });
    }

    @Test
    public void testPermissoesCom() {
        TooYummyToGo ty2g = new TooYummyToGo();
        ty2g.getRegistarComercianteHandler().registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));

        Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");
        talvezSessao.ifPresent((Sessao s) -> {
            assertDoesNotThrow(s::adicionarTipoDeProdutoHandler);
            assertDoesNotThrow(s::getColocarProdutoHandler);
            assertThrows(NoPermissionsException.class, s::getEncomendarHandler);
        });
    }

    @Test
    public void testPermissoesUt() {
        TooYummyToGo ty2g = new TooYummyToGo();
        ty2g.getRegistarUtilizadorHandler().registarUtilizador("Felismina", "hortadafcul");

        Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcul");
        talvezSessao.ifPresent((Sessao s) -> {
            assertThrows(NoPermissionsException.class, s::adicionarTipoDeProdutoHandler);
            assertThrows(NoPermissionsException.class, s::getColocarProdutoHandler);
            assertDoesNotThrow(s::getEncomendarHandler);
        });
    }

}
