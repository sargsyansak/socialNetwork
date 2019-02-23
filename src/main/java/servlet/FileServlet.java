package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.server.LogStream;

@WebServlet("/getFile")

public class FileServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = req.getParameter("file");
        OutputStream outputStream = resp.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream("C:\\socialNetwork\\files\\" + file);
        byte[] buf = new byte[4096];
        int inRead = 0;

        while ((inRead = fileInputStream.read()) > 0) {
            outputStream.write(buf, 0, inRead);

        }
        fileInputStream.close();
        outputStream.flush();
    }
}
