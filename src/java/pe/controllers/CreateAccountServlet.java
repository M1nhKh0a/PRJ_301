/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.model.registration.RegistrationCreateError;
import pe.model.registration.RegistrationDAO;
import pe.model.registration.RegistrationDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    private static final String ERROR_PAGE = "createNewAccount.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        
        // 1. Get all user's information
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullName = request.getParameter("txtFullname");
        
        RegistrationCreateError errors = new RegistrationCreateError();
        boolean foundErr = false;
        
        try {
            // 2. Controller processes all user's errors
            if (username.trim().length() < 6 || username.trim().length() > 20) {
                foundErr = true;
                errors.setUsernameLengthErr("Needs to be 6 - 20 chararcters");
            }
            
            if (password.trim().length() < 6 || username.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordLengthErr("Needs to be 6 - 30 chararcters");
            } else if (!confirm.trim().equals(password.trim())) {
                foundErr = true;
                errors.setConfirmNotMatched("Needs to be 6 - 30 chararcters");
            }
            
            if (fullName.trim().length() < 2 || username.trim().length() > 50) {
                foundErr = true;
                errors.setFullNameLengthErr("Needs to be 2 - 50 chararcters");
            }
            
            if (foundErr = true) { // cache errors into attribute of request
                request.setAttribute("CREATE_ERRORS", errors);
            } else {
                // 3. Controller calls methods of Model
                RegistrationDAO dao = new RegistrationDAO();
                
                RegistrationDTO dto = new RegistrationDTO(username, password, fullName, false);
                boolean result = dao.createAccount(dto);
                // 4. Controller processes results
                if (result) {
                    url = LOGIN_PAGE;
                }
            }
            
        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            log("CreteAccountServlet _ SQL: " + errMsg);
            
            if (errMsg.contains("duplicate")) {
                errMsg.setUsernameIsExisted(username + " is existed");
                
                request.setAttribute("CREATE_ERRORS", errors);
            }
        } catch (ClassNotFoundException ex) {
            log("CreteAccountServlet _ SQL: " + ex.getMessage());    
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
