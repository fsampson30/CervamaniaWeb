package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cerveja;
import model.Classificacao;
import model.TipoCerveja;

public class CervejaDao {

    private Cerveja cerveja = new Cerveja();
    private Classificacao atual = new Classificacao();
    private String selecionaDadosCerveja = "SELECT c.CODIGO_CERVEJA, c.NOME_CERVEJA, c.DESCRICAO_CERVEJA, c.CERVEJARIA, c.CODIGO_TIPO_CERVEJA, c.TEOR, c.INGREDIENTES, c.TEMPERATURA, c.COR, c.CODIGO_PAIS_CERVEJA FROM cerveja c WHERE c.NOME_CERVEJA = ?;";
    private String selecionaNomeCerveja = "SELECT c.NOME_CERVEJA FROM cerveja c WHERE c.CODIGO_TIPO_CERVEJA = ?;";
    private String pesquisaNomeCerveja = "SELECT c.NOME_CERVEJA FROM cerveja c WHERE c.NOME_CERVEJA LIKE";
    private String ordemNomeCerveja = "ORDER BY c.NOME_CERVEJA";
    private String selecionaNomeTipoCerveja = "SELECT tp.CODIGO_TIPO_CERVEJA, tp.NOME_TIPO_CERVEJA, tp.DESCRICAO_TIPO_CERVEJA FROM TIPO_CERVEJA tp JOIN CERVEJA c ON tp.CODIGO_TIPO_CERVEJA = c.CODIGO_TIPO_CERVEJA WHERE c.CODIGO_CERVEJA = ?;";
    private String insereClassificacao = "INSERT INTO CLASSIFICACAO (CODIGO_CERVEJA, CLASSIFICACAO, COMENTARIOS) VALUES (?,?,?);";
    private String selecionaNomeCervejaPorPais = "SELECT c.NOME_CERVEJA FROM cerveja c WHERE c.CODIGO_PAIS_CERVEJA = ? ORDER BY c.NOME_CERVEJA;";
    private String selecionaNomeClassificacao = "SELECT c.NOME_CERVEJA ,ROUND( SUM(cl.CLASSIFICACAO) / COUNT(cl.CODIGO_CERVEJA),2)  NOTA FROM CLASSIFICACAO cl JOIN CERVEJA c ON cl.CODIGO_CERVEJA = c.CODIGO_CERVEJA GROUP BY c.NOME_CERVEJA ORDER BY 2 DESC, c.NOME_CERVEJA";
    private String selecionaNotaClassificacao = "SELECT ROUND(SUM(cl.CLASSIFICACAO) / COUNT(cl.CODIGO_CERVEJA),2)  NOTA FROM CLASSIFICACAO cl JOIN CERVEJA c ON cl.CODIGO_CERVEJA = c.CODIGO_CERVEJA GROUP BY cl.CODIGO_CERVEJA ORDER BY 1 DESC, c.NOME_CERVEJA";
    private String selecionaNotaClassificacaoIndividual = "SELECT ROUND(SUM(cl.CLASSIFICACAO) / (SELECT COUNT(clx.CODIGO_CERVEJA) FROM classificacao clx WHERE clx.codigo_cerveja = cl.CODIGO_CERVEJA),2)  NOTA FROM CLASSIFICACAO cl JOIN CERVEJA c ON cl.CODIGO_CERVEJA = c.CODIGO_CERVEJA WHERE cl.CODIGO_CERVEJA = ?;";
    private String selecionaComentarioClassificacao = "SELECT cl.COMENTARIOS FROM classificacao cl JOIN cerveja c ON cl.CODIGO_CERVEJA = c.CODIGO_CERVEJA WHERE c.NOME_CERVEJA = ? AND cl.COMENTARIOS <> ''";
    private String selecionaFlagComentarios = "SELECT ROUND(SUM(cl.CLASSIFICACAO) / (SELECT COUNT(clx.CODIGO_CERVEJA) FROM classificacao clx WHERE clx.codigo_cerveja = cl.CODIGO_CERVEJA) ,2) NOTA , (SELECT CASE WHEN clx.COMENTARIOS IS NOT NULL THEN 1 ELSE 0 END FROM CLASSIFICACAO clx WHERE clx.CODIGO_CERVEJA = cl.CODIGO_CERVEJA  AND clx.COMENTARIOS <> '' GROUP BY clx.CODIGO_CERVEJA) FLAG_COMENTARIOS FROM CLASSIFICACAO cl JOIN CERVEJA c ON cl.CODIGO_CERVEJA = c.CODIGO_CERVEJA  GROUP BY cl.CODIGO_CERVEJA  ORDER BY  1 DESC , c.NOME_CERVEJA";

