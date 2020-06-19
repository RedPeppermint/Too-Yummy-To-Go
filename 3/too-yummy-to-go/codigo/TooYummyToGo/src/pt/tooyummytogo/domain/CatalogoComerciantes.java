package pt.tooyummytogo.domain;

import pt.tooyummytogo.strategyNaoNecessario.SearchStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class CatalogoComerciantes {

    private List<Comerciante> comerciantes = new ArrayList<>();

    /**
     * Gets the list of Comerciante
     *
     * @return List of Comerciante
     */
    public List<Comerciante> getComerciantes() {
        return comerciantes;
    }

    /**
     * Adds a Comerciante to the list
     *
     * @param com Comerciante to add
     */
    public void addComerciante(Comerciante com) {
        comerciantes.add(com);
    }

    /**
     * Logs a Comerciante in
     *
     * @param username Username
     * @param password Password
     * @return true if the Comerciante logged in successfully, false otherwise
     * @ensures getComerciante(username).isPresent() if returns true
     */
    public boolean autenticar(String username, String password) {
        if (getComerciante(username).isPresent() && getComerciante(username).get().validatePassword(password)) {
            getComerciante(username).get().lerBuffer();
            return true;
        }
        return false;
    }

    /**
     * Gets a Comerciante from the list
     *
     * @param username Username to find
     * @return Optional.empty() if the username doesn't exist or Optional.of(the Comerciante with the username)
     */
    public Optional<Comerciante> getComerciante(String username) {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(new File("config.properties")));
            String strategy = properties.getProperty("searchComercianteStrategy");

            try {
                @SuppressWarnings("unchecked")
                Class<SearchStrategy<Comerciante, Comerciante>> klass = (Class<SearchStrategy<Comerciante, Comerciante>>) Class.forName(strategy);
                Constructor<SearchStrategy<Comerciante, Comerciante>> cons = klass.getConstructor();
                SearchStrategy<Comerciante, Comerciante> strat = cons.newInstance();

                return strat.search(username, comerciantes);

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println("Class or constructor related exception");
                return Optional.empty();
            }
        } catch (IOException e) {
            System.out.println("IOException");
            return Optional.empty();
        }
    }

    /**
     * Checks if a Comerciante with username exists
     *
     * @param username the username
     * @return true if the Comerciante with the username exists or false otherwise
     */
    public boolean contains(String username) {
        for (Comerciante comerciante : comerciantes) {
            if (comerciante.getUsername().equals(username))
                return true;
        }
        return false;
    }
}
