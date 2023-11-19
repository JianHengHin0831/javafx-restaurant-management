package com.example.coursework1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Common {
    private static final Stack<Scene> sceneHistory = new Stack<>();
    static final Stack<Scene> forwardStack = new Stack<>();
    private static boolean before=false;

    static HBox sceneBasic(Stage primaryStage,Scene prevScene){
        if(prevScene!=null){
            forwardStack.empty();
            sceneHistory.push(prevScene);
        }
        Button backButton =Common.beautifyButton("←Back",150,30,"white","10");
        Button forwardButton = Common.beautifyButton("Forward→",150,30,"white","10");


        backButton.setOnAction(e -> {
            if(!sceneHistory.isEmpty()) {
                before=true;
                Scene previousScene = sceneHistory.pop();
                forwardButton.setTextFill(Color.WHITE);
                forwardStack.push(previousScene);

                primaryStage.setScene(previousScene);
                primaryStage.setFullScreen(true);
                System.out.println("back");
            }
        });
        forwardButton.setOnAction(e -> {
            if(!forwardStack.isEmpty() ) {
                Scene nextScene = forwardStack.pop();
                sceneHistory.push(nextScene);
                primaryStage.setScene(nextScene);
                primaryStage.setFullScreen(true);
            } else if (before) {
                Common.switchColour(backButton,new ArrayList<>(Arrays.asList(forwardButton)));
                if(Restaurant.getUser().getRole().equals("a")){
                    switchScene(primaryStage,AdminPage.adminPage(primaryStage, prevScene));
                } else if (Restaurant.getUser().getRole().equals("s")) {
                    //switch to staff page
                    switchScene(primaryStage,StaffPage.staffPage(primaryStage,prevScene));
                }else{
                    //switch to client page
                    switchScene(primaryStage,ClientPage.clientPage(primaryStage,prevScene));
                };
                primaryStage.setFullScreen(true);
            }
        });
        HBox hbox = new HBox();
        hbox.getChildren().addAll(backButton,forwardButton);
        return hbox;
    }
    private static final ArrayList<String> foodType = new ArrayList<>(Arrays.asList("burger","drinks","dessert","fried","set","others"));
    static Button setButton(String text1, ImageView imageView1){
        Button button = new Button(text1);
        button.setShape(new Rectangle(12,12));
        //button.setPrefHeight(120);

        Text text = new Text(button.getText());
        text.setFont(Font.font("Arial Black", FontWeight.BOLD,30));

        imageView1.setFitHeight(text.getBoundsInLocal().getHeight());
        imageView1.setFitWidth(text.getBoundsInLocal().getWidth()/2);
        button.setTextFill(Color.BLACK);
        button.setGraphic(imageView1);
        DropShadow shadow = new DropShadow();
        button.setEffect(shadow);

        return button;
    }
    static TextField setTextField(String text,String promptText,int width,int height,boolean editable){
        TextField textField=new TextField(text);
        textField.setPromptText(promptText);
        if(width!=0)textField.setPrefWidth(width);
        if(height!=0)textField.setPrefHeight(height);
        textField.setEditable(editable);
        return textField;
    }
    static PasswordField setPasswordField(String promptText,int width,int height){
        PasswordField passwordField=new PasswordField();
        passwordField.setPromptText(promptText);
        if(width!=0)passwordField.setPrefWidth(width);
        if(height!=0)passwordField.setPrefHeight(height);
        return passwordField;
    }
    static Text setText(String text, String font, int fontSize,Color color){
        Text text1=new Text(text);
        text1.setFont(Font.font(font, FontWeight.BOLD,fontSize));
        text1.setFill(color);
        return text1;
    }
    static GridPane setGridPane(int insets,int hGap,int vGap,int width,int height, String bgColour,Color bgColor){
        GridPane grid = new GridPane();
        if(insets!=0)grid.setPadding(new Insets(insets));
        grid.setHgap(hGap);
        grid.setVgap(vGap);
        grid.setAlignment(Pos.CENTER_RIGHT);
        if(width!=0)grid.setPrefWidth(width);
        if(height!=0)grid.setPrefHeight(height);
        if(bgColour!="")grid.setStyle(bgColour);
        else if (bgColor!=null) grid.setBackground(Background.fill(bgColor));
        return grid;
    }
    static void switchColour(Button change,ArrayList<Button> original){
        String ORIGINAL_STYLE = "-fx-background-color: yellow;";
        String CLICKED_STYLE = "-fx-background-color: #DAA520;";
        change.setStyle(CLICKED_STYLE);
        for(Button button:original){
            if(button!=change) button.setStyle(ORIGINAL_STYLE);
        }
    }

    static Button beautifyButton(String text1, int width,int height, String colour, String fontSize){
        Button button1 = new Button(text1);
        button1.setFont(Font.font("Verdana", FontWeight.BOLD, Integer.valueOf(fontSize)));
        button1.setTextFill(Color.BLACK);
        button1.setStyle("-fx-background-color:"+colour+"; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0.0, 0, 1); ");
        button1.setPrefWidth(width);
        button1.setPrefHeight(height);
        DropShadow shadow = new DropShadow();
        button1.setEffect(shadow);
        button1.setOnMouseEntered(mouseEvent -> button1.setTextFill(Color.GRAY));
        button1.setOnMouseExited(mouseEvent -> button1.setTextFill(Color.BLACK));
        return button1;
    }
    static  Label setLabel(String text,int fontSize,Color textColor,Color backGroundColor,String bgColour){
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        label.setTextFill(textColor);
        if(backGroundColor != null){
            label.setBackground(Background.fill(backGroundColor));
        } else if (bgColour!=null) {
            label.setStyle("-fx-background-color:"+bgColour+";");
        }
        return label;
    }
    static ImageView createImageView(String img,int width, int height){
        Image image = new Image(img);
        ImageView imageView = new ImageView(image);
        if(width!=0)imageView.setFitWidth(width);
        if(height!=0)imageView.setFitHeight(height);
        return imageView;
    }
    static ScrollPane createScrollPane(){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }
    static void register(Stage stage1, String role) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage1);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Label regLabel = new Label("Please provide your register details");
        regLabel.setTextFill(Color.WHITE);
        regLabel.setStyle("-fx-font-size: 18px;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setStyle("-fx-font-size: 14px;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.WHITE);
        passwordLabel.setStyle("-fx-font-size: 14px;");

        Label passwordLabel1 = new Label("Confirm Password:");
        passwordLabel1.setTextFill(Color.WHITE);
        passwordLabel1.setStyle("-fx-font-size: 14px;");

        Label resultLabel = new Label("");
        resultLabel.setTextFill(Color.WHITE);
        resultLabel.setStyle("-fx-font-size: 14px;");

        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-prompt-text-fill: #9e9e9e;");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-prompt-text-fill: #9e9e9e;");
        PasswordField passwordField1 = new PasswordField();
        passwordField1.setStyle("-fx-prompt-text-fill: #9e9e9e;");

        Button registerBtn = Common.setButton("Register", new ImageView("img_7.png"));
        registerBtn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14px;");
        registerBtn.setPrefWidth(180);

        // grid.add(Node, colIndex, rowIndex, colSpan, rowSpan):
        grid.add(regLabel, 0, 0, 2, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordLabel1, 0, 3);
        grid.add(usernameField, 1, 1);
        grid.add(passwordField, 1, 2);
        grid.add(passwordField1, 1, 3);
        grid.add(registerBtn, 0, 4, 2, 1);
        grid.add(resultLabel, 0, 5, 2, 1);

        ColumnConstraints column1 = new ColumnConstraints(120);
        ColumnConstraints column2 = new ColumnConstraints(200, 200, Double.POSITIVE_INFINITY);
        column2.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(column1, column2);

        BackgroundFill backgroundFill = new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(33, 150, 243)),
                        new Stop(1, Color.rgb(41, 98, 255))
                ),
                CornerRadii.EMPTY,
                Insets.EMPTY
        );
        grid.setBackground(new Background(backgroundFill));

        registerBtn.setOnAction(e->{
            if(!usernameField.getText().equals("") && !passwordField.getText().equals(""))
            addUser(usernameField,passwordField,passwordField1,resultLabel,role,"add",dialog);
        });

        Scene dialogScene = new Scene(grid, 340, 240);
        dialogScene.setFill(Color.rgb(66, 66, 66));
        dialog.setScene(dialogScene);
        dialog.show();
    }

    static void addUser(TextField usernameField,TextField passwordField,TextField passwordField1,Label resultLabel,String role,String status,Stage stage1){
        if(!passwordField1.getText().equals(passwordField.getText())) {
            resultLabel.setText("Password you enter is not same");
        }else{
            ResultSet resultSet= Database.readDatabase("Select * FROM user");
            String passwordInput = passwordField.getText();
            String usernameInput = usernameField.getText();

            try {
                usernameField.setText("");
                passwordField.setText("");
                passwordField1.setText("");
                boolean isCollision=false;
                while (resultSet.next()) { //read line by line
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    if (name.equals(usernameInput) && password.equals(passwordInput)) {
                        resultLabel.setText("Sorry, please try another username or password");
                        isCollision=true;
                        break;
                    }
                }
                if(!isCollision){
                    try {
                        String query = null;
                        if(status.equals("add")) {
                            query = "INSERT INTO `user` (`name`, `password`, `role`) VALUES ('" + usernameInput + "', '" + passwordInput + "', '" + role + "');";
                            if(role=="c"){
                                stage1.close();
                            }
                        }
                        else if(status.equals("edit")){
                            query = "UPDATE `user` SET `password` = '"+passwordInput+"' WHERE `user`.`name` = '"+usernameInput+"'";
                        }

                        Database.writeDatabase(query);
                        resultLabel.setText("Successfully Add!");

                    }catch (Exception ex){
                        System.out.println("error");
                    }
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    static void switchScene(Stage stage1, Scene scene){
        stage1.setScene(scene);
        stage1.setFullScreen(true);
    }
    static HBox integerBox(int stock,TextField textField){
        textField.setPrefSize(200,50);
        Button plusButton = Common.beautifyButton("+",50,50,"yellow","25");
        plusButton.setOnAction(event -> {
            int quantity=Integer.valueOf(textField.getText());
            quantity++;
            textField.setText(Integer.toString(quantity));
            if(quantity>=stock){
                plusButton.setDisable(true);
            }
        });
        Button minusButton = Common.beautifyButton("-",50,50,"yellow","25");
        minusButton.setOnAction(event -> {
            int quantity=Integer.valueOf(textField.getText());
            quantity--;
            textField.setText(Integer.toString(quantity));
            if(quantity<=0){
                minusButton.setDisable(true);
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(minusButton,textField,plusButton);
        return buttonBox;
    }

    public static ArrayList<String> getFoodType() {
        return foodType;
    }
    static void changeResultContent(Pane result,Pane content){
        result.getChildren().clear();
        result.getChildren().add(content);
    }
}
