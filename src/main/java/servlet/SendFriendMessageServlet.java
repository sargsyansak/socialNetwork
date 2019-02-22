package servlet;

import manager.MessageManager;
import manager.UserManager;
import model.Message;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/user/sendMessage")
public class SendFriendMessageServlet extends HttpServlet {
    UserManager userManager = new UserManager();
    MessageManager messageManager = new MessageManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String userId = req.getParameter("userId");
        String comment = req.getParameter("message");
        int id = Integer.parseInt(userId);

        Message message = new Message();
        message.setFromId(user);
        message.setToId(userManager.getUserById(id));
        message.setMessage(comment);
        message.setDate(new Date());
        messageManager.addMessage(message);
        resp.sendRedirect("/user/messagePage?toId=" + userId);

    }
}
