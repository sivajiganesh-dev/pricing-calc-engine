package org.commerce.pricing.data;

import org.commerce.pricing.component.Component;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class for initializing and managing the default configuration.
 * It reads from json file provided in resource path
 */
public class DataSource {
    private static final String NAME_KEY = "name";
    private static final String PRICE_KEY = "price";
    private static final String COMPONENTS_KEY = "components";

    private static final List<Component> components = new ArrayList<>();
    private static String name;
    private static DataSource dataSource;

    private DataSource() {
    }

    /**
     * Load basic configuration and initiate singleton object
     *
     * @return DataSource return singleton object
     */
    public static DataSource getInstance() {
        if (dataSource == null) {
            dataSource = new DataSource();
            loadData();
        }
        return dataSource;
    }

    /**
     * Load default configuration from .json file available in resource
     */
    private static void loadData() {
        JSONParser parser = new JSONParser();

        try {
            InputStream file = DataSource.class.getResourceAsStream("/init-data.json");
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(file, StandardCharsets.UTF_8));
            name = (String) jsonObject.get(NAME_KEY);
            JSONArray jsonArray = (JSONArray) jsonObject.get(COMPONENTS_KEY);

            addComponents(components, jsonArray);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Initialize tree structure for components data
     *
     * @param components     list of components
     * @param componentArray JSON array object data
     */
    private static void addComponents(List<Component> components, JSONArray componentArray) {
        for (Object compObj : componentArray) {
            String name = (String) ((JSONObject) compObj).get(NAME_KEY);
            Double price = (Double) ((JSONObject) compObj).get(PRICE_KEY);

            Component component = new Component(name, price);

            if (components == null)
                components = new ArrayList<>();

            components.add(component);

            JSONArray subComponents = (JSONArray) ((JSONObject) compObj).get(COMPONENTS_KEY);

            if (subComponents.size() > 0) {
                addComponents(component.getComponents(), subComponents);
            }
        }
    }

    public List<Component> getComponents() {
        return components;
    }

    public String getName() {
        return name;
    }
}
