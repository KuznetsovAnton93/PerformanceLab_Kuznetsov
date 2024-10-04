import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestReportGenerator {
    public static <Gson> void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Необходимо указать три пути к файлам: <values.json> <tests.json> <report.json>");
            return;
        }

        String valuesFilePath = args[0];
        String testsFilePath = args[1];
        String reportFilePath = args[2];

        com.google.gson.Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Чтение значений из values.json
        Map<Integer, String> valuesMap = readValues(valuesFilePath, gson);
        if (valuesMap == null) return;

        // Чтение структуры тестов из tests.json
        JsonObject testsJson = readJsonFile(testsFilePath, gson);
        if (testsJson == null) return;

        // Заполнение значений в tests.json
        fillValues(testsJson, valuesMap);

        // Запись результата в report.json
        writeJsonToFile(reportFilePath, testsJson, gson);
    }

    private static Map<Integer, String> readValues(String filePath, Gson gson) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            JsonObject jsonObject = gson.fromJson(br, JsonObject.class);
            JsonArray valuesArray = jsonObject.getAsJsonArray("values");
            Map<Integer, String> valuesMap = new HashMap<>();
            for (JsonElement element : valuesArray) {
                JsonObject valueObj = element.getAsJsonObject();
                int id = valueObj.get("id").getAsInt();
                String value = valueObj.get("value").getAsString();
                valuesMap.put(id, value);
            }
            return valuesMap;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла values.json: " + e.getMessage());
            return null;
        }
    }

    private static JsonObject readJsonFile(String filePath, Gson gson) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return gson.fromJson(br, JsonObject.class);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла tests.json: " + e.getMessage());
            return null;
        }
    }

    private static void fillValues(JsonObject testsJson, Map<Integer, String> valuesMap) {
        JsonArray testsArray = testsJson.getAsJsonArray("tests");
        fillValuesRecursively(testsArray, valuesMap);
    }

    private static void fillValuesRecursively(JsonArray testsArray, Map<Integer, String> valuesMap) {
        for (JsonElement element : testsArray) {
            JsonObject test = element.getAsJsonObject();
            int id = test.get("id").getAsInt();
            if (valuesMap.containsKey(id)) {
                test.addProperty("value", valuesMap.get(id));
            }

            if (test.has("values")) {
                fillValuesRecursively(test.getAsJsonArray("values"), valuesMap);
            }
        }
    }

    private static void writeJsonToFile(String filePath, JsonObject jsonObject, Gson gson) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(jsonObject, writer);
            System.out.println("Отчет успешно записан в " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла report.json: " + e.getMessage());
        }
    }
}
