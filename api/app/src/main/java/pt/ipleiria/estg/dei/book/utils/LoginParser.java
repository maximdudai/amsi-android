package pt.ipleiria.estg.dei.book.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.User;

public class LoginParser {

    public static String parseLoginData(String response) {
        String auxUser = "";

        try {

            JSONObject getUser = new JSONObject(response);
            String getToken = getUser.getString("token");

            auxUser = getToken;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return auxUser;
    }
}
