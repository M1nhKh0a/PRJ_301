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

    public boolean checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            // 1. Model connect DB
            con = DbUtils.getConnection();
                if(con!= null) {
            // 2. Model sẽ manipulates DB
            // 2.1 tạo SQL String ( 1. ' a ' cụ thể ( ví dụ a) : statement : load - parse - excute . 2. ? : place holder : chờ :  ( câu lệnh có dạng preparedStatement ) 
            // load - parse - receive - excute . vị trí dấu chấm  ? bắt đầu từ số 1 (từ trên xuống dưới, trái qua phải ) 
            String sql = "select username "
                    + "from Registration "
                    + "where username = ? "
                    + "And password = ? "
                    + "And isAdmin = 1 ";
            // 2.2 Tạo Statement Object ( nạp dữ liệu vào bộ nhớ ) 
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            // 2.3 Excute Query
            rs = stm.executeQuery();
            // 3. Model Xử lí Result
            if (rs.next()) { // buoc 16
                result = true; // buoc 17
            }
                } // connection is available
        } finally {
            if ( rs!=null ) {
                rs.close();
            }
            if (stm!= null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return result;
    }
    
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }
    
    public void searchLastname (String searchValue) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            // 1. Model connect DB
            con = DbUtils.getConnection();
                if(con!= null) {
            // 2. Model sẽ manipulates DB
            // 2.1 tạo SQL String 
            String sql = "SELECT username, password, lastname, isAdmin "
                           + "FROM Registration "
                           + "WHERE lastname LIKE ?";
            // 2.2 Tạo Statement Object ( nạp dữ liệu vào bộ nhớ ) 
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            // 2.3 Excute Query
            rs = stm.executeQuery();
            // 3. Model Xử lí Result
                while (rs.next()) {
                    // 3.1 model gets dữ liệu từ Result set
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    // 3.2 Model sets dữ liệu từ DTO object
                    RegistrationDTO dto = new RegistrationDTO
                        (username, password, fullName, role);
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    } // account is unavailable
                    this.accounts.add(dto);
                } // traverse each row in table
                } // connection is available
        } finally {
            if ( rs!=null ) {
                rs.close();
            }
            if (stm!= null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
    }
    
    public boolean deleteAccount(String username) throw SQLException, ClassNotFoundException {
        
    }
    
    public boolean createAccount(RegistrationDTO account) throw SQLException, ClassNotFoundException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DbUtils.getConnection();
            if (con != null) {
                String sql = "Insert Into Registration("
                        + "username, password, lastName, isAdmin"
                        + ") Value("
                        + "?, ?, ?, ?"
                        + ")";
                stm = con.prepareStatement(sql);
                stm.setString(1, account.getUsername());
                stm.setString(2, account.getPassword());
                stm.setString(3, account.getFullName());
                stm.setString(4, account.isAdmin());
                
                int effectRows = stm.executeUpdate();
                
                if (effectRows > 0) {
                    result = true;
                }

            }
        } finally {
            if (result != null) {
                result.close();
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
