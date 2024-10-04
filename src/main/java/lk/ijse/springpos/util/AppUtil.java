package lk.ijse.springpos.util;

import java.util.UUID;

public class AppUtil {

    public static String createCustomerId(){return "CUSTOMER-"+ UUID.randomUUID();}
}
