package com.example.coursework1;

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.coursework1.Common.changeResultContent;
import static com.example.coursework1.Common.getFoodType;

public class ClientPage {
    private static final BorderPane bp = new BorderPane();

    static Scene clientPage(Stage primaryStage,Scene prevScene){

        //-------center----//
        VBox result = new VBox();

        ScrollPane scrollPane = Common.createScrollPane();
        scrollPane.setContent(result);
        getBp().setCenter(scrollPane);
        Scene dialogScene = new Scene(getBp());



        //-------top---------//
        VBox vBox=new VBox();
        HBox hBox = title(primaryStage,dialogScene,result);
        HBox bar= Common.sceneBasic(primaryStage, prevScene);
        vBox.getChildren().addAll(bar,hBox);
        getBp().setTop(vBox);

        //---------left-----//
        VBox menu = menu(primaryStage, false,result);
        //make the height of menu connected to scroll pane height
        menu.prefHeightProperty().bind(scrollPane.heightProperty());
        getBp().setLeft(menu);

        return dialogScene;
    }

    static HBox title(Stage stage1, Scene dialogScene,VBox result){
        HBox bar = new HBox();
        bar.prefWidthProperty().bind(stage1.widthProperty());

        //-----------button--------//
        Button mainPage = Common.setButton("McDonald's",new ImageView("img_1.png"));
        Button menuBtn = Common.setButton("menu",new ImageView("img_3.png"));
        Button offerBtn = Common.setButton("Offers",new ImageView("img_4.png"));
        Button myAccountBtn = Common.setButton("My Account", new ImageView("img.png"));
        Button supportBtn = Common.setButton("Support",new ImageView("img_5.png"));
        ArrayList<Button> buttonArr1 =new ArrayList<>(Arrays.asList(menuBtn,offerBtn,myAccountBtn,supportBtn));

        menuBtn.setOnAction(e->{
            result.getChildren().clear();
            getBp().setRight(null);
            getBp().setLeft(menu(stage1, false,result));
            switchTitleColour(menuBtn,buttonArr1);
        });


        myAccountBtn.setOnAction(e->{
            getBp().setRight(null);
            result.getChildren().clear();
            getBp().setLeft(myAccount(result));
            switchTitleColour(myAccountBtn,buttonArr1);
        });
        supportBtn.setOnAction(e->{
            getBp().setLeft(null);
            Common.changeResultContent(result,createSupportSection());
            getBp().setRight(null);
            switchTitleColour(supportBtn,buttonArr1);
        });

        offerBtn.setOnAction(e->{
            result.getChildren().clear();
            getBp().setLeft(menu(stage1, true,result));
            switchTitleColour(offerBtn,buttonArr1);

        });

        mainPage.setOnAction(e-> {
            result.getChildren().clear();
            getBp().setRight(null);
            getBp().setLeft(menu(stage1, false,result));
            switchTitleColour(new Button(),buttonArr1);
        });


        //--------------button properties-------------//
        Button[] buttonArr = {mainPage,menuBtn,offerBtn,myAccountBtn,supportBtn};
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double buttonWidth = screenWidth / buttonArr.length;
        for(var d: buttonArr){
            d.setTextFill(Color.WHITE);
            d.setPrefHeight(100);
            d.setPadding(new Insets(10,50,10,50));
            d.setStyle("-fx-border-color: black;-fx-background-color:#3d3d3d");
            d.setOnMouseEntered(mouseEvent -> d.setTextFill(Color.GRAY));
            d.setOnMouseExited(mouseEvent -> d.setTextFill(Color.WHITE));
            d.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            bar.getChildren().add(d);
            d.prefWidthProperty().bind(Bindings.createDoubleBinding(() -> buttonWidth, dialogScene.widthProperty()));
        }

        mainPage.setStyle("-fx-background-color:#c8161d");
        return bar;
    }
    public static void switchTitleColour(Button change,ArrayList<Button> original) {
        String ORIGINAL_STYLE = "-fx-background-color: #3d3d3d;";
        String CLICKED_STYLE = "-fx-background-color: #00B4FF;";
        change.setStyle(CLICKED_STYLE);
        for (Button button : original) {
            if (button != change) button.setStyle(ORIGINAL_STYLE);
        }
    }
    //buy list will be store in invoice
    private static final HashMap<Food,Integer> buyList = new HashMap<>();

