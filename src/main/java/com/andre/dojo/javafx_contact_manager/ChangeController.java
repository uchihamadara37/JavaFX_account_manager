package com.andre.dojo.javafx_contact_manager;

import com.andre.dojo.Models.Account;
import com.andre.dojo.Models.HistoryPassword;
import com.andre.dojo.Utils.ObjectSaver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChangeController implements Initializable {
    @FXML
    private TableView<Account> tableView;
    @FXML
    private TableColumn<Account, String> accountNameColumn;
    @FXML
    private TableColumn<Account, String> accountURLColumn;
    @FXML
    private TableColumn<Account, String> accountUsernameColumn;
//    @FXML
//    private TableColumn<Account, String> accountPasswordColumn;

    @FXML
    private TextField accountName;
    @FXML
    private TextField acUrl;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;


    @FXML
    private TableView<HistoryPassword> tableView2;
    @FXML
    private TableColumn<HistoryPassword, String> waktuColumn;
    @FXML
    private TableColumn<HistoryPassword, String> passNewColumn;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
//    @FXML
//    private Button btnChange;
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSearch;
    @FXML
    private TextField searchField;
    @FXML
    private Label pesan;

    private String idSelected;
    ObservableList<HistoryPassword> historyKosong = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pesan.setText("");
        btnDelete.setVisible(false);
        btnUpdate.setVisible(true);
        btnCancel.setVisible(false);
        tableView2.setVisible(false);

        reloadContentOfTable(HelloApplication.getListData());
        reloadContentOfTableHistory(historyKosong);

//        accountPasswordColumn.setCellValueFactory(cellData -> cellData.getValue().getPassword());

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

        btnCancel.setOnAction(e -> {
            setStateInsert();
            showPersonDetails(null);
        });

        btnUpdate.setOnAction(e -> {
            if (Objects.equals(btnUpdate.getText(), "Add Data")){
                if (checkFieldIsAppropriate()){
                    insertAccount();
                }
            }else{
                if (checkFieldIsAppropriate()){
                    updateSelectedData();
                }
            }
        });

        btnDelete.setOnAction(e -> {
            if (checkFieldIsAppropriate()){
                if (showConfirmDialog("Do you really want to delete this one?")){
                    boolean hasil = HelloApplication.getListData().removeIf(ac -> ac.getOwner() != null && Objects.equals(idSelected, ac.getId().get()));
                    if (hasil) showPersonDetails(null);
                    System.out.println(hasil);
                }
                if(!Objects.equals(ObjectSaver.filenameAddress, "")){
                    ObjectSaver.SaveAccount(HelloApplication.getListData());
                }
            }
        });


        searchField.setOnKeyReleased(e -> {
            String input = searchField.getText().toLowerCase();
            ObservableList<Account> listBaru = FXCollections.observableArrayList();
            boolean ketemu = false;
            for(Account ac : HelloApplication.getListData()){
                if (ac.getAccountName().get().toLowerCase().contains(input)){
                    listBaru.add(ac);
                    ketemu = true;
                    continue;
                }
                if (ac.getUrl().get().toLowerCase().contains(input)){
                    listBaru.add(ac);
                    ketemu = true;
                    continue;
                }
                if (ac.getUsername().get().toLowerCase().contains(input)){
                    listBaru.add(ac);
                    ketemu = true;
                    continue;
                }
            }
            System.out.println(listBaru.size());
            if (!input.isEmpty()){
                reloadContentOfTable(listBaru);
            }else{
                reloadContentOfTable(HelloApplication.getListData());
            }
        });
    }

    private boolean showConfirmDialog(String message) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText(message);
