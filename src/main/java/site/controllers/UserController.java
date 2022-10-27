package site.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import site.dao.UserDAO;
import site.entities.User;
import site.utils.ResponseJson;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/cadastro")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO dao = new UserDAO();
    
    public UserController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> usuarios = dao.findAll();
        
        for(User jg : usuarios) {
            System.out.println(jg.getEmail());
        }

        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        Gson json = new Gson();
        ResponseJson res = new ResponseJson();
        
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String passCon = request.getParameter("password-confirm");

        
        if(email.isEmpty()) {
            res.setMsg("Erro no e-mail");
            out.println(res.toJson());
            out.close();
            return;
        }
        
        if(pass.isEmpty()) {
            res.setMsg("Senha inválida!");
            out.println(res.toJson());
            out.close();
            return;
        } else if(pass.length() < 8) {
            res.setMsg("A senha deve possuir mais de 8 digitos!");
            out.println(res.toJson());
            out.close();
            return;
        }
        
        if(passCon.isEmpty()) {
            res.setMsg("Confirme a senha!");
            out.println(res.toJson());
            out.close();
            return;
        } else if(!passCon.equals(pass)) {
            res.setMsg("Confirmação da senha não é igual a senha!");
            out.println(res.toJson());
            out.close();
            return;
        }
        
        // Check User's e-mail
        List<User> teste = dao.listByEmail(email);
        if(teste != null && teste.size() > 0) {
            res.setMsg("Usuário Já Cadastrado!");
            out.println(res.toJson());
            out.close();
            return;
        } 

        // Add Usuario
        User us = new User(null, email, pass);        
        boolean save = dao.Save(us);
        
        if(!save) {
            res.setMsg("Erro ao Cadastrar Usuário!");
            out.println(res.toJson());
            out.close();
            return;
        }
        
        //Map<String, String[]> valores = request.getParameterMap();
        // valores.get("email")[0];
        
        res = new ResponseJson("Usuário Cadastrado com Sucesso!", 1);
        out.println(res.toJson());
        out.close();

    }

}
