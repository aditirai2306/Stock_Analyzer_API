package stockAnalyzer.service;

import stockAnalyzer.model.User;

public interface UserService {

    User signUp(User user);

    User signIn(String username, String password);
}
