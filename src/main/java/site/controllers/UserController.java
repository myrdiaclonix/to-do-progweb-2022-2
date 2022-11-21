package site.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCrypt;

import site.dao.UserDAO;
import site.entities.User;
import site.utils.ResponseJson;
import site.utils.ValidateEmail;

/**
 * Servlet implementation class UserController
 */
@WebServlet("")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserDAO dao;
    
    public UserController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Validate User Logged
        User user = (User) request.getSession().getAttribute("user");
        
        if(user != null) {
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
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
        ResponseJson res = new ResponseJson();

        String action = request.getParameter("action");

        if (action.equals("register")) {
            
            String name = request.getParameter("signUpName");
            String email = request.getParameter("signUpEmail");
            String pass = request.getParameter("signUpPassword");
            
            if (name == null || name != null && name.isBlank()) {
                res.setMsg("Nome inválido!");
                out.println(res.toJson());
                return;
            }
            
            if (email == null || email != null && email.isBlank() || email != null && !ValidateEmail.isValidEmailAddress(email)) {
                res.setMsg("E-mail inválido!");
                out.println(res.toJson());
                return;
            } 
            
            name = name.toLowerCase();
            email = email.toLowerCase();
            List<User> registers = dao.listByEmail(email);
            
            if(registers.size() > 0) {
                res.setMsg("E-mail já cadastrado!");
                out.println(res.toJson());
                return;
            }
            
            if(pass == null || pass != null && pass.isBlank()) {
                res.setMsg("Senha inválida!");
                out.println(res.toJson());
                return;
            } else if (pass.length() < 8) {
                res.setMsg("Senha deve ter mais de 8 caracteres!");
                out.println(res.toJson());
                return;
            }
            
            String passEncrypt = BCrypt.hashpw(pass, BCrypt.gensalt(12));
            User newUser = new User(null, name, email, passEncrypt);
            
            boolean save = dao.Save(newUser);
            
            if(save){
               
               registers = dao.listByEmail(email);
               
               request.getSession().setAttribute("user", registers.get(0)); 
               
               res.setMsg("Usuário cadastrado com sucesso!"); 
               res.setStatus(1);
               
            } else {
                res.setMsg("Erro ao cadastrar usuário!");  
            }
            
            out.println(res.toJson());
            return;
            
        } else if(action.equals("login")) {
            
            String email = request.getParameter("loginEmail");
            String pass = request.getParameter("loginPassword");
            String msgError = "E-mail ou Senha inválidos!";

            if (email == null || email != null && email.isEmpty() || pass == null || pass != null && pass.isEmpty()) {
                res.setMsg(msgError);
                out.println(res.toJson());
                return;
            } 
            
            email = email.toLowerCase();
            List<User> registers = dao.listByEmail(email);
            
            if(registers.size() <= 0 || registers.size() == 1 && !BCrypt.checkpw(pass, registers.get(0).getPassword())) {
                res.setMsg(msgError);
                out.println(res.toJson());
                return;
            } 
                
            request.getSession().setAttribute("user", registers.get(0)); 
            System.out.println(registers.get(0).getEmail());
            res.setMsg("Login feito com sucesso!");
            res.setStatus(1);
            
            out.println(res.toJson());
            return;
            
        } else if(action.equals("logout")) {
            
            request.getSession().removeAttribute("user");
            
            res.setMsg("Logout feito com sucesso!");
            res.setStatus(1);
            
            out.println(res.toJson());
            return;
            
        }
        
        return;

    }

}
