package pe.com.smartfinance.network;

public class ReportApi {
    public static String BASE_URL = "http://test-server1.jl.serv.net.mx/RsReport";

    public static String getReportUrl() {
        return BASE_URL + "/operations/reports";
    }

}
