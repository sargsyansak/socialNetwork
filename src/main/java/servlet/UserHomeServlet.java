package servlet;

import manager.UserManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user/home")

public class UserHomeServlet extends HttpServlet {
    UserManager userManager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            req.getSession().setAttribute("user", user);
            req.setAttribute("allUser", userManager.getAllUsers());
            req.setAttribute("allFriends", userManager.getAllFrineds(user.getId()));
            req.setAttribute("allRequests", userManager.getAllRequests(user.getId()));
            req.getRequestDispatcher("/WEB-INF/user/user.jsp").forward(req, resp);
        }
    }
}
