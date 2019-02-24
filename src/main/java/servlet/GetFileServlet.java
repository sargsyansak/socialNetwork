package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/getFile")
public class GetFileServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = req.getParameter("file");
        OutputStream outputStream = resp.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream("C:\\socialNetwork\\files\\" + file);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = fileInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        fileInputStream.close();
        outputStream.flush();

    }
}
