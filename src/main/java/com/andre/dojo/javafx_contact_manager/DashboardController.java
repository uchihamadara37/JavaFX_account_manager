package com.andre.dojo.javafx_contact_manager;

import com.andre.dojo.Models.Account;
import com.andre.dojo.Models.AnimatedHoverButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardController implements Initializable {
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

    @FXML
    private Button tombolData;
    @FXML
    private Button tombolChange;
    @FXML
    private Button tombolExport;
    @FXML
    private HBox hboxTombolData;
    @FXML
    private HBox hboxTombolChange;
    @FXML
    private HBox hboxTombolHistory;

    @FXML
    private Label countAkun;
    @FXML
    private Label countType;
    @FXML
    private Label nameUser;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox root;

    @FXML
    private Button btnFilterAll;
    private Line underline;

    @FXML
    private HBox hboxFilter;
    @FXML
    private ScrollPane scrollPaneFilter;

    List<String> jenis = new ArrayList<>();
    String stateFilter = "All";
    List<VBox> vboxes = new ArrayList<>();

    static ObservableList<Account> listView = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countAkun.setText(Integer.toString(HelloApplication.getListData().size()) + " Account");

        listView.addAll(HelloApplication.getListData());

        for (Account data : HelloApplication.getListData()){
            boolean sudahAda = false;
            for (String e : jenis){
                if (data.getAccountName().get().equals(e)){
                    sudahAda = true;
                    break;
                }
            }
            if (!sudahAda){
                jenis.add(data.getAccountName().get());
            }
        }

        nameUser.setText(HelloApplication.getUserNow().getUsername().get());


        // mengetes
        int jumlahJenis = 0;
        for (String e : jenis){
            jumlahJenis++;

            // Membuat VBox
            VBox vbox = new VBox();
            vbox.setPrefHeight(35.0);
            vbox.setPrefWidth(Region.USE_COMPUTED_SIZE);

            // Membuat Button
            Button btnFilterAll2 = new Button(e);
            btnFilterAll2.setId("btnFilterAll2");
            btnFilterAll2.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            btnFilterAll2.setMnemonicParsing(false);
            btnFilterAll2.setPrefHeight(33.0);
            btnFilterAll2.setPrefWidth(Region.USE_COMPUTED_SIZE);
            btnFilterAll2.getStyleClass().add("button-filter");
//            btnFilterAll2.setPadding(new Insets(10,10, 20, 10));
            VBox.setMargin(btnFilterAll2, new Insets(3, 0,-2,0));
//            btnFilterAll2.setStyle("-fx-line-height: 1;");


            // Membuat ImageView
//            String parentPath = "/resources/com/andre/dojo/javafx_contact_manager/icons/badge-check.png";
//            ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream("/icons/badge-check.png")));
//            iconView.setFitHeight(23.0);
//            iconView.setFitWidth(23.0);
//            iconView.setPreserveRatio(true);

            vbox.getChildren().add(btnFilterAll2);
            hboxFilter.getChildren().add(vbox);
//            System.out.println(e);
        }
        countType.setText(Integer.toString(jumlahJenis)+ " types ");

        // menambahkan state ke jenis
        jenis.add("All");
        jenis.add("Ascending");
        jenis.add("Descending");
        System.out.println("jumlah jenis : "+jumlahJenis);

        vboxes = hboxFilter.getChildren().stream()
                .filter(node -> node instanceof VBox)
                .map(node -> (VBox) node)
                .toList();


        tableView.setItems(listView);
        accountIdColumn.setCellValueFactory(data -> data.getValue().getId().asString());
        accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().getAccountName());
        accountURLColumn.setCellValueFactory(cellData -> cellData.getValue().getUrl());
        accountUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
        accountPasswordColumn.setCellValueFactory(cellData -> cellData.getValue().getPassword());

        tombolChange.setOnAction(e -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("change-view.fxml"));

                AnchorPane anchorPane2 = fxmlLoader.load();
                root.getChildren().set(1, anchorPane2);
                System.out.println("test test");

                hboxTombolData.getStyleClass().removeFirst();
                hboxTombolData.getStyleClass().add("side-button");
                hboxTombolChange.getStyleClass().removeFirst();
                hboxTombolChange.getStyleClass().add("side-button-active");
                hboxTombolHistory.getStyleClass().removeFirst();
                hboxTombolHistory.getStyleClass().add("side-button");

            }catch (IOException e1){
                System.out.println(e1.toString());
                e1.printStackTrace();
            }
        });

        tombolExport.setOnAction(e -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("export-view.fxml"));

                AnchorPane anchorPane2 = fxmlLoader.load();
                root.getChildren().set(1, anchorPane2);
