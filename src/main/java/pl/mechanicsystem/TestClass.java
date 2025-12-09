package pl.mechanicsystem;

import pl.mechanicsystem.security.JwtService;

public class TestClass {

    public static void main(String[] args) {

        JwtService jwtService = new JwtService();
        String a = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsIm5hbWUiOiJhZG1pbjAwMDAiLCJsb2dpbiI6ImFkbWluIiwiZXhwIjoxNzY1MTI3MDM0LCJpYXQiOjE3NjUxMjY0MzR9.VwuDK-l3OeO1hQaZRY7i5fvJHlAfTxrD-x9SLV_Is3c";
        String b = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsIm5hbWUiOiJhZG1pbjAwMDAiLCJsb2dpbiI6ImFkbWluIiwiZXhwIjoxNzY1MTI3MDM0LCJpYXQiOjE3NjUxMjY0MzR9.VwuDK-l3OeO1hQaZRY7i5fvJHlAfTxrD-x9SLV_Is3c";

        String token = jwtService.generateAccessToken(1L, "admin", "admin0000");
        System.out.println(token);
        System.out.println();
        System.out.println(jwtService.constantTimeEquals(a, b));
    }
}
