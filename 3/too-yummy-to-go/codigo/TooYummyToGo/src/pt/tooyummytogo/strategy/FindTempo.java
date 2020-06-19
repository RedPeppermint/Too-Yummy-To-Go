package pt.tooyummytogo.strategy;

import pt.tooyummytogo.domain.CatalogoComerciantes;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

import java.util.ArrayList;
import java.util.List;

public class FindTempo implements FindStrategy {

    /**
     * Finds all Comerciante's within the current time frame and radius defined in the EncomendaHandler
     *
     * @param localizacao The location of the Utilizador
     * @param ah          The EncomendaHandler
     * @return The list of Comerciante's within the defined radius and time frame
     */
    @Override
    public List<ComercianteInfo> find(PosicaoCoordenadas localizacao, EncomendarHandler ah) {
        List<Comerciante> allComercs = ah.getCat().getComerciantes();

        List<ComercianteInfo> result = new ArrayList<>();
        for (Comerciante comerciante : allComercs) {
            if (comerciante.estaDisponivel(localizacao, ah.getRaio(), ah.getHi(), ah.getHf())) {
                result.add(new ComercianteInfo(comerciante));
            }
        }
        return result;
    }
}