    private static VBox createSupportSection() {
        VBox supportSection = new VBox(20);
        supportSection.setAlignment(Pos.CENTER);

        // Create the support title
        Label supportTitle = new Label("Support");
        supportTitle.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        supportTitle.setTextFill(Color.DARKBLUE);

        // Create the support form
        GridPane supportForm = createSupportForm();

        // Add the title and form to the support section
        supportSection.getChildren().addAll(supportTitle, supportForm);
        return supportSection;
    }

    private static GridPane createSupportForm() {
        GridPane supportForm = new GridPane();
        supportForm.setAlignment(Pos.CENTER);
        supportForm.setHgap(10);
        supportForm.setVgap(10);


        Label nameLabel = Common.setLabel("Name",40,Color.BLACK,null,null);
        TextField nameField = new TextField();

        Label emailLabel = Common.setLabel("Email",40,Color.BLACK,null,null);
        TextField emailField = new TextField();

        Label messageLabel = Common.setLabel("Message",40,Color.BLACK,null,null);
        TextArea messageArea = new TextArea();

        Label resultLabel = Common.setLabel(" " ,30,Color.RED,null,null);


        supportForm.add(nameLabel, 0, 0);
        supportForm.add(nameField, 1, 0);

        supportForm.add(emailLabel, 0, 1);
        supportForm.add(emailField, 1, 1);

        supportForm.add(messageLabel, 0, 2);
        supportForm.add(messageArea, 1, 2);

        supportForm.add(resultLabel,0,3,2,2);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setPrefSize(100,50);
        submitButton.setOnAction(e -> {

            String name = nameField.getText();
            String email = emailField.getText();
            String message = messageArea.getText();

            boolean isSend = processSupportRequest(name, email, message,resultLabel);

            if(isSend){
                nameField.clear();
                emailField.clear();
                messageArea.clear();
            }
        });

        supportForm.add(submitButton, 0, 5);

        return supportForm;
    }

    private static boolean processSupportRequest(String name, String email, String message,Label resultLabel) {
        if(!name.equals("") && !email.equals("") && !message.equals("")){
            String query = "INSERT INTO support (username, email, message) VALUES ('"+name+"', '"+email+"', '"+message+"');";
            Database.writeDatabase(query);
            resultLabel.setText("Successfully send to staff.\nWe will send you a email within 24 hours");
            return true;
        }else{
            resultLabel.setText("Please Fill in all the information!");
            return false;
        }
    }
    static VBox menu(Stage primaryStage, boolean isPromotion, VBox result){
        VBox vBox = new VBox();

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);

        // Apply the drop shadow effect to the VBox
        vBox.setEffect(dropShadow);
        //btn list
        ArrayList<Button> btnList = new ArrayList<>();

        //list all the food
        Button allFoodBtn = Common.beautifyButton("All",150,100,"yellow","16");
        btnList.add(allFoodBtn);
        allFoodBtn.setOnAction(ex-> {
            try {
                Common.switchColour(allFoodBtn,btnList);
                Common.changeResultContent(result,AdminPage.foodChoice("",primaryStage,"c",new TextField(),isPromotion));
            } catch (Catch.NullRSException e) {
                throw new RuntimeException(e);
            }
        });
        vBox.getChildren().add(allFoodBtn);

        for(var d: getFoodType()){
            //list specific food
            Button foodBtn = Common.beautifyButton(d,150,100,"yellow","16");
            btnList.add(foodBtn);
            foodBtn.setOnAction(ex-> {
                try {
                    Common.switchColour(foodBtn,btnList);
                    Common.changeResultContent(result,AdminPage.foodChoice(d,primaryStage,"c",new TextField(),isPromotion));
                } catch (Catch.NullRSException e) {
                    throw new RuntimeException(e);
                }
            });
            vBox.getChildren().add(foodBtn);
        }

