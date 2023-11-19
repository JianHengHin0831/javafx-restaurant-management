package com.example.coursework1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.coursework1.Common.changeResultContent;
import static com.example.coursework1.Common.getFoodType;

public class StaffPage {
    static Scene staffPage(Stage primaryStage,Scene prevScene){
        BorderPane page = new BorderPane();



        //create scene
        Scene dialogScene = new Scene(page);

        //top is combine with previous,next button, logo and the title
        VBox top = new VBox();

        HBox bar = Common.sceneBasic(primaryStage,prevScene);
        HBox logo = new HBox();
        logo.setStyle("-fx-background-color: #c30e0e;");
        ImageView imageView = Common.createImageView("img_1.png",150,120);
        Label label = Common.setLabel("McDonald's Staff System",90, Color.YELLOW,null,"#c30e0e");
        logo.getChildren().addAll(imageView,label);

        top.getChildren().addAll(bar,logo);

        //--------------result------------//
        VBox result = new VBox();

        //create scroll pane
        ScrollPane scrollPane =Common.createScrollPane();
        scrollPane.setContent(result);
        page.setCenter(scrollPane);


        //--------------title--------------------------//
        VBox title=new VBox();

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);

        // Apply the drop shadow effect to the VBox
        title.setEffect(dropShadow);
        title.setBackground(Background.fill(Color.YELLOW));
        title.setAlignment(Pos.CENTER);
        title.prefHeightProperty().bind(scrollPane.heightProperty());

        //title content
        Button orderBtn = Common.beautifyButton("ORDER",150,100,"yellow","16");
        Button stockBtn=  Common.beautifyButton("STOCK",150,100,"yellow","16");
        Button supportBtn=  Common.beautifyButton("SUPPORT",150,100,"yellow","16");
        Button logOut = Common.beautifyButton("LOG OUT",150,100,"yellow","16");
        orderBtn.setOnAction(e-> {
            try {
                Common.switchColour(orderBtn,new ArrayList<>(Arrays.asList(stockBtn,supportBtn)));
                changeResultContent(result,checkOrder(result));
            } catch (Catch.DBException ex) {
                throw new RuntimeException(ex);
            }
        });
        stockBtn.setOnAction(e->{
            Common.switchColour(stockBtn,new ArrayList<>(Arrays.asList(orderBtn,supportBtn)));
            changeResultContent(result,stockResult(primaryStage));
        });
        supportBtn.setOnAction(e->{
            Common.switchColour(supportBtn,new ArrayList<>(Arrays.asList(orderBtn,stockBtn)));
            changeResultContent(result,support(result));
        });

        logOut.setOnAction(e->primaryStage.close());
        title.getChildren().addAll(orderBtn,stockBtn,supportBtn,logOut);

