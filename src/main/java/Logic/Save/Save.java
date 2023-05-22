package Logic.Save;

import Logic.Controller;
import Logic.Models.University;
import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;

public class Save {

    private static Logger log = LogManager.getLogger(Save.class);

    public static void saveChanges() {
        saveDepartments();
        saveRequests();
        saveProtests();

        log.info("Changes Saved");
    }

    private static void saveDepartments() {

        Gson gson = new Gson();
        File file = new File("src/main/resources/Database/Departments.json");

        try {

            String s = gson.toJson(University.getDepartments());
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(s);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("file baraye save yaft nashod");
        }

    }

    private static void saveRequests() {

        Gson gson = new Gson();
        File file = new File("src/main/resources/Database/Requests.json");

        try {

            String s = gson.toJson(University.getRequests());
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(s);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("file baraye save yaft nashod");
        }

    }

    private static void saveProtests() {

        Gson gson = new Gson();
        File file = new File("src/main/resources/Database/Protests.json");

        try {

            String s = gson.toJson(University.getProtests());
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(s);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("file baraye save yaft nashod");
        }

    }
}