        //-----------check out-------------//
        Button checkOutBtn = Common.beautifyButton("Check Out",150,100,"yellow","16");
        btnList.add(checkOutBtn);
        checkOutBtn.setOnAction(e->{
            if(getBuyList().size()!=0){
                Common.switchColour(checkOutBtn,btnList);
                getBp().setLeft(null);
                getBp().setRight(null);
                Common.changeResultContent(result,checkOut());
            }
        });

        Button logOut = Common.beautifyButton("LOG OUT",150,100,"yellow","16");
        logOut.setOnAction(e->primaryStage.close());

        vBox.getChildren().addAll(checkOutBtn,logOut);
        vBox.setBackground(Background.fill(Color.YELLOW));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
    static VBox myAccount(VBox result){
        VBox vBox = new VBox();
        vBox.setBackground(Background.fill(Color.YELLOW));
        vBox.setAlignment(Pos.CENTER);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);

        // Apply the drop shadow effect to the VBox
        vBox.setEffect(dropShadow);

        //-------------change password---------//
        Button changePassword = Common.beautifyButton("CHANGE PASSWORD",300,100,"yellow","16");
        Button viewHistory = Common.beautifyButton("VIEW HISTORY",300,100,"yellow","16");
        changePassword.setOnAction(e->{
            Common.switchColour(changePassword,new ArrayList<>(List.of(viewHistory)));
            changeResultContent(result,changePassword());
        });
        result.setAlignment(Pos.TOP_LEFT);

        //----------view history-------------//
        viewHistory.setOnAction(e->{
            changeResultContent(result,viewHistory());
            Common.switchColour(viewHistory,new ArrayList<>(List.of(changePassword)));
        });

