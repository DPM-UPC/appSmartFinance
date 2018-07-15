package pe.com.smartfinance.network;

public class OperationApi {
    private static String BASE_URL = "http://test-server1.jl.serv.net.mx/RsOperation";

    public static String getOperationUrl() {
        return BASE_URL + "/operations";
    }

}
