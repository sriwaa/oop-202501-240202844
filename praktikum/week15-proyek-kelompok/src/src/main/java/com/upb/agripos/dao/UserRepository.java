package main.java.com.upb.agripos.dao;
import main.java.com.upb.agripos.model.User;

public interface UserRepository {
    User findByUsername(String username) throws Exception;
}