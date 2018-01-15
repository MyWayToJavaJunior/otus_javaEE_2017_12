import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entities.Account;
import entities.AccountRole;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import storage.IDataStorageService;
import storage.db.MySQLConnectionFactory;
import storage.db.MySQLDataStorageService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception {


        IDataStorageService storageService = new MySQLDataStorageService();
        storageService.initStorage();
        storageService.importData();

    }
}
