package com.duperez.dancinglhamabot;


import lombok.Data;

@Data
public class AuthSingleton {

    private static AuthSingleton authSingleton;
    String clientId = "8rdgu5vrwswrd4rck6a4iea44wzstk";
    String clientSecret = "";
    String name = "";



    public static AuthSingleton getInstance() {
        if (authSingleton == null) {
            authSingleton = new AuthSingleton();
        }
        return authSingleton;
    }


}
