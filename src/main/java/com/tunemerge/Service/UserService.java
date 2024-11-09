package com.tunemerge.Service;

import com.tunemerge.Model.Client;
import com.tunemerge.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    static final private PasswordEncoder PASSWORD_ENCODER=new BCryptPasswordEncoder();
    public boolean createUser(String email,String username, String password) {
        if(isValidEmail(email) && password!=null) {
            Client newClient = new Client();
            newClient.setEmail(email);
            newClient.setPassword(PASSWORD_ENCODER.encode(password));
            newClient.setUsername(username);
            userRepository.save(newClient);
            return true;
        }else return false;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    public boolean checkUser(String username, String password) {
        Client client=userRepository.findByUsername(username);
        if(client!=null){
            return PASSWORD_ENCODER.matches(password,client.getPassword());
        }
        return false;
    }
}
