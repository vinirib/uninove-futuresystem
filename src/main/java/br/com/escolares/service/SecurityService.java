package br.com.escolares.service;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