//                System.out.println("test test");

                hboxTombolData.getStyleClass().removeFirst();
                hboxTombolData.getStyleClass().add("side-button");
                hboxTombolChange.getStyleClass().removeFirst();
                hboxTombolChange.getStyleClass().add("side-button");
                hboxTombolHistory.getStyleClass().removeFirst();
                hboxTombolHistory.getStyleClass().add("side-button-active");

            }catch (IOException e1){
                System.out.println(e1.toString());
                e1.printStackTrace();
            }
        });

        tombolData.setOnAction(e -> {
            root.getChildren().set(1, anchorPane);

            hboxTombolData.getStyleClass().removeFirst();
            hboxTombolData.getStyleClass().add("side-button-active");
            hboxTombolChange.getStyleClass().removeFirst();
            hboxTombolChange.getStyleClass().add("side-button");
            hboxTombolHistory.getStyleClass().removeFirst();
            hboxTombolHistory.getStyleClass().add("side-button");
        });

        scrollPaneFilter.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Sembunyikan scrollbar horizontal
        scrollPaneFilter.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Sembunyikan scrollbar vertikal
        scrollPaneFilter.setPannable(true); // Aktifkan panning (dragging dengan mouse atau touch)
        scrollPaneFilter.setFitToHeight(true); // Sesuaikan tinggi konten

        setupVBoxesFilter();
    }

    public void setupVBoxesFilter() {
        System.out.println("jumlah vbox = "+vboxes.size());
        for (VBox vbox : vboxes) {
//            vbox.setOnMouseEntered(e -> toggleUnderline(vbox));
//            vbox.setOnMouseExited(e -> toggleUnderline(vbox));
//            vbox.setOnMouseClicked(e -> changeStateFilter(vbox));
            Button button = (Button) vbox.getChildren().getFirst();
            button.setOnAction(e -> changeStateFilter(button));
//            System.out.println(vbox.getChildren().size());
        }

    }

    private void changeStateFilter(Button button) {
        System.out.println("jumlah state : "+vboxes.size());
        System.out.println("change state filter");

        stateFilter = button.getText();
        // mulai shorting sederhana
        List<Account> list2 = new ArrayList<>();

        if (Objects.equals(stateFilter, "Ascending")){
            listView.sort(Comparator.comparing(o -> o.getAccountName().get()));
        }else if(Objects.equals(stateFilter, "Descending")){
            listView.sort(Collections.reverseOrder(Comparator.comparing(o -> o.getAccountName().get())));
        }else if(Objects.equals(stateFilter, "All")){
            listView.sort(Comparator.comparingInt(o -> o.getId().get()));
        }else{
            listView.clear();
            for(Account account : HelloApplication.getListData()){
                if (Objects.equals(account.getAccountName().get(), stateFilter)){
                    listView.add(account);
                }else{
                    list2.add(account);
                }
            }
            listView.addAll(list2);
        }


        // mereset ulang table
        tableView.setItems(listView);
        accountIdColumn.setCellValueFactory(data -> data.getValue().getId().asString());
        accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().getAccountName());
        accountURLColumn.setCellValueFactory(cellData -> cellData.getValue().getUrl());
        accountUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
        accountPasswordColumn.setCellValueFactory(cellData -> cellData.getValue().getPassword());

        for (VBox vbox : vboxes){

            Button btn = (Button) vbox.getChildren().getFirst();
            if (Objects.equals(stateFilter, btn.getText())){
                btn.getStyleClass().removeFirst();
                btn.getStyleClass().add("button-filter-active");
            }else{
                btn.getStyleClass().removeAll("button-filter-active");
                btn.getStyleClass().add("button-filter");
            }
        }

    }

    public void toggleUnderline(VBox vboxHover){

        for (VBox vbox : vboxes) {
            Button button = (Button) vbox.getChildren().getFirst();

            if (!Objects.equals(button.getText(), stateFilter)){
                if (vbox == vboxHover){
                    // tambahkan garis
                    // jk sudah ada garis
                    // jk belum ada garis
                    if(vbox.getChildren().size() == 1){
                        Line line = new Line();
                        line.setStroke(Color.web("#1eddd0"));
                        line.setStrokeWidth(2);

                        line.setStartX(0);
                        line.setStartY(0);
                        line.setEndX(button.getWidth() - 2);
                        line.setEndY(0);
                        vbox.getChildren().add(1, line);
                    }else{
                        // toggle, jadinya dibalik aja
                        vbox.getChildren().remove(1);
                    }
                }else{
                    // menghilangkan garis
                    // jika garis sudah hilang
                    // jika garis belum hilang
                    if(vbox.getChildren().size() == 2){
                        vbox.getChildren().remove(1);
                    }
                }
            }

        }
    }



    private void animateUnderline(Button button, boolean show) {
        underline.setVisible(true);
        double endWidth = show ? button.getWidth() * 0.8 : 0;

        underline.setStartX(button.getLayoutX() + button.getWidth() / 2);
        underline.setStartY(button.getLayoutY() + button.getHeight() + 2);
        underline.setEndX(button.getLayoutX() + button.getWidth() / 2);
        underline.setEndY(button.getLayoutY() + button.getHeight() + 2);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(underline.startXProperty(), underline.getStartX()),
                        new KeyValue(underline.endXProperty(), underline.getEndX()),
                        new KeyValue(underline.opacityProperty(), underline.getOpacity())
                ),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(underline.startXProperty(), button.getLayoutX() + button.getWidth() / 2 - endWidth / 2),
                        new KeyValue(underline.endXProperty(), button.getLayoutX() + button.getWidth() / 2 + endWidth / 2),
                        new KeyValue(underline.opacityProperty(), show ? 1.0 : 0.0)
                )
        );
        timeline.play();
    }
}
