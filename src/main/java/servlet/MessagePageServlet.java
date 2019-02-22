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
import java.util.Comparator;
import java.util.List;

@WebServlet(urlPatterns = "/user/messagePage")

public class MessagePageServlet extends HttpServlet {
    MessageManager messageManager = new MessageManager();
    UserManager userManager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String toId = req.getParameter("toId");
        int id = Integer.parseInt(toId);
        List<Message> messageList = messageManager.getMessages(user.getId(), id);
        messageList.sort(Comparator.comparing(Message::getDate));
        req.setAttribute("allMessages", messageList);
        req.setAttribute("toUser", userManager.getUserById(id));
        req.getSession().setAttribute("sessionUser", user);
        req.getRequestDispatcher("/WEB-INF/message/message.jsp").forward(req, resp);

    }
}