    public Cerveja pesquisaDadosCerveja(String nome_cerveja) {
        ResultSet rs = null;
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaDadosCerveja);
            ps.setString(1, nome_cerveja);
            rs = ps.executeQuery();
            while (rs.next()) {
                cerveja.setCodigo_cerveja(rs.getString(1));
                cerveja.setNome_cerveja(rs.getString(2));
                cerveja.setDescricao_cerveja(rs.getString(3));
                cerveja.setCervejaria(rs.getString(4));
                cerveja.setCodigo_tipo_cerveja(rs.getString(5));
                cerveja.setTeor(rs.getString(6));
                cerveja.setIngredientes(rs.getString(7));
                cerveja.setTemperatura(rs.getString(8));
                cerveja.setCor(rs.getString(9));
                cerveja.setCodigo_pais_cerveja(rs.getString(10));
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cerveja;
    }

    public ArrayList<String> pesquisaNomeCerveja(String codigo_tipo_cerveja) {
        /*PESQUISA POR TIPO DE CERVEJA*/
        ResultSet rs = null;
        ArrayList<String> cervejasSelecionadas = new ArrayList<>();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaNomeCerveja);
            ps.setString(1, codigo_tipo_cerveja);
            rs = ps.executeQuery();
            while (rs.next()) {
                cerveja.setNome_cerveja(rs.getString(1));
                cervejasSelecionadas.add(cerveja.getNome_cerveja());
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }

    public ArrayList<String> pesquisaNomeCervejaUsuario(String pedaco_nome_cerveja) {
        /*PESQUISA POR PEDAÇO DO NOME DA CERVEJA*/
        ResultSet rs = null;
        ArrayList<String> cervejasSelecionadas = new ArrayList<>();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(pesquisaNomeCerveja + "'%" + pedaco_nome_cerveja + "%'" + ordemNomeCerveja);
            //ps.setString(1, pedaco_nome_cerveja);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                cerveja.setNome_cerveja(rs.getString(1));
                cervejasSelecionadas.add(cerveja.getNome_cerveja());
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }

    public TipoCerveja pesquisaNomeTipoCerveja(String codigo_tipo_cerveja) {
        ResultSet rs = null;
        TipoCerveja tipo = new TipoCerveja();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaNomeTipoCerveja);
            ps.setString(1, codigo_tipo_cerveja);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                tipo.setCodigo_tipo_cerveja(rs.getString(1));
                tipo.setNome_tipo_cerveja(rs.getString(2));
                tipo.setDescricao_tipo_cerveja(rs.getString(3));
            }
            ps.close();
            Conexao.fechar();
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tipo;
    }

    public boolean incluiClassificacao(Classificacao objeto) {
        boolean ok = false;
        try {
            Connection db = Conexao.obter();
            PreparedStatement ps = db.prepareStatement(insereClassificacao);
            ps.setString(1, objeto.getCodigo_cerveja());
            ps.setDouble(2, objeto.getEstrelas());
            ps.setString(3, objeto.getComentarios());
            int sucesso = ps.executeUpdate();
            if (sucesso == 1) {
                ok = true;
            } else {
                db.rollback();
            }
            ps.close();
            db.close();
            Conexao.fechar();
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados" + e);
        }
        return ok;
    }

    public ArrayList<String> pesquisaNomeCervejaPorPais(String codigo_tipo_cerveja) {
        ResultSet rs = null;
        ArrayList<String> cervejasSelecionadas = new ArrayList<>();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaNomeCervejaPorPais);
            ps.setString(1, codigo_tipo_cerveja);
            rs = ps.executeQuery();
            while (rs.next()) {
                cerveja.setNome_cerveja(rs.getString(1));
                cervejasSelecionadas.add(cerveja.getNome_cerveja());
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }

    public ArrayList<String> pesquisaNomeCervejaRanking() {
        ResultSet rs = null;
        ArrayList<String> cervejasSelecionadas = new ArrayList<>();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaNomeClassificacao);
            rs = ps.executeQuery();
            while (rs.next()) {
                cervejasSelecionadas.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }

    public ArrayList<Double> pesquisaNotaCervejaRanking() {
        ResultSet rs = null;
        ArrayList<Double> cervejasSelecionadas = new ArrayList<>();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaNotaClassificacao);
            rs = ps.executeQuery();
            while (rs.next()) {
                cervejasSelecionadas.add(rs.getDouble(1));
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }
    
    public double pesquisaNotaCervejaRankingIndividual(String codigo) {
        ResultSet rs = null;
        double cervejasSelecionadas = 0;
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaNotaClassificacaoIndividual);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                cervejasSelecionadas = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }
               
    public ArrayList<String> pesquisaComentariosRanking(String nome_cerveja) {
        ResultSet rs = null;
        ArrayList<String> cervejasSelecionadas = new ArrayList<>();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaComentarioClassificacao);
            ps.setString(1, nome_cerveja);
            rs = ps.executeQuery();
            while (rs.next()) {
                cervejasSelecionadas.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }
    
    public ArrayList<Integer> pesquisaFlagComentarios() {
        ResultSet rs = null;
        ArrayList<Integer> cervejasSelecionadas = new ArrayList<>();
        Conexao con = new Conexao();
        try {
            Class.forName(con.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaNotaClassificacao);
            rs = ps.executeQuery();
            while (rs.next()) {
                cervejasSelecionadas.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cervejasSelecionadas;
    }
}
