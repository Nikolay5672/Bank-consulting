package validation;

import Exceptions.InvalidEmailException;
import Exceptions.InvalidNameException;
import Exceptions.InvalidPhoneException;
import com.summerpractice.bankconsulting.model.Appointment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static boolean validateName(String name){
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]{3,}\\b");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    private static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private static boolean validatePhone(String phone){
        Pattern pattern = Pattern.compile("0[0-9]{9}");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static void appointmentValidation(Appointment appointment) throws Exception{
        if(!validateName(appointment.getFirstName())){
            throw new InvalidNameException();
        }
        if(!validateName(appointment.getLastName())){
            throw new InvalidNameException();
        }
        if(!validateEmail(appointment.getEmail())){
            throw new InvalidEmailException();
        }
        if(!validatePhone(appointment.getPhone())){
            throw new InvalidPhoneException();
        }
    }
}