        vBox.getChildren().addAll(changePassword,viewHistory);
        return vBox;
    }
    static GridPane changePassword(){
        GridPane grid = Common.setGridPane(10, 100, 100, 0, 0, "", null);
        grid.setPadding(new Insets(30, 700, 30, 30));

        Label regLabel = Common.setLabel("Change Password", 45, Color.BLACK, null, null);
        regLabel.setTextFill(Color.RED);

        //------------text field and label---------------//
        Label usernameLabel = Common.setLabel("Username", 40, Color.BLACK, null, null);
        Label oldPasswordLabel = Common.setLabel("Original Password", 40, Color.BLACK, null, null);
        Label passwordLabel = Common.setLabel("New Password", 40, Color.BLACK, null, null);
        Label passwordLabel1 = Common.setLabel("Type Again", 40, Color.BLACK, null, null);
        Label resultLabel = Common.setLabel("", 30, Color.BLACK, null, null);

        TextField usernameField = Common.setTextField(Restaurant.getUser().getName(), "", 0, 0, false);
        PasswordField oldPasswordField = new PasswordField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordField1 = new PasswordField();

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints(150);
        ColumnConstraints column3 = new ColumnConstraints(150);

        column2.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(column1, column2, column3);

        Button changeBtn = Common.setButton("Change Password", new ImageView("img_7.png"));
        changeBtn.setPrefHeight(50);

// grid.add(Node, colIndex, rowIndex, colSpan, rowSpan):
        grid.add(regLabel, 0, 0, 3, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(oldPasswordLabel, 0, 2);
        grid.add(passwordLabel, 0, 3);
        grid.add(passwordLabel1, 0, 4);
        grid.add(usernameField, 1, 1, 2, 1);
        grid.add(oldPasswordField, 1, 2, 2, 1);
        grid.add(passwordField, 1, 3, 2, 1);
        grid.add(passwordField1, 1, 4, 2, 1);
        grid.add(changeBtn, 0, 5, 2, 1);
        grid.add(resultLabel, 0, 6, 3, 1);

        changeBtn.setOnAction(e -> changePasswordAction(usernameField, oldPasswordField, passwordField, passwordField1, resultLabel));
        return grid;
    }

    static void changePasswordAction(TextField usernameField,PasswordField oldPasswordField,PasswordField passwordField,PasswordField passwordField1,Label resultLabel){
        //collect data of the user from database
        ResultSet resultSet= Database.readDatabase("SELECT * from user WHERE name='"+Restaurant.getUser().getName()+"'");
        String password = null;
        try {
            if (resultSet != null) {
                while ((resultSet.next())){
                    password = resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            //check if old password is same as the password in database and new password=new password1
            if (password != null) {
                if(password.equals(oldPasswordField.getText()) && passwordField.getText().equals(passwordField1.getText())){
                    String passwordFieldText = passwordField.getText();
                    String usernameFieldText = usernameField.getText();
                    updatePassword(passwordFieldText,usernameFieldText);
                    resultLabel.setText("Your password is successfully changed");
                }else{
                    if(!password.equals(oldPasswordField.getText())){
                        resultLabel.setText("Your old password is incorrect");
                    }
                    else{
                        resultLabel.setText("Your confirm password is wrong");
                    }
                    oldPasswordField.setText("");
                    passwordField.setText("");
                    passwordField1.setText("");
                }
            }
        }catch(Exception e){
            System.out.println("something error");
        }
    }
    static boolean updatePassword(String password,String username){
        try {
            String query = "UPDATE `user` SET `password` = '" + password + "' WHERE `user`.`name` = '" + username + "'";
            Database.writeDatabase(query);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    static VBox viewHistory(){
        VBox history = new VBox();
        //select all the order that ordered by user and already completed order and the table order by user_id
        ResultSet resultSet= Database.readDatabase("SELECT o.`datetime`,of.food_name,of.quantity,of.order_id FROM `order` o INNER JOIN `order_food` of ON o.order_id=of.order_id WHERE o.user_name='"+Restaurant.getUser().getName()+"' and o.isCompleted=1 ORDER BY of.order_id");
        try {
            String prevDateTime="";
            //count the order num
            int round=0;
            //count the row
            int num = 1;
            //record the order detail
            GridPane grid = null;
            //first order is brown color bg,second is green and cycle it when change order id
            ArrayList<Color> colorPicker = new ArrayList<>(Arrays.asList(Color.rgb(200, 200, 200), Color.rgb(230, 230, 230)));
            if (resultSet != null) {
                while (resultSet.next()) { //read line by line
                    String datetime = resultSet.getString("datetime");
                    String food_name= resultSet.getString("food_name");
                    int quantity  =resultSet.getInt("quantity");

                    if (!prevDateTime.equals(datetime)) {
                        //ensure this is not the first order
                        if(grid!=null){
                            //prev datetime is not equal to current datetime mean that this is different order, and we add this order grid pane into vbox
                            history.getChildren().add(grid);
                        }
                        //create a new grid pane when start to print new order
                        grid = Common.setGridPane(30,100,30,0,0,"",colorPicker.get(round%2));
                        grid.setPadding(new Insets(30,900,30,30));
                        grid.setBackground(Background.fill(colorPicker.get(round%2)));
                        num = 1;
                        round++;
                        Label datetimeLabel = Common.setLabel("Datetime: "+datetime,30,Color.BLACK,null,null);
                        history.getChildren().add(datetimeLabel);
                        grid.add(datetimeLabel,0,0,2,1);
                    }
                    prevDateTime=datetime;
                    if (grid != null) {
                        grid.add(Common.setLabel(food_name,30,Color.BLACK,null,null),0,num);
                        grid.add(Common.setLabel(Integer.toString(quantity),30,Color.BLACK,null,null),1,num);
                    }
                    num++;
                }
            }
            else{
                throw new Catch.NullHMException("You didn't buy anything!");
            }
            history.getChildren().add(grid);

        } catch (Exception a) {
            System.out.println("Sorry, read binary failed");
        }

        return history;
    }

    static GridPane checkOut(){
        String username = Restaurant.getUser().getName();
        //time now
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        //find the largest order id and add 1
        ResultSet resultSet= Database.readDatabase("SELECT * FROM `order` ORDER BY order_id DESC LIMIT 1");
        int id = 0;
        try {
            if (resultSet != null) {
                while (resultSet.next()){
                    id = resultSet.getInt("order_id")+1;
                }
            }else{
                throw new Catch.NullRSException("Your database don't have anything");
            }
            String query = "INSERT INTO `order` (`order_id`, `user_name`,`datetime`) VALUES ('"+id+"', '"+username+"','"+timeStamp+"');";
            Database.writeDatabase(query);
        } catch (SQLException | Catch.NullRSException e) {
            throw new RuntimeException(e);
        }

        return invoice(id,"checkOut");

    }
    static GridPane invoice(int order_id, String type){
        GridPane grid = Common.setGridPane(10,20,10,0,0,"",null);

        grid.add(new Label("Invoice"),1,0);
        grid.add(new Label("Item"), 0, 1);
        grid.add(new Label("Quantity"), 1, 1);
        grid.add(new Label("Price"), 2, 1);

        for (Node node : grid.getChildren()) {
            if (node instanceof Label) {
                // Set the font size and color for the Label node
                ((Label) node).setFont(new Font("Arial", 20));
                ((Label) node).setTextFill(Color.RED);
            }
        }

        int i =0;
        double sum=0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        for (Food f: getBuyList().keySet()) {
            //print invoice
            if(getBuyList().get(f)!=0){
                double price= f.getPrice()*getBuyList().get(f);
                grid.add(new Label(f.getName()), 0, i+2);
                grid.add(new Label(Integer.toString(getBuyList().get(f))), 1, i+2);
                grid.add(new Label("$" + decimalFormat.format(price)), 2, i+2);
                sum +=price;
                i++;
                if(type.equals("checkOut")) {
                    //update database
                    int currentStock = f.getStock() - getBuyList().get(f);
                    String query = "UPDATE `food` SET `stock` = '" + currentStock + "' WHERE `food`.`name` = '" + f.getName() + "';";
                    Database.writeDatabase(query);

                    int currentSales = f.getSales() + getBuyList().get(f);
                    query = "UPDATE `food` SET `sales` = '" + currentSales + "' WHERE `food`.`name` = '" + f.getName() + "';";
                    Database.writeDatabase(query);

                    query = "INSERT INTO `order_food` (`order_id`, `food_name`,`quantity`) VALUES ('" + order_id + "', '" + f.getName() + "','" + getBuyList().get(f) + "');";
                    Database.writeDatabase(query);
                }
            }
        }
        grid.add(new Label("Total:"), 1, i+2);
        grid.add(new Label("$" + decimalFormat.format(sum)), 2, i+2);
        if(type.equals("checkOut")){
            //modify the grid pane properties if check out
            grid.add(new Label("Thank you for your ordering!"),0,i+3);
            grid.setHgap(50);
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(100);
            grid.setVgap(50);
            for (Node node : grid.getChildren()) {
                if (node instanceof Label) {
                    // Set the font size and color for the Label node
                    ((Label) node).setFont(new Font("Arial", 60));
                    ((Label) node).setTextFill(Color.RED);
                }
            }
            buyList.clear();

        }
        return grid;
    }
    static void ClientBuyFood(Stage stage1,Food food){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage1);
        dialog.setTitle(food.getName());

        VBox modify = new VBox();
        int value = 0;
        Food used = food;
        //check if the food is in the buyList
        for(Food f: getBuyList().keySet()){
            if(f.getName().equals(food.getName())){
                value= getBuyList().get(f);
                used = f;
            }
        }

        //quantity label
        Label quantityLabel = Common.setLabel("Quantity: ",20,Color.BLACK,null,null);
        TextField textField=Common.setTextField(Integer.toString(value),"",50,0,false);

        HBox quantity = Common.integerBox( food.getStock(), textField);
        Button orderBtn =  Common.beautifyButton("Order",300,50,"yellow","25");
        Food finalUsed = used;
        orderBtn.setOnAction(e->{
            //put the food and quantity into array
            getBuyList().put(finalUsed,Integer.valueOf(textField.getText()));
            //print the invoice at the right
            getBp().setRight(ClientPage.invoice(0,"buy"));
            dialog.close();
        });

        modify.getChildren().addAll(quantityLabel,quantity,orderBtn);
        Scene dialogScene = new Scene(modify,300,200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public static BorderPane getBp() {
        return bp;
    }

    public static HashMap<Food, Integer> getBuyList() {
        return buyList;
    }
}
