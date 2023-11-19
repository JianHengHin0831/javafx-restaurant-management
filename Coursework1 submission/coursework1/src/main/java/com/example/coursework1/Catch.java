package com.example.coursework1;

public class Catch {
    //------------------Exception--------------//
    static class NullHMException extends Exception{
        public NullHMException(String message) {
            super(message);
        }

    }
    static class NullRSException extends Exception{
        public NullRSException(String message) {
            super(message);
        }
    }
    static class DataInputException extends Exception{
        public DataInputException(String message) {
            super(message);
        }
    }
    static class DBException extends Exception{
        public DBException(String message) {
            super(message);
        }
    }
}
