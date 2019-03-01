package servlet;

import manager.UserManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user/changePassword")

public class ChangePasswordServlet extends HttpServlet {
    UserManager userManager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatNewPassword = req.getParameter("repeatNewPassword");
        if (user.getPassword().equals(oldPassword)) {
            if (newPassword.equals(repeatNewPassword)) {
                user.setPassword(newPassword);
                userManager.changeUserPassword(user);
                req.setAttribute("message","Password changed");
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/user/changePassword.jsp").forward(req, resp);
            } else {
                req.setAttribute("errorMessage", "Password not match");
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/user/changePassword.jsp").forward(req, resp);

            }
        } else {
            req.setAttribute("errorMessage", "Old password is incorrect");
            req.getSession().setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/user/changePassword.jsp").forward(req, resp);


        }


    }
}
