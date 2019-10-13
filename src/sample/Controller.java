package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    List<Osoba> lista = new ArrayList<Osoba>();
    TreeItem<Osoba> root;
    @FXML
    Button dodaj = new Button();
    @FXML
    Button dodajO = new Button();
    @FXML
    Button zapisz = new Button();
    @FXML
    TextField poleTekstowe = new TextField();
    @FXML
    TextField pImie = new TextField();
    @FXML
    TextField pNazwisko = new TextField();
    @FXML
    TextField pWzrost = new TextField();
    @FXML
    ComboBox combo = new ComboBox();
    @FXML
    TreeTableView<Osoba> drzewko;
    @FXML
    TreeTableColumn<Osoba, String> c1;
    @FXML
    TreeTableColumn<Osoba, String> c2;
    @FXML
    TreeTableColumn<Osoba, Integer> c3;

    public void initialize(URL location, ResourceBundle resources) {
       String[] teksty = new String[] {"ala", "ola", "ula", "ela"};
       combo.getItems().addAll(teksty);
       String style = "-fx-background-color: \n" +
                "      #3c7fb1,\n" +
                "      linear-gradient(#fafdfe, #e8f5fc),\n" +
                "      linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-background-radius: 3,2,1;\n" +
                "    -fx-padding: 3 30 3 30;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-size: 14px;";
       dodaj.setStyle(style);
       zapisz.setStyle(style);
       dodajO.setStyle(style);
       c1.setCellValueFactory(new TreeItemPropertyValueFactory<Osoba, String>("Imie"));
       c2.setCellValueFactory(new TreeItemPropertyValueFactory<Osoba, String>("Nazwisko"));
       c3.setCellValueFactory(new TreeItemPropertyValueFactory<Osoba, Integer>("Wzrost"));
       WczytajOsoby();
       /*
       Osoba o1 = new Osoba("Janusz", "Monak", 211);
       Osoba o2 = new Osoba("Anna",   "Nowak", 171);
        Osoba o3 = new Osoba("Roman",  "Monak", 177);
        Osoba o4 = new Osoba("Alina",  "Koperek", 181);
        Osoba o5 = new Osoba("Maria",  "Zupka", 174);
        lista = new ArrayList<Osoba>();
        lista.add(o1);
        lista.add(o2);
        lista.add(o3);
        lista.add(o4);
        lista.add(o5);
        zapiszOsoby();
        */
        pokazDrzewko();
    }
    private void WczytajOsoby() {
        String napis = "";
        String plik = "osoby.json";
        /**
         * Blok try wczytuje dane z pliku
         */
        try {
            try
            {
                napis = new String ( Files.readAllBytes( Paths.get(plik) ) );
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        Gson gs = new Gson();
        Osoba[] osoby = gs.fromJson(napis, Osoba[].class);
        lista = new ArrayList<Osoba>(Arrays.asList(osoby));
    }
    @FXML
    public void dodanieTekstu(Event e) {
        String napis = poleTekstowe.getText();
        combo.getItems().addAll(napis);
        poleTekstowe.setText("");
    }
    @FXML
    public void pokazDrzewko() {
        root = new TreeItem<Osoba>(lista.get(0));
        for(Osoba osoba : lista) {
            TreeItem<Osoba> item = new TreeItem<Osoba>(osoba);
            root.getChildren().addAll(item);
        }
        root.setExpanded(true);
        drzewko.setRoot(root);
    }
    @FXML
    public void dodajOsobe(Event e) {
        String imie = pImie.getText();
        String nazw = pNazwisko.getText();
        int wzrost = 0;
        try {
            wzrost = Integer.parseInt(pWzrost.getText());
        }
        catch(Exception ex) {
            pokazKomunikat("Nieprawidłowa warotość wzrostu!");
        }
        Osoba o1 = new Osoba(imie, nazw, wzrost);
        lista.add(o1);
        TreeItem<Osoba> item = new TreeItem<Osoba>(o1);
        root.getChildren().addAll(item);
        root.setExpanded(true);
        drzewko.setRoot(root);
        zapisz.setDisable(false);
        pImie.setText("");
        pNazwisko.setText("");
        pWzrost.setText("");
    }
    public void pokazKomunikat(String komunikat) {
        final Stage dialog = new Stage();
        dialog.setTitle(komunikat);
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox layout= new VBox(new Text("Okienko pop up"));
        Scene scene1= new Scene(layout, 300, 250);
        dialog.setScene(scene1);
        dialog.showAndWait();
    }
    @FXML
    public void zapiszKolekcje(Event e) {
        zapiszOsoby();
        zapisz.setDisable(true);
    }
    private void zapiszOsoby() {
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        String napis =gs.toJson(lista);
        System.out.println(napis);
        try
        {
            File file = new File("osoby.json");
            FileWriter wr = new FileWriter(file);
            wr.write(napis);
            wr.close();
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