        page.setTop(top);
        page.setLeft(title);
        page.setCenter(scrollPane);
        return dialogScene;
    }
    static VBox support(VBox result){
        VBox vbox = new VBox();
        String query = "SELECT * FROM `support` WHERE isDone=0 ORDER BY id";
        ResultSet resultSet= Database.readDatabase(query);

        try{
            if (resultSet != null) {
                while (resultSet.next()) { //read line by line;
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String message = resultSet.getString("message");

                    Label usernameLabel = Common.setLabel("Username：" +username,30,Color.BLACK,null,null);
                    Label emailLabel =Common.setLabel("Email: " + email,30,Color.BLACK,null,null);
                    Label messageLabel = Common.setLabel("Message: " + message,30,Color.BLACK,null,null);
                    Button solvedBtn =Common.beautifyButton("SOLVED",100,30,"yellow","10");
                    solvedBtn.setOnAction(e->{
                        updateSupport(id);
                        changeResultContent(result,support(result));
                    });

                    Pane pane = new Pane();
                    pane.setPadding(new Insets(20, 20, 20, 20));
                    Rectangle line = new Rectangle();
                    line.setWidth(2000);
                    line.setHeight(1);
                    line.setFill(Color.BLACK);
                    pane.getChildren().add(line);


                    vbox.getChildren().addAll(usernameLabel, emailLabel, messageLabel,solvedBtn,pane);
                }
            }
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return vbox;
    }

    static void updateSupport(int id){
        String query ="UPDATE `support` SET isDone = 1 WHERE id = "+id;
        Database.writeDatabase(query);

    }

    static VBox stockResult(Stage primaryStage){
        VBox vbox = new VBox();

        //search the stock by food name
        Label searchLabel = Common.setLabel("Search",40,Color.BLACK,null,null);
        TextField searchBox = new TextField();

        //------------result------------//
        TilePane result = new TilePane();
        Label checkFoodLabel= Common.setLabel("Check Stock",40,Color.BLACK,null,null);

        HBox checkFood = new HBox();

        //check all type
        ArrayList<Button> btnList = new ArrayList<>();
        Button checkAllBtn = Common.beautifyButton("ALL",100,30,"yellow","10");
        btnList.add(checkAllBtn);
        checkAllBtn.setOnAction(e-> {
            try {
                Common.switchColour(checkAllBtn,btnList);
                changeResultContent(result,AdminPage.foodChoice("",primaryStage,"s",searchBox,false));
            } catch (Catch.NullRSException ex) {
                throw new RuntimeException(ex);
            }
        });
        checkFood.getChildren().add(checkAllBtn);

        //check specific type
        for(var d: getFoodType()){
            Button checkBtn = Common.beautifyButton(d,100,30,"yellow","10");
            btnList.add(checkBtn);
            checkBtn.setOnAction(e-> {
                try {
                    Common.switchColour(checkBtn,btnList);
                    changeResultContent(result,AdminPage.foodChoice(d,primaryStage,"s",searchBox,false));
                } catch (Catch.NullRSException ex) {
                    throw new RuntimeException(ex);
                }
            });
            checkFood.getChildren().add(checkBtn);
        }
        vbox.getChildren().addAll(searchLabel,searchBox,checkFoodLabel,checkFood,result);
        return vbox;
    }
    static Stage checkStock(Stage stage1,Food food){
        final Stage dialog = new Stage();
        dialog.setTitle(food.getName()+" details");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage1);

        //label and field
        Label foodDetail = Common.setLabel("Food Detailed: ",30,Color.BLACK,null,null);
        Label nameLabel = Common.setLabel("Name: ",20,Color.BLACK,null,null);
        Label stockLabel =Common.setLabel("Stock: ",20,Color.BLACK,null,null);

        TextField nameField = Common.setTextField(food.getName(), "",50,0,false);
        TextField stockField = Common.setTextField(Integer.toString(food.getStock()), "",50,0,false);
        HBox quantity = Common.integerBox(Integer.MAX_VALUE, stockField);

        Button changeBtn = new Button("change");
        changeBtn.setOnAction(e->{
            String stockFieldText=stockField.getText();
            updateFood(stockFieldText,food);
            dialog.close();
        });

        //grid pane
        GridPane grid = new GridPane();
        grid.add(foodDetail,0,0,2,1);
        grid.add(nameLabel,0,1);
        grid.add(stockLabel,0,2);
        grid.add(nameField,1,1);
        grid.add(quantity,1,2);
        grid.add(changeBtn,0,3);

        //scene and stage
        Scene dialogScene = new Scene(grid,500,300);
        dialog.setScene(dialogScene);
        dialog.show();
        return dialog;
    }
    static boolean updateFood(String stockField,Food food){
        try{
            String query = "UPDATE `food` SET `stock` = '"+stockField+"' WHERE `food`.`name` = '"+food.getName()+"';";
            Database.writeDatabase(query);
            return true;
        }catch (Exception e){
            return false;
        }

    }
    static VBox checkOrder(VBox result) throws Catch.DBException {
        VBox order = new VBox();

        //only select the order that uncompleted and order by order_id
        ResultSet resultSet= Database.readDatabase("SELECT o.user_name,of.order_id,of.food_name,of.quantity FROM `order` o INNER JOIN `order_food` of ON o.order_id=of.order_id WHERE o.isCompleted=0 ORDER BY of.order_id");

        try {
            int prevId=0;
            //count the order num
            int round=0;
            //count the row
            int num = 1;
            //record the order detail
            GridPane grid = null;
            //first order is brown color bg,second is green and cycle it when change order
            ArrayList<Color> colorPicker = new ArrayList<>(Arrays.asList(Color.rgb(200, 200, 200), Color.rgb(230, 230, 230)));
            if (resultSet != null) {
                while (resultSet.next()) { //read line by line
                    String user_name = resultSet.getString("user_name");
                    int id = resultSet.getInt("order_id");
                    String food_name = resultSet.getString("food_name");
                    int quantity = resultSet.getInt("quantity");
                    if(prevId!=id){
                        //ensure this is not the first order
                        if(grid!=null){
                            //prev id is not equal to current id mean that this is different order, and we add this order grid pane into vbox
                            order.getChildren().add(grid);
                        }
                        //create a new grid pane when start to print new order
                        grid = Common.setGridPane(30,100,30,0,0,"",colorPicker.get(round%2));
                        grid.setPadding(new Insets(30,700,30,30));
                        num = 1;
                        round++;
                        Label userNameLabel =  Common.setLabel(user_name,30,Color.BLACK,null,null);
                        Label idLabel= Common.setLabel(""+id,30,Color.BLACK,null,null);
                        Button completeBtn = Common.beautifyButton("COMPLETED",150,30,"yellow","10");
                        completeBtn.setOnAction(e->{
                            completeOrder(id);
                            try {
                                changeResultContent(result,checkOrder(result));
                            } catch (Catch.DBException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        grid.add(userNameLabel,0,0,2,1);
                        grid.add(idLabel,2,0);
                        grid.add(completeBtn,3,0);
                    }
                    prevId = id;
                    grid.add(Common.setLabel(food_name,30,Color.BLACK,null,null),0,num);
                    grid.add(Common.setLabel(Integer.toString(quantity),30,Color.BLACK,null,null),1,num);
                    num++;
                }
            }else{
                throw new Catch.NullHMException("Your database is empty");
            }
            order.getChildren().add(grid);

        } catch (SQLException e){
            throw new Catch.DBException("Cannot connect to your database");
        } catch (Exception a) {
            System.out.println("Sorry, read binary failed");
        }

        return order;
    }
    static boolean completeOrder(int id){
        try {
            String query = "UPDATE `order` SET `isCompleted` = '1' WHERE `order`.`order_id` = " + id + ";";
            Database.writeDatabase(query);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
