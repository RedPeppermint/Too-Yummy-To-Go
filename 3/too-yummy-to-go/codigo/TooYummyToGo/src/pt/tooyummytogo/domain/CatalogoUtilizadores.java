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

public class CatalogoUtilizadores {

    private List<Utilizador> users = new ArrayList<>();

    /**
     * Adds a Utilizador to the list of Utilizadores
     *
     * @param user the Utilizador to add
     */
    public void addUtilizador(Utilizador user) {
        users.add(user);
    }

    /**
     * Logs a Utilizador in
     *
     * @param username Username
     * @param password Password
     * @return true if the Utilizador logged in successfully, false otherwise
     * @ensures getUtilizador(username).isPresent() if returns true
     */
    public boolean autenticar(String username, String password) {
        return getUtilizador(username).isPresent() && getUtilizador(username).get().validatePassword(password);
    }

    /**
     * Gets a Utilizador from the list
     *
     * @param username Username to find
     * @return Optional.empty() if the username doesn't exist or Optional.of(the Comerciante with the username)
     */
    public Optional<Utilizador> getUtilizador(String username) {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(new File("config.properties")));
            String strategy = properties.getProperty("searchUtilizadorStrategy");

            try {
                Class<SearchStrategy<Utilizador, Utilizador>> klass = (Class<SearchStrategy<Utilizador, Utilizador>>) Class.forName(strategy);
                Constructor<SearchStrategy<Utilizador, Utilizador>> cons = klass.getConstructor();
                SearchStrategy<Utilizador, Utilizador> strat = cons.newInstance();
                return strat.search(username, users);

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
     * Checks if a Utilizador with username exists
     *
     * @param username the username
     * @return true if the Utilizador with the username exists or false otherwise
     */
    public boolean contains(String username) {
        for (Utilizador user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }
}
