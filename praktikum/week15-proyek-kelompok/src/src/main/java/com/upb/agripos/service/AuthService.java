package main.java.com.upb.agripos.service;

import main.java.com.upb.agripos.dao.UserRepository;
import main.java.com.upb.agripos.model.User;

public class AuthService {
    private final UserRepository userRepo;
    private static User currentUser; 

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean login(String username, String password) throws Exception {
        User user = userRepo.findByUsername(username);
        // Cek password sederhana (di real world pakai hash)
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public static User getCurrentUser() { return currentUser; }
    public static void logout() { currentUser = null; }
}