package com.andre.dojo.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class HistoryPassword implements Serializable {
    public StringProperty id_acoount;
    public StringProperty waktuUpdate;
    public StringProperty passwordSebelumnya;
    public StringProperty passwordBaru;

    public HistoryPassword(String id_account, String waktuUpdate, String passwordSebelumnya, String passwordBaru){
        this.id_acoount = new SimpleStringProperty(id_account);
        this.waktuUpdate = new SimpleStringProperty(waktuUpdate);
        this.passwordSebelumnya = new SimpleStringProperty(passwordSebelumnya);
        this.passwordBaru = new SimpleStringProperty(passwordBaru);
    }

    @Override
    public String toString(){
        return id_acoount.get()+"<>"+waktuUpdate.get()+"<>"+passwordSebelumnya.get()+"<>"+passwordBaru.get();
    }
    public static HistoryPassword fromString(String historyString){
        String[] parts = historyString.split("<>");
        String id = parts[0];
        String waktu = parts[1];
        String pwBefore = parts[2];
        String pwNow = parts[3];
        return new HistoryPassword(id, waktu, pwBefore, pwNow);
    }

}
