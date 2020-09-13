package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import model.Cerveja;
import model.Classificacao;

@WebServlet(name = "ServletConsultaNomeRanking", urlPatterns = {"/ServletConsultaNomeRanking"})

public class ServletConsultaNomeRanking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

        CervejaDao dao = new CervejaDao();
        ArrayList<String> cervejasSelecionadas = new ArrayList<>();
        cervejasSelecionadas = dao.pesquisaNomeCervejaRanking();
        String json = new Gson().toJson(cervejasSelecionadas);
        json = URLEncoder.encode(json, "UTF-8");
        System.out.println(json);
        writer.write(json);
        writer.flush();
        writer.close();

    }

}
