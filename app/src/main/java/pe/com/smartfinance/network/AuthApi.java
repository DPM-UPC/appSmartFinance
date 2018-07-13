package pe.com.smartfinance.network;

public class AuthApi {
    private static String BASE_URL = "http://192.168.1.43:8081";

    public static String getUserUrl() {
        return BASE_URL + "/users";
    }

    public static String getAccessTokensUrl() {
        return BASE_URL + "/accessTokens";
    }

}
