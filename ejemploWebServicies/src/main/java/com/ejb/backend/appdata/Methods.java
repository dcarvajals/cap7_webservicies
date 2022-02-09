/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb.backend.appdata;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.swing.table.DefaultTableModel;

public final class Methods {

    public static String getJsonMessage(String status, String information, String data) {
        return "{\"status\":" + status + ",\"information\":\"" + information + "\",\"data\":" + data + "}";
    }

    /**
     * This method is for the security application.
     *
     * @param request Processes HTTP type requests
     * @param param String type variable, contains the information obtained to
     * the method.
     * @param defaulx String type variable, return variable
     * @return a String, for the security request.
     */
    public static String securRequest(HttpServletRequest request, String param, String defaulx) {
        try {
            String res = request.getParameter(param);
            return res != null ? res : defaulx;
        } catch (Exception e) {
            return defaulx;
        }
    }

    /**
     * This method is for the security application.
     *
     * @param email String type variable, contains the email.
     * @return a String, for the security request.
     */
    public static Boolean comprobeEmail(String email) {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");//".*@uteq.edu.ec"
        Matcher mat = pat.matcher(email);
        if (mat.matches()) {
            return (email.length() <= 100);// length in database
        } else {
            return false;
        }
    }

    public static Boolean comprobePassword(String pass) {
        Pattern pat = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\w{6,}");///^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])\w{6,}/
        Matcher mat = pat.matcher(pass);
        return mat.matches();// length in database
    }

