package pt.tooyummytogo.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.monstercard.Card;
import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.adapter.MyCard;
import pt.tooyummytogo.exceptions.NoPermissionsException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

public class ClienteExemplo {
    public static void main(String[] args) {
        TooYummyToGo ty2g = new TooYummyToGo();

        // UC1
        RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
        regHandler.registarUtilizador("Felismina", "hortadafcul");


        // UC3
        RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

        regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
        regComHandler.registarComerciante("Maribel", "torredotombo", new PosicaoCoordenadas(33.5, 45.2));

        // UC4
        Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");
        talvezSessao.ifPresent((Sessao s) -> {
            AdicionarTipoDeProdutoHandler atp = null;
            try {
                atp = s.adicionarTipoDeProdutoHandler();
            } catch (NoPermissionsException e) {
                System.out.println("ERRO");
                return;
            }
            Random r = new Random();
            for (String tp : new String[]{"Pão", "Pão de Ló", "Mil-folhas"}) {
                atp.registaTipoDeProduto(tp, r.nextDouble() * 10);
            }
        });

        // UC5
        Optional<Sessao> talvezSessao2 = ty2g.autenticar("Silvino", "bardoc2");
        talvezSessao2.ifPresent((Sessao s) -> {

            ColocarProdutoHandler cpv = null;
            try {
                cpv = s.getColocarProdutoHandler();
            } catch (NoPermissionsException e) {
                System.out.println("ERRO");
                return;
            }

            List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();

            boolean bool1 = cpv.indicaProduto(listaTiposDeProdutos.get(0), 10); // Pão
            boolean bool2 = cpv.indicaProduto(listaTiposDeProdutos.get(2), 5); // Mil-folhas

            if (!bool1) {
                AdicionarTipoDeProdutoHandler atp = null;
                try {
                    atp = s.adicionarTipoDeProdutoHandler();
                } catch (NoPermissionsException e) {
                    System.out.println("ERRO");
                    return;
                }
                Random r = new Random();
                atp.registaTipoDeProduto("TipoProduto1", r.nextDouble() * 10);
                cpv.indicaProduto("TipoProduto14", 10);
            }
            if (!bool2) {
                AdicionarTipoDeProdutoHandler atp = null;
                try {
                    atp = s.adicionarTipoDeProdutoHandler();
                } catch (NoPermissionsException e) {
                    System.out.println("ERRO");
                    return;
                }
                Random r = new Random();
                atp.registaTipoDeProduto("TipoProduto2", r.nextDouble() * 10);
                cpv.indicaProduto("TipoProduto25", 5);
            }

            cpv.confirma(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

            System.out.println("Produtos disponíveis");
        });

        // UC6 + UC7
        Optional<Sessao> talvezSessao3 = ty2g.autenticar("Felismina", "hortadafcul");
        talvezSessao3.ifPresent((Sessao s) -> {
            EncomendarHandler lch = null;
            try {
                lch = s.getEncomendarHandler();
            } catch (NoPermissionsException e) {
                System.out.println("ERRO");
                return;
            }
            List<ComercianteInfo> cs = lch.indicaLocalizacaoActual(new PosicaoCoordenadas(34.5, 45.2));

            for (ComercianteInfo i : cs) {
                System.out.println(i);
            }

            boolean redefineRaio = false;
            if (redefineRaio) {
                cs = lch.redefineRaio(100);
                for (ComercianteInfo i : cs) {
                    System.out.println(i);
                }
            }

            boolean redefinePeriodo = false;
            if (redefinePeriodo) {
                cs = lch.redefinePeriodo(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
                for (ComercianteInfo i : cs) {
                    System.out.println(i);
                }
            }


            // A partir de agora é UC7
            List<ProdutoInfo> ps = lch.escolheComerciante(cs.get(0));
            for (ProdutoInfo p : ps) {
                lch.indicaProduto(p, 1); // Um de cada
            }

            MyCard mycard = new MyCard(new Card("365782312312", "764", "02", "2021"));

            String codigoReserva = lch.indicaPagamento(mycard);

            System.out.println("Reserva " + codigoReserva + " feita com sucesso");
        });


        System.err.println("\n---------------------------------------------------------------------------");
        System.err.println("\nMostar como implementamos o observer, com um buffer no comerciante " +
                "que lança a mensagem quando ele faz login:\n");
        ty2g.autenticar("Silvino", "bardoc2");


        //Autenticacoes impossiveis
        System.err.println("\nAutenticacoes impossiveis:\n");
        ty2g.autenticar("Felismina", "PalavraPasseErrada");
        ty2g.autenticar("Silvino", "PalavraPasseErrada");
        ty2g.autenticar("UserQueNaoExiste", "password123");
        System.err.println();

    }
}
