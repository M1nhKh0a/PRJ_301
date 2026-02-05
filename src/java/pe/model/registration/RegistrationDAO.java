/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.model.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class RegistrationDAO implements Serializable {
    
    public boolean checkLogin(String username, String password)
            throws SQLException, ClassNotFoundException {
        boolean result = false;
        //1. model connect database
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //2. Create SQL String
            con = DbUtils.getConnection();
            if (con != null) {
                String sql = "Select isAdmin "
                        + "From Registration "
                        + "Where username= ? and password = ?";
                
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                
                //4. Execute Statement Object
                rs = stm.executeQuery();
                
                //5. Process Result  
                if(rs.next()){
                    // if user has isAdmin in DB is 1 => accept login else not login
//                    boolean role = rs.getBoolean("isAdmin");
//                    if(role == true){
//                        result = true;
//                    }
                    result = true;
                }
            }
        } finally {
            /*Luu y:   
                + dung xong phai dong lai
                + Dong nguoc voi luc mo
            */
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    // Tao danh sach de luu account
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public void searchLastname(String searchValue)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. model connect database
            con = DbUtils.getConnection();
            if (con != null) {
                /* 2. Create SQL String
                    + moi menh de SQL phai viet tren 1 dong
                    + moi khi xuong dong bat buoc phai them khoang trang
                 */
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname like ?";

                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%"); //tham so thu nhat la value duoc chuyen vao searchLastname

                /*4. Execute Statement Object 
                    CUD--> so int the hien so dong affected 
                    row|R--> result set: 1 object tro den 1 list, moi phan tu trong list 
                    se mapping vs 1 dong trong server, phan tu dau tien chua BeginOfFile, 
                    va cuoi cung chua EndOfFile, de dich chuyen con tro su dung phuong 
                    thuc next() tra ve T or F co dac tinh la forward only */
                rs = stm.executeQuery();

                /*5. Process Result
                    phai bt cau lenh tra 1 row hay nhiu row:
                        + only 1 -> use if,
                        + more than 1 -> use while */
                while (rs.next()) {
                    //5.1 Model loads/get data from resultset
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");

                    //5.2 Model sets data to DTO object
                    RegistrationDTO dto = new RegistrationDTO(username, password, fullName, role);
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    } // account is invailuable
                    this.accounts.add(dto);
                }//end traverse is available
            }// connection is available
        } finally {
            /*Luu y:   
                + dung xong phai dong lai
                + Dong nguoc voi luc mo
             */
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();

            }
        }
    }
}
