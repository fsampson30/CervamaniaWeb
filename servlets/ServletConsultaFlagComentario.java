package servlets;

import com.google.gson.Gson;
import controller.CervejaDao;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletConsultaFlagComentario", urlPatterns = {"/ServletConsultaFlagComentario"})

public class ServletConsultaFlagComentario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

        CervejaDao dao = new CervejaDao();
        ArrayList<Integer> cervejasSelecionadas = new ArrayList<>();
        cervejasSelecionadas = dao.pesquisaFlagComentarios();
        String json = new Gson().toJson(cervejasSelecionadas);
        json = URLEncoder.encode(json, "UTF-8");
        System.out.println(json);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    
}
