package servlet;

import manager.UserManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user/acceptOrReject")

public class AcceptOrRejectServlet extends HttpServlet {
    UserManager userManager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String getFromId = req.getParameter("fromId");
        String action = req.getParameter("action");
        User user = (User) req.getSession().getAttribute("user");
        int id = Integer.parseInt(getFromId);
        if (action.equals("accept")) {
            userManager.addFriend(user.getId(), id);
            userManager.removeRequest(user.getId(), id);
            resp.sendRedirect("/user/home");
        } else if (action.equals("reject")) {
            userManager.removeRequest(user.getId(), id);
            resp.sendRedirect("/user/home");

        }

    }
}