//        confirm.setContentText(message);

        ButtonType result = confirm.showAndWait().orElse(ButtonType.CANCEL);

        //            System.out.println("User chose OK");
        //            System.out.println("User chose Cancel or closed the dialog");
        return result == ButtonType.OK;
    }

    private void updateSelectedData() {
        String acName = accountName.getText();
        String acURL = acUrl.getText();
        String acUsername = username.getText();
        String acPass = password.getText();
        for (Account ac : HelloApplication.getListData()){
            if (Objects.equals(ac.getId().get(), idSelected) && showConfirmDialog("Are you sure want to update this one?")){
                ac.addHistoryPassword(new HistoryPassword(
                        ac.getId().get(),
                        getTimeNow(),
                        ac.getPassword().get(),
                        acPass));

                ac.setUrl(acURL);
                ac.setPassword(acPass);
                HelloController.showAlert("Data berhasil diupdate!");
                pesan.setText("data berhasil diupdate!");
//                reloadContentOfTable();
                showPersonDetails(null);
                if(!Objects.equals(ObjectSaver.filenameAddress, "")){
                    ObjectSaver.SaveAccount(HelloApplication.getListData());
                }
                break;
            }
        }
    }

    public static String getTimeNow() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        return formatter.format(now);
    }

    private void reloadContentOfTable(ObservableList<Account> list) {
        tableView.setItems(list);
        accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().getAccountName());
        accountURLColumn.setCellValueFactory(cellData -> cellData.getValue().getUrl());
        accountUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
    }

    private void insertAccount() {
        String acName = accountName.getText();
        String acURL = acUrl.getText();
        String acUsername = username.getText();
        String acPass = password.getText();
        pesan.setText("berhasil menginput data!");
        Account ac = new Account(HelloApplication.getUserNow(), SignupController.generateId(), acName, acURL, acUsername, acPass);
        ac.addHistoryPassword(new HistoryPassword(ac.getId().get(), getTimeNow(), "", acPass));
        HelloApplication.addListData(ac);

        reloadContentOfTable(HelloApplication.getListData());
        if(!Objects.equals(ObjectSaver.filenameAddress, "")){
            ObjectSaver.SaveAccount(HelloApplication.getListData());
        }
        showPersonDetails(null);
        tableView2.setVisible(false);
        HelloController.showAlert("Berhasil menginput data!");
    }

    private boolean checkFieldIsAppropriate() {
        String acName = accountName.getText();
        String acURL = acUrl.getText();
        String acUsername = username.getText();
        String acPass = password.getText();
        String acRepPass = repeatPassword.getText();

        if (Objects.equals(acName, "") || Objects.equals(acURL, "") || Objects.equals(acUsername, "") || Objects.equals(acPass, "") || Objects.equals(acRepPass, "")){
            pesan.setText("Pastikan anda mengisi semua kolom ini!");
            return false;
        }else{
            if (!Objects.equals(acPass, acRepPass)){
                pesan.setText("Ulangi password dengan benar");
                return false;
            }else{
                return true;
            }
        }
    }

    private void setStateInsert() {
        btnDelete.setVisible(false);
        btnUpdate.setVisible(true);
        btnCancel.setVisible(false);
        btnUpdate.setText("Add Data");
    }

    private void showPersonDetails(Account person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            accountName.setText(person.getAccountName().get());
            acUrl.setText(person.getUrl().get());
            username.setText(person.getUsername().get());
            password.setText(person.getPassword().get());
            idSelected = person.getId().get();

            setStateChange();
            repeatPassword.setText("");
            if(person.getHistoryPassword().isEmpty()){
                reloadContentOfTableHistory(person.getHistoryPassword());
                tableView2.setVisible(false);
            }else{
                reloadContentOfTableHistory(person.getHistoryPassword());
                tableView2.setVisible(true);
            }
//            tableView.getSelectionModel().clearSelection();

        } else {
            // Person is null, remove all the text.
            accountName.setText("");
            acUrl.setText("");
            username.setText("");
            password.setText("");
            repeatPassword.setText("");
            idSelected = "";

            reloadContentOfTableHistory(historyKosong);
        }
    }

    private void reloadContentOfTableHistory(ObservableList<HistoryPassword> histories) {
        tableView2.setItems(histories);
        waktuColumn.setCellValueFactory(cellData -> cellData.getValue().waktuUpdate);
        passNewColumn.setCellValueFactory(data -> data.getValue().passwordBaru);
    }

    private void setStateChange() {
        btnDelete.setVisible(true);
        btnUpdate.setVisible(true);
        btnCancel.setVisible(true);
        btnUpdate.setText("Update");
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedIndex);
    }
}
