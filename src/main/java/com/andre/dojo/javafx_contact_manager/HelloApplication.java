package com.andre.dojo.javafx_contact_manager;

import com.andre.dojo.Models.Account;
import com.andre.dojo.Utils.ObjectSaver;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private static Stage stageOri;

    private static Account userNow;

    private static ObservableList<Account> listData = FXCollections.observableArrayList();

    public static int max_id = 0;

    @Override
    public void start(Stage stage) throws IOException {
        stageOri = stage;

        // mengambil data dari file.
        if (!ObjectSaver.readFileCache().isEmpty()){
            ObjectSaver.filenameAddress = ObjectSaver.readFileCache();
            if (ObjectSaver.retrieveAccount().getKey() != null){
                listData = ObjectSaver.retrieveAccount().getKey();
            }
            System.out.println("berhasil membaca dataCache.");
        }else{
            System.out.println("Belum ada file tersimpan");
        }
        initPrimaryStage();
    }

    private void initPrimaryStage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            scene.getRoot().setStyle(
                            "-fx-background-radius: 0 0 20 20;" + // Radius untuk corner bawah
                            "-fx-border-radius: 0 0 20 20;" +     // Radius untuk border
                            "-fx-border-color: black;" +
                            "-fx-border-width: 0px;"
            );
//            stageOri.initStyle(StageStyle.TRANSPARENT);
            stageOri.setTitle("Hello!");
            stageOri.setScene(scene);
            stageOri.show();
        }catch (IOException e1){
            e1.printStackTrace();
        }
    }

    public static Stage getStage() {
        return stageOri;
    }

    public static void addListData(Account Data) {
        listData.add(Data);
    }

    public static String makeVigenereString(String pass){
        StringBuilder dx = new StringBuilder();
        String c = "ayam";
        String c2 = "";
        int geser = 3;
        for (int j = 0; j < pass.length(); j++) {
            if (c.charAt(j % c.length()) == 32){

            }else{
                dx.append(c.charAt((j+geser) % c.length()));
            }
        }
        c2 = dx.toString();
        StringBuilder d2x = new StringBuilder();

        for (int j = 0; j < pass.length(); j++) {
            int o = ((pass.charAt(j) + c2.charAt(j)) % 26) + 65;

            d2x.append((char) o);
//            System.out.println(d2x.toString());
        }
        return d2x.toString();
    }

    public static String getSHA256Hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setListData(ObservableList<Account> listData) {
        HelloApplication.listData = listData;
    }

    public static ObservableList<Account> getListData() {
        return listData;
    }

    public static void setUserNow(Account userNow) {
        HelloApplication.userNow = userNow;
    }

    public static Account getUserNow() {
        return userNow;
    }

    public static void main(String[] args) {
        launch();
    }

}