    /**
     * Convert from string to json.
     *
     * @param json String type variable, contains the json to be converted.
     * @return a json.
     */
    public static JsonObject stringToJSON(String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject Jso = parser.parse(json).getAsJsonObject();
            return Jso;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new JsonObject();
        }
    }

    /**
     * Convert from string to json.
     *
     * @param json String type variable, contains the json to be converted.
     * @return a json.
     */
    public static JsonArray stringToJsonArray(String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsa = parser.parse(json).getAsJsonArray();
            return jsa;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new JsonArray();
        }
    }

    /**
     * Convert from string to json.
     *
     * @param json String type variable, contains the json to be converted.
     * @return a json.
     */
    public static JsonElement stringToJSON2(String json) {
        try {
            JsonElement parser = new JsonPrimitive(json);
            System.out.println(parser.getAsString());
            //JsonObject Jso = new JsonObject();
            //Jso =  (JsonObject) parser.p(json);
            return parser;
        } catch (Exception e) {
            return new JsonObject();
        }
    }

    /**
     * Get a part of the json.
     *
     * @param jso Variable type json, contains the information.
     * @param param String type variable, contains the name of the json
     * parameter to be divided.
     * @return a json, divided.
     */
    public static JsonElement securGetJSON(JsonObject jso, String param) {
        try {
            JsonElement res = jso.get(param);//request.getParameter(param);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to divide a json.
     *
     * @param jso Variable type json, contains the information.
     * @param param String type variable, contains the name of the json
     * parameter to be divided.
     * @param defaulx String type variable, return variable
     * @return Return a String, with the json divided.
     */
    public static String JsonToSub(JsonObject jso, String param, String defaulx) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                return res.toString();
            } else {
                return defaulx;
            }
        } catch (Exception e) {
            return defaulx;
        }
    }

    /**
     * A sub json of a json.
     *
     * @param jso Variable type json, contains the information.
     * @param param String type variable, contains the name of the json
     * parameter to be divided.
     * @return a json.
     */
    public static JsonObject JsonToSubJSON(JsonObject jso, String param) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                return res.getAsJsonObject();
            } else {
                return new JsonObject();
            }
        } catch (Exception e) {
            return new JsonObject();
        }
    }

    /**
     * From json to array.
     *
     * @param jso Variable type json, contains the information.
     * @param param String type variable, contains the name of the json
     * parameter to be divided.
     * @return a jsonArray, with data loaded
     */
    public static JsonArray JsonToArray(JsonObject jso, String param) {
        try {
            JsonArray jarr = jso.get(param).getAsJsonArray();
            if (jarr != null) {
                return jarr;
            } else {
                return new JsonArray();
            }
        } catch (Exception e) {
//            System.out.println("erro json a string");
            return new JsonArray();
        }
    }

    public static String[] JsonToStringVecttor(JsonObject jso, String param) {
        Gson gson = new Gson();

        try {
            String[] jarr = gson.fromJson(jso.get(param), String[].class);
            if (jarr != null) {
                return jarr;
            } else {
                return new String[]{};
            }
        } catch (Exception e) {
//            System.out.println("erro json a string");
            return new String[]{};
        }
    }

    /**
     * From json to String
     *
     * @param jso Variable type json, contains the information.
     * @param param String type variable, contains the name of the json
     * parameter to be divided.
     * @param defaulx String type variable, return variable
     * @return a String, with data loaded from the json.
     */
    public static String JsonToString(JsonObject jso, String param, String defaulx) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                String result = res.getAsString();
                result = result.trim().replace("\n", "\\n").replace("\t", "\\t").replace("'", "''");
                return result;
            } else {
                return defaulx;
            }
        } catch (Exception e) {
//            System.out.println("erro json a string");
            return defaulx;
        }
    }

    public static JsonObject objectToJson(Object jsonO) {
        try {
            return (JsonObject) jsonO;
        } catch (Exception e) {
            return new JsonObject();
        }
    }

    public static JsonArray objectToJsonArray(Object jsonarrayO) {
        try {
            return (JsonArray) jsonarrayO;
        } catch (Exception e) {
            return new JsonArray();
        }
    }

    public static String JsonToStringWithFormat(JsonObject jso, String param, String defaulx) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                String result = res.getAsString();
                return result;
            } else {
                return defaulx;
            }
        } catch (Exception e) {
//            System.out.println("erro json a string");
            return defaulx;
        }
    }

    /**
     * Obtain an element from a Json, and store it in a String variable.
     *
     * @param jse The variable type JsonElement, contains the information.
     * @param defaulx String type variable, contains the element of the selected
     * json.
     * @return a variable of type String, selected element of the json.
     */
    public static String JsonElementToString(JsonElement jse, String defaulx) {
        try {
            if (jse != null) {
                return jse.getAsString();
            } else {
                return defaulx;
            }
        } catch (Exception e) {
            return defaulx;
        }
    }

    /**
     * from JsonElement to json.
     *
     * @param jse Variable type jsonElement, contains an element of another
     * json.
     * @return an object-type json
     */
    public static JsonObject JsonElementToJSO(JsonElement jse) {
        try {
            if (jse != null) {
                return jse.getAsJsonObject();
            } else {
                return new JsonObject();
            }
        } catch (Exception e) {
            return new JsonObject();
        }
    }

    /**
     * from json to Integer.
     *
     * @param jso Variable type json, contains the information
     * @param param String type variable, contains the name of the json
     * parameter to be divided.
     * @param defaulx String type Integer, return variable
     * @return an integer, the variable is defaulx.
     */
    public static int JsonToInteger(JsonObject jso, String param, int defaulx) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                return res.getAsInt();
            } else {
                return defaulx;
            }
        } catch (Exception e) {
            return defaulx;
        }
    }

    /**
     * from json to boolean
     *
     * @param jso Variable type json, contains the information
     * @param param String type variable, contains the name of the json
     * parameter to be divided.
     * @param defaulx String type Boolean, return variable
     * @return an Boolean, the variable is defaulx.
     */
    public static Boolean JsonToBoolean(JsonObject jso, String param, boolean defaulx) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                return res.getAsBoolean();
            } else {
                return defaulx;
            }
        } catch (Exception e) {
            return defaulx;
        }
    }

    /**
     * From table to json.
     *
     * @param table Variable of type DefaultTableModel, table with loaded data
     * @return a String, contains a json with data.
     */
    public static String tableToJson(DefaultTableModel table) {
        String resul = "[";
        if (table.getRowCount() > 0) {
            int columCount = table.getColumnCount();
            for (int row = 0; row < table.getRowCount(); row++) {
                String line = "";
                for (int colum = 0; colum < columCount; colum++) {
                    String ro = table.getValueAt(row, colum).toString();
                    System.out.println(ro + ":" + JsonValid(ro));
                    ro = JsonValid(ro) ? ro : String.format("\"%s\"", ro.replace("\n", "\\n").replace("\t", "\\t").replace("\"", "\\\""));
                    line += "\"" + table.getColumnName(colum) + "\":" + ro;
                    if (colum < columCount - 1) {
                        line += ",";
                    }
                }
                if (line.length() > 0) {
                    resul += "{" + line + "}";
                    if (row < table.getRowCount() - 1) {
                        resul += ",";
                    }
                }
            }
            resul += "]";
        } else {
            resul = "[]";
        }
        System.out.println(resul);
        return resul;
    }

    public static boolean JsonValid(String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement jse = parser.parse(json);
            boolean flag1 = false, flag2 = false;
            try {
                jse.getAsJsonObject();
                flag1 = true;
            } catch (Exception e) {
                flag1 = false;
            }
            try {
                jse.getAsJsonArray();
                flag2 = true;
            } catch (Exception e) {
                flag2 = false;
            }
            return (flag1 || flag2);
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            return false;
        }
    }


    
}
