package com.example.coursework1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AdminPage {
    static Scene adminPage(Stage primaryStage,Scene prevScene){
        BorderPane page = new BorderPane();

        Scene dialogScene = new Scene(page);

        //-------------top------------//
        VBox top = new VBox();

        HBox bar = Common.sceneBasic(primaryStage,prevScene);
        HBox logo = new HBox();
        ImageView imageView=Common.createImageView("img_1.png",150,120);
        Label label = Common.setLabel("McDonald's Administrator System",90,Color.YELLOW,null,"#c30e0e");
        logo.getChildren().addAll(imageView,label);

        top.getChildren().addAll(bar,logo);
        logo.setStyle("-fx-background-color: #c30e0e;");

        //--------------result------------//
        VBox result = new VBox();

        ScrollPane scrollPane = Common.createScrollPane();
        scrollPane.setContent(result);

        //--------------title--------------------------//
        VBox title=new VBox();
        //title properties
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(20);
        dropShadow.setOffsetY(20);

        // Apply the drop shadow effect to the VBox
        title.setEffect(dropShadow);
        title.setBackground(Background.fill(Color.YELLOW));
        title.setAlignment(Pos.CENTER);
        title.prefHeightProperty().bind(scrollPane.heightProperty());

        //button in the title
        Button userBtn = Common.beautifyButton("USER",150,100,"yellow","16");
        Button stockBtn=  Common.beautifyButton("STOCK",150,100,"yellow","16");
        Button statisticBtn =  Common.beautifyButton("STATISTICS",150,100,"yellow","16");
        Button logOut = Common.beautifyButton("LOG OUT",150,100,"yellow","16");
        userBtn.setOnAction(e-> {
            Common.switchColour(userBtn,new ArrayList<>(Arrays.asList(stockBtn,statisticBtn)));
            Common.changeResultContent(result,checkUser());
        });
        stockBtn.setOnAction(e->{
            Common.switchColour(stockBtn,new ArrayList<>(Arrays.asList(userBtn,statisticBtn)));
            Common.changeResultContent(result,checkStock(primaryStage));
        });
        statisticBtn.setOnAction(e->{
            Common.switchColour(statisticBtn,new ArrayList<>(Arrays.asList(stockBtn,userBtn)));
            Common.changeResultContent(result,statistics());
        });
        logOut.setOnAction(e->primaryStage.close());
        title.getChildren().addAll(userBtn,stockBtn,statisticBtn,logOut);
        page.setTop(top);
        page.setLeft(title);
        page.setCenter(scrollPane);
        return dialogScene;
    }

    static VBox statistics(){
        VBox vbox = new VBox();

        //-------------date chooser-------------------//
        HBox before = new HBox();
        Label beforeLabel = Common.setLabel("Initial Date",30,Color.BLACK,null,null);
        HBox after = new HBox();
        Label afterLabel = Common.setLabel("Final Date",30,Color.BLACK,null,null);

        int currentYear = LocalDate.now().getYear();
        ObservableList<String> yearOption0 = FXCollections.observableArrayList();
        ObservableList<String> yearOption1 = FXCollections.observableArrayList();
        for(int i = 2020;i<=currentYear;i++){
            yearOption0.add(Integer.toString(i));
            yearOption1.add(Integer.toString(i));
        }
        ComboBox<String> year0 = new ComboBox<>(yearOption0);
        year0.setValue("2020");
        ComboBox<String> year1 = new ComboBox<>(yearOption1);
        year1.setValue("2020");

        // 0 means ignore month/date
        ObservableList<String> monthOption0 = FXCollections.observableArrayList();
        ObservableList<String> monthOption1 = FXCollections.observableArrayList();
        for(int i = 0;i<=12;i++){
            monthOption0.add(Integer.toString(i));
            monthOption1.add(Integer.toString(i));
        }
        ComboBox<String> month0 = new ComboBox<>(monthOption0);
        ComboBox<String> month1 = new ComboBox<>(monthOption1);
        month0.setValue("0");
        month1.setValue("0");

        ObservableList<String> dayOption0 = FXCollections.observableArrayList();
        ObservableList<String> dayOption1 = FXCollections.observableArrayList();
        for(int i =0;i<=31;i++){
            dayOption0.add(Integer.toString(i));
            dayOption1.add(Integer.toString(i));
        }
        ComboBox<String> day0 = new ComboBox<>(dayOption0);
        day0.setValue("0");
        ComboBox<String> day1 = new ComboBox<>(dayOption1);
        day1.setValue("0");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(year0,month0,day0);
        hbox.setAlignment(Pos.CENTER);
        before.getChildren().add(hbox);

        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(year1,month1,day1);
        hbox1.setAlignment(Pos.CENTER);
        after.getChildren().add(hbox1);

        //btn list
        ArrayList<Button> btnList = new ArrayList<>();

        //----------pie chart-------------//
        VBox chart = new VBox();

        ArrayList<String> foodType = new ArrayList<>(Arrays.asList("burger","drinks","dessert","fried","set","others"));
        HBox salesPBox = new HBox();
        //all content
        Label salesPLabel = Common.setLabel("Sales Percentage",30,Color.BLACK,null,null);
        Button allSalesP = Common.beautifyButton("ALL",100,30,"yellow","10");
        btnList.add(allSalesP);
        allSalesP.setOnAction(e->{
            //get again the date data
            ArrayList<String> date = new ArrayList<>(Arrays.asList(year0.getValue(),month0.getValue(),day0.getValue(),year1.getValue(),month1.getValue(),day1.getValue()));
            //change the content of chart
            try {
                Common.switchColour(allSalesP,btnList);
                Common.changeResultContent(chart,salesPercentage("all",date));
            } catch (Catch.DataInputException ex) {
                throw new RuntimeException(ex);
            }
        });
        salesPBox.getChildren().addAll(allSalesP);

        //-----------bar chart-----------//
        HBox compareBox = new HBox();
        Label compareLabel = Common.setLabel("Compare Sales",30,Color.BLACK,null,null);
        Button compareAll = Common.beautifyButton("ALL",100,30,"yellow","10");
        btnList.add(compareAll);
        compareAll.setOnAction(e->{
            Common.switchColour(compareAll,btnList);
            ArrayList<String> date = new ArrayList<>(Arrays.asList(year0.getValue(),month0.getValue(),day0.getValue(),year1.getValue(),month1.getValue(),day1.getValue()));
            try {
                Common.changeResultContent(chart,compareBox("all",date));
            } catch (Catch.DataInputException ex) {
                throw new RuntimeException(ex);
            }
        });
        compareBox.getChildren().addAll(compareAll);

        //-----------line chart-----------//
        HBox salesBox = new HBox();
        Label salesLabel = Common.setLabel("Sales",30,Color.BLACK,null,null);
        Button allSales = Common.beautifyButton("ALL",100,30,"yellow","10");
        btnList.add(allSales);
        allSales.setOnAction(e->{
            ArrayList<String> date = new ArrayList<>(Arrays.asList(year0.getValue(),month0.getValue(),day0.getValue(),year1.getValue(),month1.getValue(),day1.getValue()));
            try {
                Common.switchColour(allSales,btnList);
                Common.changeResultContent(chart,lineBox("all",date));
            } catch (Catch.DataInputException ex) {
                throw new RuntimeException(ex);
            }
        });

        salesBox.getChildren().addAll(allSales);

        //for specific categories
        for(var d: foodType){
            Button salesPBtn = Common.beautifyButton(d,100,30,"yellow","10");
            salesPBtn.setOnAction(e->{
                ArrayList<String> date = new ArrayList<>(Arrays.asList(year0.getValue(),month0.getValue(),day0.getValue(),year1.getValue(),month1.getValue(),day1.getValue()));
                try {
                    Common.switchColour(salesPBtn,btnList);
                    Common.changeResultContent(chart,salesPercentage(d,date));
                } catch (Catch.DataInputException ex) {
                    throw new RuntimeException(ex);
                }
            });
            salesPBox.getChildren().add(salesPBtn);
            Button compareFood = Common.beautifyButton(d,100,30,"yellow","10");
            compareFood.setOnAction(e->{
                ArrayList<String> date = new ArrayList<>(Arrays.asList(year0.getValue(),month0.getValue(),day0.getValue(),year1.getValue(),month1.getValue(),day1.getValue()));
                try {
                    Common.switchColour(compareFood,btnList);
                    Common.changeResultContent(chart,compareBox(d,date));
                } catch (Catch.DataInputException ex) {
                    throw new RuntimeException(ex);
                }
            });
            compareBox.getChildren().add(compareFood);
            Button salesBtn = Common.beautifyButton(d,100,30,"yellow","10");
            salesBtn.setOnAction(e->{
                ArrayList<String> date = new ArrayList<>(Arrays.asList(year0.getValue(),month0.getValue(),day0.getValue(),year1.getValue(),month1.getValue(),day1.getValue()));
                try {
                    Common.switchColour(salesBtn,btnList);
                    Common.changeResultContent(chart,lineBox(d,date));
                } catch (Catch.DataInputException ex) {
                    throw new RuntimeException(ex);
                }
            });
            salesBox.getChildren().add(salesBtn);
            btnList.add(salesPBtn);btnList.add(salesBtn);btnList.add(compareFood);
        }
        vbox.getChildren().addAll(beforeLabel,before,afterLabel,after,salesPLabel,salesPBox,compareLabel,compareBox,salesLabel,salesBox,chart);
        return vbox;
    }
    static String verifyDate(ArrayList<String> date) throws Catch.DataInputException {
        try{
            ArrayList<String> check31Days = new ArrayList<>(Arrays.asList("1","3","5","7","8","10","12"));
            int year0=Integer.parseInt(date.get(0));
            int month0=Integer.parseInt(date.get(1));
            int day0=Integer.parseInt(date.get(2));
            int year1=Integer.parseInt(date.get(3));
            int month1=Integer.parseInt(date.get(4));
            int day1=Integer.parseInt(date.get(5));
            boolean isLeap0 = year0%4==0;
            boolean isLeap1 = year1%4==0;
            if(month0==2) {
                if(isLeap0&&day0>29 || !isLeap0&&day0>28){
                    return "n/a";
                }
            }
            if(month1==2) {
                if(isLeap1&&day1>29 || !isLeap1&&day1>28){
                    return "n/a";
                }
            }
            if(check31Days.contains(Integer.toString(month0))&&day0>31 || check31Days.contains(Integer.toString(month1))&&day1>31){
                return "n/a";
            }
            if(!check31Days.contains(Integer.toString(month0))&&day0>30 || !check31Days.contains(Integer.toString(month1))&&day1>30){
                return "n/a";
            }
            //if month=0, it means that month not important and the chart only compare by year.eg. 2020,2021,2022
            if(month0==0||month1==0){
                return "compare year";
            }
            //if year is equal and day=0, it means that date not important and the chart only compare by month.eg. 2020-08,2020-09,2020-10
            if((day0==0||day1==0) && year0==year1){
                return "compare month";
            }
            //if year and month is equal, it means that date not important and the chart only compare by date.eg. 2020-08-07,2020-08-08,2020-08-09
            if(month0==month1 && year0==year1) {
                return "compare date";
            }
            //else only the pie chart is available because pie chart is not compare but just count the sales percentage
            else {
                return "only sales percentage";
            }

        }catch(Exception e){
            throw new Catch.DataInputException("Your date input is wrong!");
        }

    }

    static int checkQty(HashMap<String,Integer> hm,String target,int quantity ){
        int qty;
        if(hm.containsKey(target))qty = hm.get(target) + quantity;
        else qty = quantity;
        return qty;
    }

    static VBox salesPercentage(String target,ArrayList<String> date) throws Catch.DataInputException {
        String result = verifyDate(date);
        int year0=Integer.parseInt(date.get(0));
        int month0=Integer.parseInt(date.get(1));
        if(month0==0)month0=1;
        int day0=Integer.parseInt(date.get(2));
        if (day0==0)day0=1;
        int year1=Integer.parseInt(date.get(3));
        int month1=Integer.parseInt(date.get(4));
        if(month1==0)month1=12;
        int day1=Integer.parseInt(date.get(5));
        if(day1==0)day1= (month1 == 2) ? 28 : ((month1 == 4 || month1 == 6 || month1 == 9 || month1 == 11) ? 30 : 31);
        //if month0=0,it assumes it is 1, month1 =0,it assumes it is 12,day0=0,it assumes it is 1,if day1=0,it values is last day of the month

        if(result.equals("n/a")){
            return null;
        }
        PieChart piechart = new PieChart();
        HashMap<String,Integer> hm = new HashMap<>();
        //join 3 tables and count the food that is sold
        ResultSet resultSet= Database.readDatabase("SELECT o.`datetime`,of.`food_name`,of.`quantity`,f.`type` from `order` o INNER JOIN order_food of ON o.order_id=of.order_id INNER JOIN food f ON of.food_name=f.name WHERE o.isCompleted=1");
        try {
            if (resultSet != null) {
                //parse it to date
                LocalDate startDate = LocalDate.of(year0, month0, day0);
                LocalDate endDate = LocalDate.of(year1, month1, day1);
                while (resultSet.next()) {
                    String name = resultSet.getString("food_name");
                    String type = resultSet.getString("type");
                    int quantity = resultSet.getInt("quantity");
                    String datetime = resultSet.getString("datetime");
                    //my table is YYYYMMDD_HHMMSS
                    int year =Integer.parseInt(datetime.substring(0,4));
                    int month =Integer.parseInt(datetime.substring(4,6));
                    int day =Integer.parseInt(datetime.substring(6,8));
                    LocalDate dateToCheck = LocalDate.of(year, month, day);

                    //only get the data within the before date and after date
                    if (dateToCheck.isAfter(endDate) || dateToCheck.isBefore(startDate)) {
                        continue;
                    }
                    if(target.equals("all")){
                        int qty=checkQty(hm,type,quantity);
                        hm.put(type, qty);
                    } else if(target.equals(type)){
                        int qty=checkQty(hm,name,quantity);
                        hm.put(name, qty);
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(String key:hm.keySet()){
            piechart.getData().add(new PieChart.Data(key, hm.get(key)));
        }
        //count the max,min,avg data
        VBox analysis = analysisStatistics(hm,null);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(piechart,analysis);
        return vbox;
    }

    static VBox compareBox(String target,ArrayList<String> date) throws Catch.DataInputException {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> chart = new BarChart<>(xAxis,yAxis);
        chart.setTitle("Menu Item Revenue");
        xAxis.setLabel("Menu Item");
        yAxis.setLabel("Sales");


        String result = verifyDate(date);
        int year0=Integer.parseInt(date.get(0));
        int month0=Integer.parseInt(date.get(1));
        int day0=Integer.parseInt(date.get(2));
        int year1=Integer.parseInt(date.get(3));
        int month1=Integer.parseInt(date.get(4));
        int day1=Integer.parseInt(date.get(5));

        if(result.equals("n/a") || result.equals("only sales percentage")){
            return null;
        }
        HashMap<String, Integer> hm;
        ArrayList<Integer> analysisD = new ArrayList<>();

        if(result.equals("compare year")) {
            for (int i = year0; i <= year1; i++) {
                XYChart.Series series1 = new XYChart.Series();
                series1.setName(Integer.toString(i));
                //get data here
                hm = checkSuitableData(target, result, i, 0, 0,"bar");
                for(String key:hm.keySet()){
                    series1.getData().add(new XYChart.Data(key, hm.get(key)));
                    analysisD.add(hm.get(key));
                }
                chart.getData().add(series1);
            }
        }
        if(result.equals("compare month")){
            for(int i =month0;i<=month1;i++){
                XYChart.Series series1 = new XYChart.Series();
                series1.setName(Integer.toString(i));
                hm = checkSuitableData(target, result, year0, i, 0,"bar");
                for(String key:hm.keySet()){
                    series1.getData().add(new XYChart.Data(key, hm.get(key)));
                    analysisD.add(hm.get(key));
                }
                chart.getData().add(series1);
            }
        }
        if(result.equals("compare date")){
            for(int i =day0;i<=day1;i++){
                XYChart.Series series1 = new XYChart.Series();
                series1.setName(Integer.toString(i));
                hm = checkSuitableData(target, result, year0, month0, i,"bar");
                for(String key:hm.keySet()){
                    series1.getData().add(new XYChart.Data(key, hm.get(key)));
                    analysisD.add(hm.get(key));
                }
                chart.getData().add(series1);
            }
        }
        VBox analysis = analysisStatistics(null,analysisD);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(chart,analysis);
        return vbox;
    }

    static HashMap<String,Integer> checkSuitableData(String target,String result,int year0,int month0,int date0,String chartType){
        HashMap<String, Integer> hm = new HashMap<>();
        ResultSet resultSet= Database.readDatabase("SELECT o.`datetime`,of.`food_name`,of.`quantity`,f.`type` from `order` o INNER JOIN order_food of ON o.order_id=of.order_id INNER JOIN food f ON of.food_name=f.name WHERE o.isCompleted=1");
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String name = resultSet.getString("food_name");
                    String type = resultSet.getString("type");
                    int quantity = resultSet.getInt("quantity");
                    String datetime = resultSet.getString("datetime");
                    int year =Integer.parseInt(datetime.substring(0,4));
                    int month =Integer.parseInt(datetime.substring(4,6));
                    int day =Integer.parseInt(datetime.substring(6,8));

                    //only get the required year
                    if(result.equals("compare year") && year != year0){
                        continue;
                    }
                    if(result.equals("compare month") && (month != month0 || year!=year0)){
                        continue;
                    }
                    if(result.equals("compare date")&& (day!=date0 ||month != month0 || year!=year0) ){
                        continue;
                    }

                    //bar chart
                    if(chartType.equals("bar")) {
                        if (target.equals("all")) {
                            int qty=checkQty(hm,type,quantity);
                            hm.put(type, qty);
                        } else if (target.equals(type)) {
                            int qty=checkQty(hm,name,quantity);
                            hm.put(name, qty);
                        }
                    }

                    //line chart
                    if (chartType.equals("line")){
                        //line chart only want to get total sales, so no need the record name
                         if (target.equals(type) || target.equals("all")) {
                             int qty =checkQty(hm,target,quantity);
                            hm.put(target, qty);
                        }
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hm;
    }

    static VBox lineBox(String target,ArrayList<String> date) throws Catch.DataInputException {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales ($)");
        LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);
        chart.setTitle("Menu Item Revenue");


        String result = verifyDate(date);
        int year0=Integer.parseInt(date.get(0));
        int month0=Integer.parseInt(date.get(1));
        int day0=Integer.parseInt(date.get(2));
        int year1=Integer.parseInt(date.get(3));
        int month1=Integer.parseInt(date.get(4));
        int day1=Integer.parseInt(date.get(5));
        if(result.equals("n/a") || result.equals("only sales percentage")){
            return null;
        }

        HashMap<String, Integer> hm = new HashMap<>();
        ArrayList<Integer> analysisD = new ArrayList<>();

        XYChart.Series series = new XYChart.Series();
        series.setName("Sales");
        XYChart.Series series1 = new XYChart.Series();
        if(result.equals("compare year")) {
            xAxis.setLabel("Year");
            xAxis.setLowerBound(year0);
            xAxis.setUpperBound(year1);
            xAxis.setAutoRanging(false);
            for (int i = year0; i <= year1; i++) {
                series1.setName(Integer.toString(i));
                hm = checkSuitableData(target, result, i,0, 0,"line");
                if(hm.get(target)==null)hm.put(target,0);//prevent null
                analysisD.add(hm.get(target));
                series1.getData().add(new XYChart.Data(i, hm.get(target)));
            }
        }
        if(result.equals("compare month")){
            xAxis.setLabel("Month");
            xAxis.setLowerBound(month0);
            xAxis.setUpperBound(month1);
            xAxis.setAutoRanging(false);
            for(int i =month0;i<=month1;i++){
                series1.setName(Integer.toString(i));
                hm = checkSuitableData(target, result, year0, i, 0,"line");
                if(hm.get(target)==null)hm.put(target,0);
                analysisD.add(hm.get(target));
                series1.getData().add(new XYChart.Data(i, hm.get(target)));
            }
        }
        if(result.equals("compare date")){
            xAxis.setLabel("Date");
            xAxis.setLowerBound(day0);
            xAxis.setUpperBound(day1);
            xAxis.setAutoRanging(false);
            for(int i =day0;i<=day1;i++){
                series1.setName(Integer.toString(i));
                hm = checkSuitableData(target, result, year0, month0, i,"line");
                if(hm.get(target)==null)hm.put(target,0);
                analysisD.add(hm.get(target));
                series1.getData().add(new XYChart.Data(i, hm.get(target)));
            }
        }
        chart.getData().add(series1);
        VBox analysis = analysisStatistics(null,analysisD);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(chart,analysis);
        return vbox;

    }

    static VBox analysisStatistics(HashMap<String, Integer> hm,ArrayList<Integer> arr){
        VBox vbox  =new VBox();

        int sum=findSum(hm,arr);
        int count=countSize(hm,arr);
        int max=findMax(hm,arr);
        int min=findMin(hm,arr);
        double avg = countAvg(sum, count);

        DecimalFormat df = new DecimalFormat("0.00");
        Label sumLabel=Common.setLabel("SUM: "+df.format(sum),20,Color.BLACK,null,null);
        Label maxLabel=Common.setLabel("MAX: "+df.format(max),20,Color.BLACK,null,null);
        Label minLabel=Common.setLabel("MIN: "+df.format(min),20,Color.BLACK,null,null);
        Label avgLabel=Common.setLabel("AVG: "+df.format(avg),20,Color.BLACK,null,null);

        vbox.getChildren().addAll(sumLabel,maxLabel,minLabel,avgLabel);

        return vbox;
    }
    static int findMax(HashMap<String, Integer> hm,ArrayList<Integer> arr){
        int max=0;
        //count by using hashmap
        if(hm!=null) {
            for (String key : hm.keySet()) {
                int result = hm.get(key);
                if (result >= max) {
                    max = result;
                }
            }
        }
        //count by using arr
        else{
            for (var d: arr) {
                int result = d;
                if (result >= max) {
                    max = result;
                }
            }
        }
        return max;
    }
    static int findMin(HashMap<String, Integer> hm,ArrayList<Integer> arr){
        int min = Integer.MAX_VALUE;
        //count by using hashmap
        if(hm!=null) {
            for (String key : hm.keySet()) {
                int result = hm.get(key);
                if (result <= min) {
                    min = result;
                }
            }
        }
        //count by using arr
        else{
            for (var d: arr) {
                int result = d;
                if (result <= min) {
                    min = result;
                }
            }
        }
        return min;
    }
    static int findSum(HashMap<String, Integer> hm,ArrayList<Integer> arr){
        int sum=0;
        //count by using hashmap
        if(hm!=null) {
            for (String key : hm.keySet()) {
                int result = hm.get(key);
                sum += result;
            }
        }
        //count by using arr
        else{
            for (var d: arr) {
                int result = d;
                sum += result;
            }
        }
        return sum;
    }
    static int countSize(HashMap<String, Integer> hm,ArrayList<Integer> arr){
        //count by using hashmap
        int count = 0;
        if(hm!=null) {
            count = hm.size();
        }
        //count by using arr
        else{count=arr.size();
        }
        return count;
    }
    static double countAvg(int sum, int count){
        double avg;
        if(count!=0){
            avg = (double) sum/count;
        }else{//prevent divide by 0
            avg = 0;
        }
        return avg;
    }

    static VBox checkStock(Stage primaryStage){
        VBox vbox = new VBox();

        //--------result------------//
        VBox result = new VBox();

        //btn list
        ArrayList<Button> btnList = new ArrayList<>();
        //----------------add food----------------------//
        Label addFoodLabel= Common.setLabel("Add Food",40,Color.BLACK,null,null);
        HBox addFoodBox = new HBox();
        TextField nameField = Common.setTextField("","Name",0,0,true);
        TextField priceField = Common.setTextField("","Price",0,0,true);
        TextField stockField = Common.setTextField("","Stock",0,0,true);
        TextField urlField = Common.setTextField("","Picture URL",0,0,true);

        addFoodBox.getChildren().addAll(nameField,priceField,stockField,urlField);
        HBox addFood = new HBox();



        //------------check food----------//
        TextField searchBox = new TextField();
        searchBox.setPromptText("Search Food");
        searchBox.setPrefWidth(600);

        Label checkFoodLabel= Common.setLabel("Check Food",40,Color.BLACK,null,null);
        HBox checkFood = new HBox();
        Button checkAllBtn = Common.beautifyButton("ALL",100,30,"yellow","10");//check all
        checkAllBtn.setOnAction(e-> {
            try {
                Common.switchColour(checkAllBtn,btnList);
                Common.changeResultContent(result,foodChoice("",primaryStage,"a",searchBox,false));
            } catch (Catch.NullRSException ex) {
                throw new RuntimeException(ex);
            }
        });
        checkFood.getChildren().addAll(checkAllBtn);
        btnList.add(checkAllBtn);

        for(var d: Common.getFoodType()){
            Button foodBtn = Common.beautifyButton(d,100,30,"yellow","10");
            Button checkBtn = Common.beautifyButton(d,100,30,"yellow","10");
            //when the button clicked, the content in result vbox will change
            foodBtn.setOnAction(e-> {
                Common.switchColour(foodBtn,btnList);
                Common.changeResultContent(result,addFood("add",nameField,priceField,stockField,urlField,d));
            });
            checkBtn.setOnAction(e-> {
                try {
                    Common.switchColour(checkBtn,btnList);
                    Common.changeResultContent(result,foodChoice(d,primaryStage,"a",searchBox,false));
                } catch (Catch.NullRSException ex) {
                    throw new RuntimeException(ex);
                }
            });
            addFood.getChildren().add(foodBtn);
            checkFood.getChildren().add(checkBtn);
            btnList.add(foodBtn);btnList.add(checkBtn);
        }
        vbox.getChildren().addAll(addFoodLabel,addFoodBox,addFood,checkFoodLabel,searchBox,checkFood,result);
        return vbox;
    }

    static VBox addFood(String role, TextField nameField,TextField priceField, TextField stockField,TextField url,String type){
        VBox vbox = new VBox();
        Label resultLabel = Common.setLabel("",50,Color.BLACK,null,null);
        vbox.getChildren().addAll(resultLabel);
        String nameInput = nameField.getText();
        String priceInput = priceField.getText();
        String stockInput = stockField.getText();
        String urlInput = url.getText();

        ResultSet resultSet= Database.readDatabase("Select * FROM food");
        try {
            boolean isCollision = false;
            if(role.equals("add")) {
                if (resultSet != null) {
                    while (resultSet.next()) { //read line by line
                        String name = resultSet.getString("name");
                        if (name.equals(nameInput)) {//check if the name is registered
                            resultLabel.setText("Sorry, already registered");
                            nameField.setText("");
                            priceField.setText("");
                            stockField.setText("");
                            url.setText("");
                            isCollision = true;
                            break;
                        }
                    }
                }
            }
            if(!isCollision){
                try {
                    double price=Double.parseDouble(priceInput);
                    int stock=Integer.parseInt(stockInput);
                    String query ;
                    if(role.equals("add")) {//add food
                        query="INSERT INTO `food` (`name`, `price`, `stock`,`type`,`photo`) VALUES ('" + nameInput + "', '" + price + "', '" + stock + "', '" + type + "', '" + urlInput + "');";
                    } else if (role.equals("edit")) {//edit food
                        query="UPDATE `food` SET `price` = '"+price+"', `stock` = '"+stock+"', `photo` = '"+urlInput+ "' WHERE `food`.`name` = '" +nameInput+ "';";
                    }else{//edit food without url
                        query="UPDATE `food` SET `price` = '"+price+"', `stock` = '"+stock+ "' WHERE `food`.`name` = '" +nameInput+ "';";
                    }
                    Database.writeDatabase(query);
                    resultLabel.setText("successfully add!!");
                }catch (Exception ex){
                    System.out.println("error");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    return vbox;
    }

    static VBox checkUser(){
        VBox vbox = new VBox();

        //btn list
        ArrayList<Button> btnList = new ArrayList<>();

        Label addEmployeeLabel =Common.setLabel("Add Employee",40,Color.BLACK,null,null);
        TextField usernameField = Common.setTextField("","Enter Username",0,0,true);
        PasswordField passwordField = Common.setPasswordField("Enter Password",0,0);
        PasswordField passwordField1 = Common.setPasswordField("Confirm Username",0,0);
        Label resultLabel = Common.setLabel("",20,Color.BLACK,null,null);

        Label checkUser = Common.setLabel("Search User",40,Color.BLACK,null,null);
        TextField searchBox = Common.setTextField("","search username",200,30,true);

        Button checkStaffBtn = Common.beautifyButton("STAFF",150,30,"yellow","10");
        Button checkClientBtn = Common.beautifyButton("CLIENT",150,30,"yellow","10");
        Button checkAllBtn = Common.beautifyButton("ALL",150,30,"yellow","10");
        Button checkAdminBtn = Common.beautifyButton("ADMINISTRATOR",150,30,"yellow","10");
        btnList.add(checkAdminBtn);btnList.add(checkClientBtn);btnList.add(checkAllBtn);btnList.add(checkStaffBtn);
        //print all user data
        TableView userDetail = buildTable(userdata("",searchBox));

        Button staffBtn = Common.beautifyButton("STAFF",150,30,"yellow","10");
        Button adminBtn= Common.beautifyButton("ADMINISTRATOR",150,30,"yellow","10");
        btnList.add(staffBtn);btnList.add(adminBtn);
        staffBtn.setOnAction(e->{
            boolean isEmpty = usernameField.getText().isEmpty()&&passwordField.getText().isEmpty()&&passwordField1.getText().isEmpty();
            if(!isEmpty){
                Common.addUser(usernameField,passwordField,passwordField1,resultLabel,"s","add",null);
                userDetail.setItems(userdata("",searchBox));
                Common.switchColour(staffBtn,btnList);
            }
        });
        adminBtn.setOnAction(e->{
            boolean isEmpty = usernameField.getText().isEmpty()&&passwordField.getText().isEmpty()&&passwordField1.getText().isEmpty();
            if(!isEmpty){
                Common.addUser(usernameField,passwordField,passwordField1,resultLabel,"a","add",null);
                userDetail.setItems(userdata("",searchBox));
                Common.switchColour(adminBtn,btnList);
            }
        });

        Label editUserLabel =Common.setLabel("Edit User",40,Color.BLACK,null,null);
        TextField upUsernameField = Common.setTextField("","Enter Username",0,0,false);
        PasswordField upPasswordField = Common.setPasswordField("Enter Password",0,0);
        PasswordField upPasswordField1 = Common.setPasswordField("Confirm Username",0,0);
        Label upResultLabel = Common.setLabel("",20,Color.BLACK,null,null);
        Button editBtn = Common.beautifyButton("Edit",100,30,"yellow","10");
        Button deleteBtn = Common.beautifyButton("Delete",100,30,"yellow","10");
        btnList.add(editBtn);btnList.add(deleteBtn);
        editBtn.setOnAction(e->{
            boolean isEmpty0 = upUsernameField.getText().isEmpty()&&upPasswordField.getText().isEmpty()&&upPasswordField1.getText().isEmpty();
            if(!isEmpty0){
                Common.addUser(upUsernameField,upPasswordField,upPasswordField1,upResultLabel,"","edit",null);
                Common.switchColour(editBtn,btnList);
                userDetail.setItems(userdata("",searchBox));
            }
        });
        deleteBtn.setOnAction(e->{
            boolean isEmpty0 = upUsernameField.getText().isEmpty()&&upPasswordField.getText().isEmpty()&&upPasswordField1.getText().isEmpty();
            if(!isEmpty0){
                Common.switchColour(deleteBtn,btnList);
                String query = "DELETE FROM `user` WHERE `user`.`name` = '"+upUsernameField.getText()+"'";
                Database.writeDatabase(query);
                userDetail.setItems(userdata("",searchBox));
            }
        });


        //when clicked the data in the table
        userDetail.setOnMouseClicked(e->{
            User user =(User) userDetail.getSelectionModel().getSelectedItem();
            String query = "SELECT * FROM `user` WHERE name = \""+user.getName()+"\"";
            ResultSet resultSet= Database.readDatabase(query);
            try{
                if (resultSet != null) {
                    while (resultSet.next()) { //read line by line;
                        String name = resultSet.getString("name");
                        String password = resultSet.getString("password");
                        upUsernameField.setText(name);
                        upPasswordField.setText(password);
                        upPasswordField1.setText(password);
                        break;
                    }
                }
            }catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        //when click the button, the content in the table will change
        checkAdminBtn.setOnAction(e->{
            userDetail.setItems(userdata("admin",searchBox));
            Common.switchColour(checkAdminBtn,btnList);

        });
        checkClientBtn.setOnAction(e->{
            Common.switchColour(checkClientBtn,btnList);
            userDetail.setItems(userdata("client",searchBox));
        });
        checkStaffBtn.setOnAction(e->{
            userDetail.setItems(userdata("staff",searchBox));
            Common.switchColour(checkStaffBtn,btnList);
        });
        checkAllBtn.setOnAction(e->{
            userDetail.setItems(userdata("",searchBox));
            Common.switchColour(checkAllBtn,btnList);
        });


        //structure
        HBox addUserBox = new HBox();
        addUserBox.getChildren().addAll(usernameField,passwordField,passwordField1,staffBtn,adminBtn);
        HBox editUserBox = new HBox();
        editUserBox.getChildren().addAll(upUsernameField,upPasswordField,upPasswordField1,editBtn,deleteBtn);
        HBox checkUserBox = new HBox();
        checkUserBox.getChildren().addAll(checkAllBtn,checkAdminBtn,checkStaffBtn,checkClientBtn);
        vbox.getChildren().addAll(addEmployeeLabel,addUserBox,resultLabel,editUserLabel,editUserBox,upResultLabel,checkUser,searchBox,checkUserBox, userDetail);
        return vbox;
    }

    static ObservableList<User> userdata(String roleRequest,TextField search){
        ObservableList<User> list = FXCollections.observableArrayList();
        ResultSet resultSet= Database.readDatabase("Select * FROM user");
        try {
            if (resultSet != null) {
                while (resultSet.next()) { //read line by line
                    String name = resultSet.getString("name");
                    String password=resultSet.getString("password");
                    String role = resultSet.getString("role");
                    switch (role) {
                        case "a" -> role = "admin";
                        case "c" -> role = "client";
                        case "s" -> role = "staff";
                    }
                    if((roleRequest.equals(role) || roleRequest.equals("")) && name.contains(search.getText())){
                        //only add the required role to observable list
                        list.add(new User(name,password,role));
                    }
                }
            }
        } catch (Exception a) {
            System.out.println("Sorry, read binary failed");
        }
        return list;
    }
    static TableView buildTable(ObservableList<User> list){
        var table = new TableView<User>();
        table.setPrefHeight(600);
        //Creating columns
        TableColumn nameCol = new TableColumn("username");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(800);
        TableColumn passwordCol = new TableColumn("password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordCol.setPrefWidth(300);
        TableColumn roleCol = new TableColumn("role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setPrefWidth(300);

        table.setItems(list);
        table.getColumns().addAll(nameCol,passwordCol,roleCol);
        return table;
    }

    static TilePane foodChoice(String typeRequest,Stage primaryStage,String role,TextField search,boolean isPromotion) throws Catch.NullRSException {
        ResultSet resultSet;
        if(isPromotion==true){
            resultSet= Database.readDatabase("Select * FROM food WHERE promotion=1 ORDER BY type ");
        }else{
            resultSet= Database.readDatabase("Select * FROM food ORDER BY type ");
        }
        TilePane tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setPadding(new Insets(25, 25, 25, 25));

        try {
            if (resultSet != null) {
                while (resultSet.next()) { //read line by line
                    String name = resultSet.getString("name");
                    double price=resultSet.getDouble("price");
                    int stock=resultSet.getInt("stock");
                    String type = resultSet.getString("type");
                    String photo= resultSet.getString("photo");
                    int sales = resultSet.getInt("sales");
                    boolean promote = resultSet.getBoolean("promotion");
                    Image img = new Image(photo,100,150,true,true);
                    if((typeRequest.equals(type) || typeRequest.equals("")) && name.contains(search.getText())){
                        //add the image into imageview
                        ImageView imgv = new ImageView(img);
                        imgv.setFitWidth(300);
                        imgv.setFitHeight(300);

                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        //Button properties
                        Button food = new Button(name+"\n$ "+decimalFormat.format(price));
                        food.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                        food.setPrefWidth(300);
                        food.setGraphic(imgv);
                        food.setContentDisplay(ContentDisplay.TOP);
                        food.setOnAction(e->{
                            Food f = new Food(name,price,stock,type,sales,photo,promote);
                            if(role.equals("a"))adminModifyFood(primaryStage,f);
                            if(role.equals("c")){
                                ClientPage.ClientBuyFood(primaryStage,f);
                            }
                            if(role.equals("s"))StaffPage.checkStock(primaryStage,f);
                        });
                        tilePane.getChildren().add(food);
                    }
                }
            }
        }catch(SQLException a){
            throw new Catch.NullRSException("Cannot connect to your database");
        } catch (Exception a) {
            a.printStackTrace();
        }
        return tilePane;
    }
    static Stage adminModifyFood(Stage stage1,Food food){
        //a stage that let the admin modify or delete data
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage1);
        dialog.setTitle(food.getName());

        Label foodDetail = new Label("Enter Food Details");
        foodDetail.setTextFill(Color.RED);
        Label nameLabel = Common.setLabel("name: ",20,Color.BLACK,null,null);
        Label priceLabel = Common.setLabel("price: ",20,Color.BLACK,null,null);
        Label stockLabel = Common.setLabel("stock: ",20,Color.BLACK,null,null);
        Label urlLabel = Common.setLabel("URL: ",20,Color.BLACK,null,null);
        Label promotionLabel = Common.setLabel("Promotion: ",20,Color.BLACK,null,null);

        TextField nameField = Common.setTextField(food.getName(), "",300,0,false);
        TextField priceField = Common.setTextField(Double.toString(food.getPrice()), "",300,0,true);
        TextField stockField = Common.setTextField(Integer.toString(food.getStock()), "",300,0,true);
        TextField urlField = Common.setTextField(food.getImg(), "",300,0,true);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList("true", "false"));
        comboBox.setValue(String.valueOf(food.isPromotion()));


        Button editBtn = Common.beautifyButton("edit",200,30,"yellow","10");
        editBtn.setOnAction(e->{
            addFood("edit",nameField,priceField,stockField,urlField,food.type);
            String selectedValue = comboBox.getValue();
            boolean parsedValue = Boolean.parseBoolean(selectedValue);
            updatePromotion(parsedValue,food.getName());
            dialog.close();
        });

        Button deleteBtn = Common.beautifyButton("delete",200,30,"yellow","10");
        deleteBtn.setOnAction(e->{
            String query = "DELETE FROM `food` WHERE `food`.`name` = '"+food.name+"'";
            Database.writeDatabase(query);
            dialog.close();
        });


        GridPane grid = new GridPane();
        grid.add(foodDetail,0,0,2,1);
        grid.add(nameLabel,0,1);
        grid.add(priceLabel,0,2);
        grid.add(stockLabel,0,3);
        grid.add(urlLabel,0,4);
        grid.add(promotionLabel,0,5);
        grid.add(nameField,1,1);
        grid.add(priceField,1,2);
        grid.add(stockField,1,3);
        grid.add(urlField,1,4,2,1);
        grid.add(comboBox,1,5,2,1);
        grid.add(editBtn,0,6);
        grid.add(deleteBtn,1,6);
        Scene dialogScene = new Scene(grid,500,400);
        dialog.setScene(dialogScene);
        dialog.show();

        return dialog;
    }
    static void updatePromotion(boolean isPromote, String foodName){
        String promotion="0";
        if(isPromote==true)promotion="1";
        String query ="UPDATE food SET promotion ="+promotion+" WHERE name='"+foodName+"';";
        Database.writeDatabase(query);
    }

}
