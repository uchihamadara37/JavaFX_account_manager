package com.andre.dojo.javafx_contact_manager;

import com.andre.dojo.Models.Account;
import com.andre.dojo.Utils.ObjectSaver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ExportController implements Initializable {

    @FXML
    private Button btnExportFile;
    @FXML
    private Button btnImportFile;
    @FXML
    private Label address;

    @FXML
    private TableView<Account> tableView;
    @FXML
    private TableColumn<Account, String> accountIdColumn;
    @FXML
    private TableColumn<Account, String> accountNameColumn;
    @FXML
    private TableColumn<Account, String> accountURLColumn;
    @FXML
    private TableColumn<Account, String> accountUsernameColumn;
    @FXML
    private TableColumn<Account, String> accountPasswordColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init export");
        loadContentTable();

        address.setText(ObjectSaver.filenameAddress);

        btnExportFile.setOnAction(e -> {

//            DirectoryChooser directoryChooser = new DirectoryChooser();
//            directoryChooser.setInitialDirectory(new File("/home/"));
//            File selectedDirectory = directoryChooser.showDialog(HelloApplication.getStage());
//
//            if(selectedDirectory == null){
//                //No Directory selected
//            }else{
//                address.setText(selectedDirectory.getAbsolutePath());
//                ObjectSaver.filename = selectedDirectory.getAbsolutePath() + "account_data.txt";
//                System.out.println(ObjectSaver.filename);
//            }
            File file = null;
            if (ObjectSaver.filenameAddress.isEmpty()){
                file = ObjectSaver.chooseFileSave();
            }else{
                int lastSlashIndex = ObjectSaver.filenameAddress.lastIndexOf("/");
                String directory = ObjectSaver.filenameAddress.substring(0, lastSlashIndex + 1);
                String fileName = ObjectSaver.filenameAddress.substring(lastSlashIndex + 1);
                file = ObjectSaver.chooseFileSave(directory, fileName);
            }
            if (file != null) {
                System.out.println(file.getAbsoluteFile());
                ObjectSaver.filenameAddress = file.getAbsolutePath();
                ObjectSaver.SaveAccount(HelloApplication.getListData());
                ObjectSaver.saveFileCache(file.getAbsolutePath());
                System.out.println("berhasil menyimpan data");

                HelloController.showAlert("Berhasil menyimpan data.");
                address.setText(ObjectSaver.filenameAddress);
            }
        });

        btnImportFile.setOnAction( e -> {
            File file = ObjectSaver.chooseFileSelectOpen();
            if (file != null) {
                System.out.println(file.getAbsoluteFile());
                String fileNameBefore = ObjectSaver.filenameAddress;

                ObjectSaver.filenameAddress = file.getAbsolutePath();
                if (ObjectSaver.retrieveAccount().getKey() != null){
                    for (Account ac : ObjectSaver.retrieveAccount().getKey()){

                        if (ac.getOwner() == null){
                            if (Objects.equals(ac.getId().get(), HelloApplication.getUserNow().getId().get())){
                                // data cocok
                                HelloApplication.setListData(ObjectSaver.retrieveAccount().getKey());
                                System.out.println("berhasil mengimport data");
                                HelloController.showAlert("Berhasil mengimport data");

                                loadContentTable();
                                ObjectSaver.saveFileCache(file.getAbsolutePath());
                                address.setText(ObjectSaver.filenameAddress);
                            }else{
//                                itu bukan data anda
                                HelloController.showAlert("Tidak bisa mengimport file tersebut. Data tersebut bukan milik anda");
                                ObjectSaver.filenameAddress = fileNameBefore;
                            }

                            break;
                        }
                    }

                }else{
                    System.out.println("isi file masih kosong");
                }
                ObjectSaver.saveFileCache(file.getAbsolutePath());
            }
        });
    }

    private void loadContentTable() {
        tableView.setItems(HelloApplication.getListData());
        accountIdColumn.setCellValueFactory(data -> data.getValue().getId());
        accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().getAccountName());
        accountURLColumn.setCellValueFactory(cellData -> cellData.getValue().getUrl());
        accountUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
        accountPasswordColumn.setCellValueFactory(cellData -> cellData.getValue().getPassword());
    }



}
