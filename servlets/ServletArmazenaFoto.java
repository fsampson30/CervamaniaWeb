package servlets;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletArmazenaFoto", urlPatterns = {"/ServletArmazenaFoto"})

public class ServletArmazenaFoto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         try {
            int length = request.getContentLength();
            System.out.println(String.valueOf(length));
            ServletInputStream sin = request.getInputStream();  
            byte [] input = new byte[length];
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count +=c;
            }
            sin.close();            
            
            Calendar cal = Calendar.getInstance();
            File file = new File("c:/Fotos/"+cal.getTimeInMillis()+".jpg");
            System.out.println(file.getName());
            FileOutputStream parametro = new FileOutputStream(file);
            if (!file.exists()) {
		file.createNewFile();
	    }
            parametro.write(input);            
            parametro.flush();
            parametro.close();
            System.out.println("Sera?");
            
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            writer.write("Arquivo Salvo com Sucesso!");           
            writer.flush();
            writer.close();              
 
        } catch (IOException e) {
             try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
    
    }
    }
}

    


