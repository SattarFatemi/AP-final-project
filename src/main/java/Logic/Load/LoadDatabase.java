package Logic.Load;

import GuiController.Popup;
import Logic.Controller;
import Logic.Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoadDatabase {

    private static Logger log = LogManager.getLogger(LoadDatabase.class);

    public static void loadDatabase() {
        loadDepartments();
        loadRequests();
        loadProtests();
    }

    private static void loadDepartments() {
        Gson gson = new Gson();

        try {
            File file = new File("src/main/resources/Database/Departments.json");
            JsonReader reader = new JsonReader(new FileReader(file));

            Type departmentArrayType = new TypeToken<ArrayList<Department>>(){}.getType();
            ArrayList<Department> departmentsToLoad = gson.fromJson(reader, departmentArrayType);

            for (int i = 0; i < departmentsToLoad.size(); i++) {
                University.addDepartment(departmentsToLoad.get(i));
            }

        } catch (FileNotFoundException e) {
            Popup.showAlert("Loading", "Loading Faild :(");
            log.error("Loading Departments Failed");
            e.printStackTrace();
        }
    }

    private static void loadRequests() {
        Gson gson = new Gson();

        try {
            File file = new File("src/main/resources/Database/Requests.json");
            JsonReader reader = new JsonReader(new FileReader(file));

            Type requestArrayType = new TypeToken<ArrayList<Request>>(){}.getType();
            ArrayList<Request> requestsToLoad = gson.fromJson(reader, requestArrayType);

            for (int i = 0; i < requestsToLoad.size(); i++) {
                University.addRequest(requestsToLoad.get(i));
            }

        } catch (FileNotFoundException e) {
            Popup.showAlert("Loading", "Loading Faild :(");
            log.error("Loading Requests Failed");
            e.printStackTrace();
        }
    }

    private static void loadProtests() {
        Gson gson = new Gson();

        try {
            File file = new File("src/main/resources/Database/Protests.json");
            JsonReader reader = new JsonReader(new FileReader(file));

            Type protestArrayType = new TypeToken<ArrayList<Protest>>(){}.getType();
            ArrayList<Protest> protestsToLoad = gson.fromJson(reader, protestArrayType);

            for (int i = 0; i < protestsToLoad.size(); i++) {
                University.addProtest(protestsToLoad.get(i));
            }

        } catch (FileNotFoundException e) {
            Popup.showAlert("Loading", "Loading Faild :(");
            log.error("Loading Protests Failed");
            e.printStackTrace();
        }
    }

}
