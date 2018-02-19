package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    /* Name information */
    private static final String SO_NAME = "name";
    private static final String SO_MAIN_NAME = "mainName";
    private static final String SO_ALSO_KNOWN_AS = "alsoKnownAs";

    /* Origin information */
    private static final String SO_PLACE_OF_ORIGIN = "placeOfOrigin";

    /* Description */
    private static final String SO_DESCRIPTION = "description";

    /* Image */
    private static final String SO_IMAGE = "image";

    /* ingredients information */
    private static final String SO_INGREDIENTS = "ingredients";

    /**
     * This method parses JSON and returns a sandwich object
     * describing the sandwich and it's fields.
     *
     * @param json a String representation of the json of the sandwich
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObjectSandwich = new JSONObject(json);

            JSONObject jsonObjectName = jsonObjectSandwich.getJSONObject(SO_NAME);

            String mainName = jsonObjectName.getString(SO_MAIN_NAME);

            JSONArray alsoKnownAsJson = jsonObjectName.getJSONArray(SO_ALSO_KNOWN_AS);
            ArrayList<String> alsoKnowAsArray = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJson.length(); i++) {
                alsoKnowAsArray.add((String) alsoKnownAsJson.get(i));
            }

            String placeOfOrigin = jsonObjectSandwich.getString(SO_PLACE_OF_ORIGIN);
            String description = jsonObjectSandwich.getString(SO_DESCRIPTION);
            String image = jsonObjectSandwich.getString(SO_IMAGE);

            JSONArray ingredientsAsJson = jsonObjectSandwich.getJSONArray(SO_INGREDIENTS);
            ArrayList<String> ingredientsArray = new ArrayList<>();
            for (int i = 0; i < ingredientsAsJson.length(); i++) {
                ingredientsArray.add((String) ingredientsAsJson.get(i));
            }

            return new Sandwich(mainName, alsoKnowAsArray, placeOfOrigin, description, image, ingredientsArray);
        } catch (JSONException e) {
            return null;
        }
    }
}
