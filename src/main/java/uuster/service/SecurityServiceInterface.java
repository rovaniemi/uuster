package uuster.service;

public interface SecurityServiceInterface {
    String findLoggedInUsername();
    void autologin(String username, String password);
}