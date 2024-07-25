package com.andre.dojo.Models;

import com.andre.dojo.Models.HistoryPassword;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.LinkedList;

public class Account implements Serializable {
    private final IntegerProperty id;
    private final StringProperty accountName;
    private StringProperty url;
    private final StringProperty username;
    private StringProperty password;
    private ObservableList<HistoryPassword> historyPassword = FXCollections.observableArrayList();

    public Account(int id, String accountName, String url, String username, String password){
        this.id = new SimpleIntegerProperty(id);
        this.accountName = new SimpleStringProperty(accountName);
        this.url = new SimpleStringProperty(url);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    @Override
    public String toString(){
        return id.get()+"<>"+accountName.get()+"<>"+url.get()+"<>"+username.get()+"<>"+password.get();
    }
    public String getString(){
        return toString();
    }
    public static Account fromString(String akunString){
        String[] parts = akunString.split("<>");
        int id = Integer.parseInt(parts[0]);
        String jenisAkun = parts[1];
        String url = parts[2];
        String username = parts[3];
        String password = parts[4];
        return new Account(id, jenisAkun, url, username, password);
    }

    public void addHistoryPassword(HistoryPassword history){
        this.historyPassword.add(history);
    }

    public void setPassword(StringProperty password) {
        this.password = password;
    }

    public StringProperty getAccountName() {
        return accountName;
    }

    public StringProperty getPassword() {
        return password;
    }

    public StringProperty getUsername() {
        return username;
    }

    public void setHistoryPassword(LinkedList<HistoryPassword> historyPassword) {
        this.historyPassword.addAll(historyPassword);
    }

    public ObservableList<HistoryPassword> getHistoryPassword() {
        return historyPassword;
    }



    public void setUrl(String url) {
        this.url.set(url);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setUrl(StringProperty url) {
        this.url = url;
    }

    public StringProperty getUrl() {
        return url;
    }
}
