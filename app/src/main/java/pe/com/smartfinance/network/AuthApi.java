package pe.com.smartfinance.network;

public class AuthApi {
    private static String BASE_URL = "http://test-server1.jl.serv.net.mx/RsAuth";

    public static String getUserUrl() {
        return BASE_URL + "/users";
    }

    public static String getAccessTokensUrl() {
        return BASE_URL + "/accessTokens";
    }

}
