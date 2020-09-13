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
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cerveja;
import model.Classificacao;

@WebServlet(name = "ServletConsultaNotaRankingIndividual", urlPatterns = {"/ServletConsultaNotaRankingIndividual"})

public class ServletConsultaNotaRankingIndividual extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int length = request.getContentLength();
        byte[] input = new byte[length];
        ServletInputStream sin = request.getInputStream();
        int c, count = 0;
        while ((c = sin.read(input, count, input.length - count)) != -1) {
            count += c;
        }
        sin.close();

        String parametro = new String(input);
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

        CervejaDao dao = new CervejaDao();
        double cervejasSelecionadas = 0;
        cervejasSelecionadas = dao.pesquisaNotaCervejaRankingIndividual(parametro);
        String json = new Gson().toJson(cervejasSelecionadas);
        json = URLEncoder.encode(json, "UTF-8");
        System.out.println(json);
        writer.write(json);
        writer.flush();
        writer.close();

    }

}
