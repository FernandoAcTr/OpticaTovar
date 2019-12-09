package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

public class MyUtils {
    static DecimalFormat formatter = new DecimalFormat("##.00");

    public static String trimFloat(Float number){
        return formatter.format(number);
    }

    public static void openWindow(URL url, String title){
        Parent root;
        try {
            root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openWindow(URL url, String title, Object controller){
        FXMLLoader loader = new FXMLLoader(url);
        Parent root;
        try {
            loader.setController(controller);
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> getStates(){
        ObservableList<String> listStates = FXCollections.observableArrayList();
        listStates.add("Aguascalientes");
        listStates.add("Baja California");
        listStates.add("Baja California Sur");
        listStates.add("Campeche");
        listStates.add("Coahuila de Zaragoza");
        listStates.add("Colima");
        listStates.add("Chiapas");
        listStates.add("Chihuahua");
        listStates.add("Distrito Federal");
        listStates.add("Durango");
        listStates.add("Guanajuato");
        listStates.add("Guerrero");
        listStates.add("Hidalgo");
        listStates.add("Jalisco");
        listStates.add("México");
        listStates.add("Michoacán");
        listStates.add("Morelos");
        listStates.add("Nayarit");
        listStates.add("Nuevo León");
        listStates.add("Oaxaca");
        listStates.add("Puebla");
        listStates.add("Querétaro");
        listStates.add("Quintana Roo");
        listStates.add("San Luis Potosí");
        listStates.add("Sinaloa");
        listStates.add("Sonora");
        listStates.add("Tabasco");
        listStates.add("Tamaulipas");
        listStates.add("Tlaxcala");
        listStates.add("Veracruz");
        listStates.add("Yucatán");
        listStates.add("Zacatecas");
        return listStates;
    }

    public static ObservableList<String> getStatusOptions(){
        ObservableList<String> status = FXCollections.observableArrayList();
        status.add("Activo");
        status.add("Inactivo");
        return status;
    }

    public static ObservableList<String> getGeneres(){
        ObservableList<String> generes = FXCollections.observableArrayList();
        generes.add("M");
        generes.add("F");
        return generes;
    }

    public static void stablishTextFiedlLimit(TextField textField, int limit){
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() >= limit)
                event.consume();
        });
    }

    public static void stablishNumericRestriction(TextField textField){
        textField.setOnKeyTyped(event -> {
            if(Character.isLetter(event.getCharacter().charAt(0)))
                event.consume();
        });
    }

    public static Alert makeDialog(String title, String header, String content, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }

    public static String formatDouble(double number){
        DecimalFormat formatter = new DecimalFormat("00.00");
        return formatter.format(number);
    }
}
