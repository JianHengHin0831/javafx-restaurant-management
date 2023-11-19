package com.example.coursework1;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.coursework1.Common.switchScene;

public class Restaurant extends Application{
    private static User user;

    public static User getUser() {
        return user;
    }

    public void start(Stage primaryStage) {
        AnchorPane pane = new AnchorPane();
        var scene = new Scene(pane);

        //background image
        Image backgroundImage = new Image("img_8.png");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        //make the background image fit to the screen size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        backgroundImageView.setFitHeight(screenBounds.getHeight());
        backgroundImageView.setFitWidth(screenBounds.getWidth());
        pane.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true))));

        //sign in box
        GridPane signIn = signIn(primaryStage,scene);
        signIn.setStyle("-fx-background-color: rgba(192, 192, 192, 0.7);");
        AnchorPane.setRightAnchor(signIn,  pane.getWidth() / 2.0 - signIn.getWidth() / 2.0);
        AnchorPane.setTopAnchor(signIn, pane.getHeight() / 2.0 - signIn.getHeight() / 2.0);

        //bar(back,next)
        HBox bar = Common.sceneBasic(primaryStage,null);
        pane.getChildren().addAll(signIn,bar);
        AnchorPane.setTopAnchor(bar,10.0);
        AnchorPane.setLeftAnchor(bar,10.0);

        //let the login box at the middle
        pane.widthProperty().addListener((obs, oldVal, newVal) -> AnchorPane.setRightAnchor(signIn, pane.getWidth() / 2.0 - signIn.getWidth() / 2.0));
        pane.heightProperty().addListener((obs, oldVal, newVal) -> AnchorPane.setTopAnchor(signIn, pane.getHeight() / 2.0 - signIn.getHeight() / 2.0));

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Restaurant System");
        primaryStage.setFullScreen(true);
    }

    static GridPane signIn(Stage stage1,Scene scene){
        GridPane signInBox = Common.setGridPane(30,10,50,700,500,"-fx-background-color: #FFFFFF;",null);

        //title
        Text title=Common.setText("Sign In","Verdana",40,Color.BLACK);

        //username,password field
        Label usernameLabel = Common.setLabel("Username",30,Color.BLACK,null,null);
        Label passwordLabel = Common.setLabel("Password",30,Color.BLACK,null,null);
        Label resultLabel = Common.setLabel("",20,Color.BLACK,null,null);

        TextField usernameField = Common.setTextField("","",150,0,true);
        PasswordField passwordField=Common.setPasswordField("",150,0);

        //login button
        Button loginBtn = Common.setButton("login",new ImageView("img_6.png"));
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginBtn.setPrefSize(650,70);
        loginBtn.setOnAction(e-> {
            try {
                checkLogin(usernameField,passwordField,resultLabel,stage1,scene);
            } catch (Catch.NullRSException ex) {
                throw new RuntimeException(ex);
            }
        });

        //register label
        Label registerLabel = new Label("No account? register here ");
        registerLabel.setOnMouseClicked(mouseEvent -> Common.register(stage1,"c"));
        registerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        registerLabel.setStyle("-fx-text-fill: red; -fx-underline: true;");

        // grid.add(Node, colIndex, rowIndex, colSpan, rowSpan):
        signInBox.add(title, 2, 0,3, 1);
        signInBox.add(usernameLabel, 0, 1,2,1);
        signInBox.add(passwordLabel, 0, 2,2,1);
        signInBox.add(usernameField, 2, 1);
        signInBox.add(passwordField, 2, 2);
        signInBox.add(loginBtn,0,3,3,1);
        signInBox.add(resultLabel,0,4,3,1);
        signInBox.add(registerLabel,0,5,3,1);


        //column constraint
        ColumnConstraints column1 = new ColumnConstraints(80);
        ColumnConstraints column2 = new ColumnConstraints(150,150,Double.POSITIVE_INFINITY);
        column2.setHgrow(Priority.ALWAYS);
        signInBox.getColumnConstraints().addAll(column1, column2);
    return signInBox;

    }
    static void checkLogin(TextField usernameField,PasswordField passwordField, Label resultLabel,Stage stage1, Scene scene) throws Catch.NullRSException {
        ResultSet resultSet= Database.readDatabase("Select * FROM user");
        String passwordInput = passwordField.getText();
        String usernameInput = usernameField.getText();
        try {
            boolean isTrue=false;
            if (resultSet != null) {
                while (resultSet.next()) { //read line by line
                    String name = resultSet.getString("name");
                    String password=resultSet.getString("password");
                    String role=resultSet.getString("role");
                    if(name.equals(usernameInput)&&password.equals(passwordInput)){
                        isTrue=true;//check if the user is registered
                        user = new User(name,password,role);
                        resultLabel.setText("Successfully Sign in");
                        if(role.equals("a")){
                            //switch to admin page
                            switchScene(stage1,AdminPage.adminPage(stage1, scene));
                        } else if (role.equals("s")) {
                            //switch to staff page
                            switchScene(stage1,StaffPage.staffPage(stage1,scene));
                        }else{
                            //switch to client page
                            switchScene(stage1,ClientPage.clientPage(stage1,scene));
                        }
                    }
                }
            }
            if(!isTrue){
                //the user not register or wrong password
                resultLabel.setText("Wrong password or Username!");
            }
        }catch (NullPointerException e){
            throw  new Catch.NullRSException("Sorry, database have nothing");
        } catch (SQLException e){
            throw new Catch.NullRSException("Cannot connect to database!");
        } catch (Exception e) {
            System.out.println("Sorry, something wrong happened");
        }
    }

    public static void main(String[] args) {
        launch();
    }



}
