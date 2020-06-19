package pt.tooyummytogo.strategy;

import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

import java.util.List;

public interface FindStrategy {

    List<ComercianteInfo> find(PosicaoCoordenadas localizacao, EncomendarHandler ah);
}
