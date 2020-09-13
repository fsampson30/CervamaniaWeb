package servlets;

import com.google.gson.Gson;
import controller.CervejaDao;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Classificacao;

@WebServlet(name = "ServletInsereClassificacao", urlPatterns = {"/ServletInsereClassificacao"})
public class ServletInsereClassificacao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
             ServletInputStream sin = request.getInputStream();
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count +=c;
            }
            sin.close();
 
            String parametro = new String(input);
            parametro = URLDecoder.decode(parametro, "UTF-8");
            System.out.println(parametro);
            Gson gson = new Gson();
            System.out.println(gson.toString());
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            Classificacao classificacao = new Classificacao();
            classificacao = gson.fromJson(parametro, Classificacao.class);                        
 
            CervejaDao dao = new CervejaDao();
            Boolean ok = false;
            ok = dao.incluiClassificacao(classificacao);            
            String json = new Gson().toJson(ok);
            json = URLEncoder.encode(json, "UTF-8");
            writer.write(json);
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



