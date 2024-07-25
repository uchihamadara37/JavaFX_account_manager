package com.andre.dojo.Utils;

import com.andre.dojo.Models.Account;
import com.andre.dojo.Models.HistoryPassword;
import com.andre.dojo.javafx_contact_manager.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class ObjectSaver implements Serializable{

    public static String filenameAddress;
    public static String fileCacheAddress;

    public static void SaveAccount(ObservableList<Account> allAkun) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filenameAddress))) {
            StringBuilder str = new StringBuilder();
            int yk = 1;
            for (Account account : allAkun) {

                str.append(account.toString());
                if (!account.getHistoryPassword().isEmpty()){

                    str.append("#");
                    int x = 1;
                    int size = account.getHistoryPassword().size();
                    for (HistoryPassword h : account.getHistoryPassword()){
                        str.append(h.toString());
                        if (x < size){
                            str.append(",");
                        }
                        x++;
                    }
                }
                if (yk < allAkun.size()){
                    str.append("@");
                }
                yk++;
            }
            // akun#histo,history@akun#histo,histo
            String enkripsi = Base64.getEncoder().encodeToString(str.toString().getBytes());
            writer.write(enkripsi);
            writer.newLine();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static Pair<ObservableList<Account>, Integer> retrieveAccount(){
        ObservableList<Account> accounts = null;
        int id_max = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filenameAddress))) {
            String line;
            if ((line = reader.readLine()) != null){

                // akun#histo,history@akun#histo,histo
                accounts = FXCollections.observableArrayList();

                String encodeEnkripsi = new String(Base64.getDecoder().decode(line));

                String[] akuns = encodeEnkripsi.split("@");

                for (String akunDanHist : akuns){ // loop akun
                    String[] split2 = akunDanHist.split("#");
                    Account akun = Account.fromString(split2[0]);

                    if (split2.length == 2){
                        String[] histories = split2[1].split(",");
                        for (String his : histories){
                            akun.addHistoryPassword(HistoryPassword.fromString(his));
                        }
                    }
                    accounts.add(akun);
                    // mencari id max
                    if (akun.getId().get() > id_max){
                        id_max = akun.getId().get();
                    }
                }
            }

        }catch (IOException e){
            System.out.println("error baca file mase");
            throw new RuntimeException(e);
        }
        return new Pair<>(accounts, id_max);
    }

    public static boolean saveFileCache(String fileNameAddress){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./fileCache.txt"))) {
            String enkripsi = Base64.getEncoder().encodeToString(fileNameAddress.getBytes());
            writer.write(enkripsi);
            writer.newLine();
            return true;
        }catch (IOException e1){
            System.out.println("error save fileCache mase \n "+e1);
            return false;
//            throw new RuntimeException(e1);
        }
    }
    public static String readFileCache(){
        try (BufferedReader reader = new BufferedReader(new FileReader("./fileCache.txt"))) {
            String line;
            if ((line = reader.readLine()) != null){
                return new String(Base64.getDecoder().decode(line));
            }else{
                System.out.println("isi file kosong");
                return "";
            }
        }catch (IOException e1){
            System.out.println("error save fileCache mase \n "+e1);
            return "";
//            throw new RuntimeException(e1);
        }
    }

    public static File chooseFileSave() {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        fileChooser.setInitialDirectory(getUserDocumentsDirectory());
        fileChooser.setInitialFileName("akun.txt");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        return fileChooser.showSaveDialog(HelloApplication.getStage());
    }
    public static File chooseFileSave(String addressRecentFile, String fileName) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        fileChooser.setInitialFileName(fileName);
        fileChooser.setInitialDirectory(new File(addressRecentFile));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        return fileChooser.showSaveDialog(HelloApplication.getStage());
    }

    public static File chooseFileSelectOpen() {

        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        fileChooser.setInitialDirectory(getUserDocumentsDirectory());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        return fileChooser.showOpenDialog(HelloApplication.getStage());
    }
    public static File getUserDocumentsDirectory() {
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // Windows
            return new File(userHome + "\\Documents");
        } else if (os.contains("mac")) {
            // macOS
            return new File(userHome + "/Documents");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("unix")) {
            // Linux/Unix
            return new File(userHome + "/Documents");
        } else {
            // Fallback ke user home jika OS tidak dikenali
            return new File(userHome);
        }
    }

}
