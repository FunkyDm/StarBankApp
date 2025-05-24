package pro.sky.StarBankApp.StarBankApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.StarBankApp.StarBankApp.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UUID getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username);
    }

    public String getFullNameByUsername(String username) {
        return userRepository.getFullNameByUsername(username);
    }
}
