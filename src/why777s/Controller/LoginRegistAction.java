package why777s.Controller;

import com.opensymphony.xwork2.ActionSupport;
import why777s.DAO.UserQuery;
import why777s.Db.DbClose;
import why777s.Db.DbConn;
import why777s.Entity.User;

import java.sql.*;

/**
 * Created by wangzhaojun on 2017/2/21.
 */
public class LoginRegistAction extends ActionSupport {
    private String userName;
    private String passWord;

    public LoginRegistAction() {
        System.out.println("实例化Action...");
    }

    public LoginRegistAction(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }



    public String regist() throws Exception{
        boolean registration_Succcessfull =
                new UserQuery().is_Regist_Successful(getUserName(),getPassWord());
        if (registration_Succcessfull){
            return SUCCESS;
        }else {
            return ERROR;
        }
    }
    @Override
    public String execute() throws Exception {

        boolean isValidate = new UserQuery().isValidate(getUserName(),getPassWord());
        if (isValidate){
            return SUCCESS;
        }else {
            return ERROR;
        }

//        使用JDBC 已测试

//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        boolean flag  = false;
//        conn = DbConn.getconn();
//        String sql = "SELECT * FROM USER WHERE USERNAME=?";
//        try {
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, getUserName());
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                if (rs.getString("PASSWORD").equals(getPassWord())) {
//                    flag = true;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("好像有问题啊兄弟");
//        }finally {
//            DbClose.addClose(pstmt,conn);
//        }
//        if (flag){
//            return SUCCESS;
//        }else{
//            return ERROR;
//        }
    }
}
