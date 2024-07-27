package com.andre.dojo.javafx_contact_manager;

import com.andre.dojo.Models.Account;
import com.andre.dojo.Utils.ObjectSaver;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;

    @FXML
    private AnchorPane anchorPaneRoot;
    @FXML
    private AnchorPane anchorPane;

    public static AnchorPane anchorPaneRootStatic;
    public static AnchorPane anchorPaneStatic;

    @FXML
    private Label pesan;
    @FXML
    private Button btnImportFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText("");
        password.setText("");
//        pesan.setText(ObjectSaver.filenameAddress);
        pesan.setText("");

        anchorPaneRootStatic = anchorPaneRoot;
        anchorPaneStatic = anchorPane;

        btnLogin.setOnAction(event -> {
            // logic login
            System.out.println("LOgin jarene");
            loginAction();
        });

        btnImportFile.setOnMouseEntered(e -> {
            pesan.setText("You can load your recent data from file.txt");
        });
        btnImportFile.setOnMouseExited(e -> {
//            pesan.setText(ObjectSaver.filenameAddress);
            pesan.setText("");
        });
        btnSignup.setOnMouseEntered(e -> {
            pesan.setText("if you don't have first account then please do registration!");
        });
        btnSignup.setOnMouseExited(e -> {
//            pesan.setText(ObjectSaver.filenameAddress);
            pesan.setText("");
        });
        btnSignup.setOnAction(event -> {
            signupAction();
        });
        btnImportFile.setOnAction(e -> {
            File file = ObjectSaver.chooseFileSelectOpen();
            if (file !=null){
                System.out.println(file.getAbsoluteFile());
                ObjectSaver.filenameAddress = file.getAbsolutePath();
                if (ObjectSaver.retrieveAccount().getKey() != null){
                    HelloApplication.setListData(ObjectSaver.retrieveAccount().getKey());
                    System.out.println("berhasil mengimport data");
                    ObjectSaver.saveFileCache(file.getAbsolutePath());
                    pesan.setText(ObjectSaver.filenameAddress);
                }else{
                    System.out.println("isi file masih kosong");
                }
                ObjectSaver.saveFileCache(file.getAbsolutePath());
            }
        });


//        System.out.println("Jumlah id max = "+HelloApplication.max_id);
    }

    public void setPesan(String pesan) {
        this.pesan.setText(pesan);
    }

    private void signupAction() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();
            anchorPaneRoot.getChildren().removeFirst();
            anchorPaneRoot.getChildren().add(0, anchorPane);
            HelloApplication.getStage().setTitle("Sign up!");

        }catch (IOException e1){
            System.out.println(e1.toString());
            e1.printStackTrace();
        }
    }

    private void loginAction() {
        String uname = username.getText();
        String pass = password.getText();
        boolean ketemu = false;

//        int i = 1;
        if (HelloApplication.getListData().isEmpty()){
            showAlert("Belum ada akun yang terdaftar");
            return;
        }else{
            for(Account data : HelloApplication.getListData()){
//            System.out.println(i);
                if (data.getOwner() == null){
                    if (data.getUsername().get().equals(uname) ){
                        System.out.println("oi "+1);
                        if (data.getPassword().get().equals(pass)){
                            HelloApplication.setUserNow(data);
                            ketemu = true;
                            break;
                        }
                    }
                }
//            i++;
            }
        }

        if (ketemu){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getRoot().setStyle(
                        "-fx-background-color: white;" +
                                "-fx-background-radius: 0 0 20 20;" + // Radius untuk corner bawah
                                "-fx-border-radius: 0 0 20 20;" +     // Radius untuk border
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1px;"
                );
                HelloApplication.getStage().setTitle("Dashboard");
                HelloApplication.getStage().setScene(scene);
            }catch (IOException e1){
                System.out.println(e1.toString());
                e1.printStackTrace();
            }
        }else{
            showAlert("Maaf password atau username tidak cocok");
//            Dialog<ButtonType> dialog = new Dialog<>();
//            dialog.setTitle("Custom Dialog");
//            dialog.setHeaderText("Ini adalah header dialog");
//            dialog.setContentText("Username atau password salah.");
//
//            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
//            dialog.getDialogPane().getButtonTypes().add(okButton);
//
//            dialog.showAndWait();
        }
    }

    public static void showAlert(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }


}