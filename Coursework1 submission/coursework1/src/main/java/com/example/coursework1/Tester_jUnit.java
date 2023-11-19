package com.example.coursework1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class Tester_jUnit {

    @BeforeEach
    public static void setUp() {
        System.out.println("Before each test method");
    }

    @AfterEach
    public static void tearDown() {
        System.out.println("After each test method");
    }
    @BeforeAll
    public static void setUpBeforeClass() {
        System.out.println("Before all test methods");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        System.out.println("After all test methods");
    }

    @Test
    private static void assertSum(){
        HashMap<String,Integer> hm = new HashMap<>();
        hm.put("apple",5);
        hm.put("orange",5);
        hm.put("pineapple",10);

        assertEquals(6, AdminPage.checkQty(hm,"apple",1));
        System.out.println("The system successfully to view the hashmap and 5+1 = 6");
        assertEquals(7, AdminPage.checkQty(hm,"apple",2));
        System.out.println("The system will change the result when change the quantity and 5+2 = 7");
        assertEquals(12,AdminPage.checkQty(hm,"pineapple",2));
        System.out.println("The system will change the result when change the target and 10+2 = 12");
        assertEquals(2,AdminPage.checkQty(hm,"banana",2));
        System.out.println("The system will give the correct result when the target and 0+2 =2");
        System.out.println("the checkQty method is successfully run");
    }

    @Test
    private static void verifyDate() throws Catch.DataInputException {
        ArrayList<String> arr1 = new ArrayList<>(Arrays.asList("2020","0","0","2021","0","0"));
        assertEquals("compare year",AdminPage.verifyDate(arr1));
        System.out.println("when month,date is 0 and year is different,it will successfully return \"compare year\"");

        ArrayList<String> arr2 = new ArrayList<>(Arrays.asList("2020","1","0","2020","9","0"));
        assertEquals("compare month",AdminPage.verifyDate(arr2));
        System.out.println("when year are equal,date are 0 and month is different,it will successfully return \"compare month\"");

        ArrayList<String> arr3 = new ArrayList<>(Arrays.asList("2020","1","1","2020","1","10"));
        assertEquals("compare date",AdminPage.verifyDate(arr3));
        System.out.println("when year and month are 0 and date is different,it will successfully return \"compare date\"");

        System.out.println("check if it is leap year, it allows 29 feb");
        ArrayList<String> arr4 = new ArrayList<>(Arrays.asList("2020","2","29","2021","1","1"));
        assertEquals("only sales percentage",AdminPage.verifyDate(arr4));
        ArrayList<String> arr5 = new ArrayList<>(Arrays.asList("2019","2","29","2021","1","1"));
        assertEquals("n/a",AdminPage.verifyDate(arr5));
        System.out.println("successful");

        System.out.println("check the n/a date");
        for(int i =31;i<=40;i++){
            ArrayList<String> arr6 = new ArrayList<>(Arrays.asList("2020","1","1","2020","4"));
            arr6.add(Integer.toString(i));
            assertEquals("n/a",AdminPage.verifyDate(arr6));
            System.out.println("when month is 4 and day = #"+i+", it will return \"n/a\"");
        }

        ArrayList<String> arr6 = new ArrayList<>(Arrays.asList("2020","6","1","2020","4","10"));
        assertEquals("only sales percentage",AdminPage.verifyDate(arr6));
        System.out.println("when month or date is different,it will successfully return \"only sales percentage\"");

        System.out.println("the verify date method is successfully run");
    }
    private static void assertHMSum(){
        System.out.println("now test hashmap sum");
        HashMap<String,Integer> hm = new HashMap<>();
        hm.put("apple",5);
        hm.put("orange",7);
        hm.put("pineapple",6);
        for(int i = 0;i<=10;i++){
            int random = (int) (Math.random()*20);
            hm.put("banana",random);
            int assertSum = 5+7+6+random;
            assertEquals(assertSum,AdminPage.findSum(hm,null));
            System.out.println("when banana is #"+random+", it run successful");
        }
        System.out.println("now test arraylist");
        for(int i = 0;i<=10;i++){
            ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(5,7,6));
            int random = (int) (Math.random()*20);
            arr.add(random);
            int assertSum = 5+7+6+random;
            assertEquals(assertSum,AdminPage.findSum(null,arr));
            System.out.println("when int is #"+random+", it run successful");
        }

        System.out.println("the find sum method is successfully run");
    }
    private static void assertHMMax(){
        System.out.println("now test hashmap max");
        HashMap<String,Integer> hm = new HashMap<>();
        hm.put("apple",5);
        hm.put("orange",7);
        hm.put("pineapple",6);
        for(int i = 0;i<=10;i++){
            int random = (int) (Math.random()*20);
            hm.put("banana",random);
            int assertMax = Math.max(random, 7);
            assertEquals(assertMax,AdminPage.findMax(hm,null));
            System.out.println("when banana is #"+random+", it run successful");
        }
        System.out.println("now test arraylist");
        for(int i = 0;i<=10;i++){
            ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(5,7,6));
            int random = (int) (Math.random()*20);
            arr.add(random);
            int assertMax = Math.max(random, 7);
            assertEquals(assertMax,AdminPage.findMax(null,arr));
            System.out.println("when int is #"+random+", it run successful");
        }

        System.out.println("the find max method is successfully run");
    }

    private static void assertHMMin(){
        System.out.println("now test hashmap min");
        HashMap<String,Integer> hm = new HashMap<>();
        hm.put("apple",5);
        hm.put("orange",7);
        hm.put("pineapple",6);
        for(int i = 0;i<=10;i++){
            int random = (int) (Math.random()*20);
            hm.put("banana",random);
            int assertMin = Math.min(random, 5);
            assertEquals(assertMin,AdminPage.findMin(hm,null));
            System.out.println("when banana is #"+random+", it run successful");
        }
        System.out.println("now test arraylist");
        for(int i = 0;i<=10;i++){
            ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(5,7,6));
            int random = (int) (Math.random()*20);
            arr.add(random);
            int assertMin = Math.min(random, 5);
            assertEquals(assertMin,AdminPage.findMin(null,arr));
            System.out.println("when int is #"+random+", it run successful");
        }

        System.out.println("the find min method is successfully run");
    }
    private static void assertSize(){
        System.out.println("now test hashmap size");
        for(int i = 0;i<10;i++){
            HashMap<String,Integer> hm = new HashMap<>();
            int random = (int) (Math.random()*9);
            for(int j =0;j<random;j++){
                hm.put(Integer.toString(j),j);
            }
            assertEquals(random,AdminPage.countSize(hm,null));
            System.out.println("when num is #"+random+", it run successful");
        }
        System.out.println("now test arraylist size");
        for(int i = 0;i<10;i++){
            ArrayList<Integer> arr = new ArrayList<>();
            int random = (int) (Math.random()*9);
            for(int j =0;j<random;j++){
               arr.add(j);
            }
            assertEquals(random,AdminPage.countSize(null,arr));
            System.out.println("when num is #"+random+", it run successful");
        }
        System.out.println("the count size method is successfully run");
    }
    private static void assertAvg(){
        for(int count=0;count<=5;count++){
            for(int sum=0;sum<=5;sum++){
                double assertAvg = 0;
                if(count!=0)assertAvg = (double) sum/count;
                assertEquals(assertAvg,AdminPage.countAvg(sum,count));
                System.out.println("when sum is #"+sum+" and count is #"+count+", it successfully run");
            }
        }
        System.out.println("the count avg method is successfully run");
    }
    public static void assertUpdate(){
        HashMap<Food, String > foodhm = new HashMap<>();
        foodhm.put(new Food("Americano"),"1200");
        foodhm.put(new Food("Corn Cup"),"7650");
        foodhm.put(new Food("Drinking Water"),"3000");
        for(Food f:foodhm.keySet()){
            assertTrue(StaffPage.updateFood(foodhm.get(f), f));
            System.out.println(f.getName()+" is successfully updated");
        }
    }

    public static void assertComplete(){
        for(int id = 1; id<5;id++){
            assertTrue(StaffPage.completeOrder(id));
            System.out.println("order "+id+" is successfully updated");
        }
    }
    public static void assertChangePassword(){
        HashMap<String,String> userDetail = new HashMap<>();
        userDetail.put("cindy","cindy123");
        userDetail.put("aming","aming456");
        userDetail.put("Abu","abu789");
        for(String user:userDetail.keySet()){
            assertTrue(ClientPage.updatePassword(user,userDetail.get(user)));
            System.out.println(user+"'s password is successfully updated");
        }
    }




    public static void main(String args[]) throws Catch.DataInputException {
        Tester_jUnit.setUpBeforeClass();

        Tester_jUnit.setUp();
        Tester_jUnit.assertSum();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.verifyDate();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.assertHMSum();
        Tester_jUnit.tearDown();
        System.out.println();
        Tester_jUnit.setUp();

        Tester_jUnit.assertHMMax();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.assertHMMin();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.assertSize();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.assertAvg();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.assertUpdate();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.assertComplete();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.setUp();
        Tester_jUnit.assertChangePassword();
        Tester_jUnit.tearDown();
        System.out.println();

        Tester_jUnit.tearDownAfterClass();
    }
}
