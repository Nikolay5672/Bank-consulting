package Exceptions;

public class InvalidNameException extends Exception{
    public InvalidNameException(){
        super("Name must be at least 3 symbols long.");
    }
}
