package com.andre.dojo.javafx_contact_manager;

import com.andre.dojo.Models.Account;
import com.andre.dojo.Utils.ObjectSaver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    private TextField accountName;
    @FXML
    private TextField acUrl;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField repPassword;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnBack;
    @FXML
    private Label pesan;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pesan.setText("");
        btnBack.setOnAction(event -> {
            HelloController.anchorPaneRootStatic.getChildren().removeFirst();
            HelloController.anchorPaneRootStatic.getChildren().add(HelloController.anchorPaneStatic);
            HelloApplication.getStage().setTitle("Dashboard!");
        });

        btnSignUp.setOnAction(event -> {
            String acName = accountName.getText();
            String acUrl2 = acUrl.getText();
            String uname = username.getText();
            String pass = password.getText();
            String repeatPass = repPassword.getText();
            if (
                    !Objects.equals(acName, "") || !Objects.equals(acUrl2, "") || !Objects.equals(uname, "") || !Objects.equals(pass, "") || !Objects.equals(repeatPass, "")
            ) {
                if (Objects.equals(pass, repeatPass)){
                    boolean passMengandungSpace = false;
                    for (int j = 0; j < pass.length(); j++) {
                        if (pass.charAt(j) == 32){
                            // mengandung space
                            passMengandungSpace = true;
                            System.out.println("password tidak boleh mengandung spasi");
                        }
                    }
                    if (passMengandungSpace){
                        pesan.setText("password tidak boleh mengandung spasi");
                    }else{
                        // sudah sesuai. tinggal membuat id
                        HelloApplication.max_id++;
                        HelloApplication.addListData(new Account(HelloApplication.max_id, acName, acUrl2, uname, pass));
                        clearField();
//                        ObjectSaver.SaveAccount(HelloApplication.getListData());
                        pesan.setText("berhasil menambahkan akun!");
                        HelloController.anchorPaneRootStatic.getChildren().removeFirst();
                        HelloController.anchorPaneRootStatic.getChildren().add(HelloController.anchorPaneStatic);
                        HelloApplication.getStage().setTitle("Dashboard!");
                    }
                }else{
                    pesan.setText("Ulangi password dengan benar!");
                }
            }else{
                pesan.setText("Pastikan semua kolom di atas terisi!");
            }
        });
    }

    private void clearField() {
        accountName.setText("");
        acUrl.setText("");
        username.setText("");
        password.setText("");
        repPassword.setText("");
    }
}
