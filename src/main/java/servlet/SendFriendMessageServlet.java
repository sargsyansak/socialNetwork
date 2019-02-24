package servlet;


import manager.MessageManager;
import manager.UserManager;
import model.Message;
import model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/user/sendMessage")
public class SendFriendMessageServlet extends HttpServlet {
    UserManager userManager = new UserManager();
    MessageManager messageManager = new MessageManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        String userId = req.getParameter("userId");
        int id = Integer.parseInt(userId);

        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 1024);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1024 * 1024 * 50);
            upload.setSizeMax(1024 * 1024 * 5 * 50);
            String uploadPath = "C:\\socialNetwork\\files\\";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            try {
                Message message = new Message();
                List<FileItem> formItems = upload.parseRequest(req);
                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (!item.isFormField()) {
                            if (!item.getName().equals("")) {
                                String fileName = System.currentTimeMillis() + "_" + new File(item.getName()).getName();
                                String filePath = uploadPath + File.separator + fileName;
                                File storeFile = new File(filePath);
                                item.write(storeFile);
                                message.setFile(fileName);
                            }
                        } else {
                            if (item.getFieldName().equals("message")) {
                                message.setMessage(item.getString());
                            }
                        }
                    }
                    message.setToId(userManager.getUserById(id));
                    message.setFromId(user);
                    message.setDate(new Date());
                    messageManager.addMessage(message);
                    resp.sendRedirect("/user/messagePage?toId=" + userId);
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
