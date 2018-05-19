package invoice.invoice_api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App
{
    public static void main( String[] args )
    {
        String mysqlConnectUrl = "jdbc:mysql://localhost/midj-dev?characterEncoding=UTF-8&serverTimezone=JST";
        String mysqlUser        = "systena";
        String mysqlPassword   = "";
        String sql = "select * from client;";

        try {
            // DBとの接続
            Connection connection = DriverManager.getConnection(mysqlConnectUrl, mysqlUser, mysqlPassword);
            // ステートメントの取得
            Statement statement = connection.createStatement();
            // SQL文の実行
            ResultSet resultSet = statement.executeQuery(sql);
            // 表示
            while (resultSet.next()){
                System.out.println("client_no: " + resultSet.getString( "client_no" ));
                System.out.println("client_charge_last_name: " + resultSet.getString( "client_charge_last_name" ));
                System.out.println("client_charge_first_name: " + resultSet.getString( "client_charge_first_name" ));
                System.out.println("client_name: " + resultSet.getString( "client_name" ));
                System.out.println("client_address: " + resultSet.getString( "client_address" ));
                System.out.println("client_tel: " + resultSet.getString( "client_tel" ));
                System.out.println("client_fax: " + resultSet.getString( "client_fax" ));
                System.out.println("create_datetime: " + resultSet.getString( "create_datetime" ));
                System.out.println("update_datetime: " + resultSet.getString( "update_datetime" ));
                System.out.println("del_flg: " + resultSet.getString( "del_flg" ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
