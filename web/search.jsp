<%-- 
    Document   : search
    Created on : Apr 26, 2025, 8:59:02 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page import="pe.model.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <h1>Search Page</h1>
        <form action="MainController">
            Search value <input type="text" name ="txtSearchValue"
                                value = "${param.txtSearchValue}"/><br/>
            <input type="submit" value="Search" name="action" />
        </form><br/>
        <c:set var="searchValue" value="${param.txtSearchValue}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>        
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full Name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${result}" var="dto" varStatus="counter">
                        <form action="MainController" method="POST">
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" 
                                           value="${dto.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" 
                                           value="${dto.password}" />
                                </td>
                                <td>
                                    ${dto.fullname}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                            <c:if test="${dto.role}">
                                               checked="checked"
                                            </c:if>
                                    />
                                </td>
                                <td>
                                    <c:url var="deleteLink" value="MainController">
                                        <c:param name="action" value="delete" />
                                        <c:param name="pk" value="${dto.username}" />
                                        <c:param name="lastSearchValue" value="${searchValue}" />
                                    </c:url>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="action" />
                                    <input type="hidden" name="lastSearchValue" 
                                           value="${searchValue}" />
                                </td>
                            </tr>
                        </form>    
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty result}">
                <h2>
                    <font color = "red"> 
                    No record is matched!!!
                    </font>
                </h2>
            </c:if>
        </c:if>
        <%--
       <h1>Search Page</h1>
<form action="MainController">
           Search value <input type="text" name ="txtSearchValue"
                               value="<%= request.getParameter("txtSearchValue")%>" /><br/>
           <input type="submit" value="Search" name="action" />
       </form><br/>
       <%
           String searchValue = request.getParameter("txtSearchValue");
           if (searchValue != null) {
               List<RegistrationDTO> result
                       = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
               if (result != null) {//found
       %>
       <table border="1">
           <thead>
               <tr>
                   <th>No.</th>
                   <th>Username</th>
                   <th>Password</th>
                   <th>Full Name</th>
                   <th>Role</th>
               </tr>
           </thead>
           <tbody>
               <%
                   int count = 0;
                   for (RegistrationDTO dto : result) {
               %>
               <tr>
                   <td>
                       <%= ++count%>    
                       .</td>
                   <td>
                       <%= dto.getUsername()%>
                   </td>
                   <td>
                       <%= dto.getPassword()%>
                   </td>
                   <td>
                       <%= dto.getFullName()%>
                   </td>
                   <td>
                       <%= dto.isRole()%>
                   </td>
               </tr>
               <%
                   } // traverse each dto in result
               %>
           </tbody>
       </table>

        <%
        } else { // not found
        %>
        <h2>
            <font color ="red">
            No record is matched!!!
            </font>
        </h2>
        <%
                }
            } // k phải lần đầu tiên
        %>
        --%>
    </body>
</html>
