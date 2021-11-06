package com.cs6550.upicresortsserver.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Authentication {

  public static boolean checkBasicAuth(String authorization) {
    if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
      // Authorization: Basic base64credentials
      String base64Credentials = authorization.substring("Basic".length()).trim();
      byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
      String credentials = new String(credDecoded, StandardCharsets.UTF_8);
      // credentials = username:password
      final String[] values = credentials.split(":", 2);
      String username = values[0];
      String password = values[1];
      //  checking that the username and password are set to the ultra-secret values of “admin” and “admin”
      return username.equals("admin") && password.equals("admin");
    }
    return false;
  }
}
