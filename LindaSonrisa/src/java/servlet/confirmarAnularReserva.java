/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.ReservaController;
import Controller.ModuloController;
import Model.Reserva;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hozonov
 */
@WebServlet(name = "confirmarAnularReserva", urlPatterns = {"/confirmarAnularReserva"})
public class confirmarAnularReserva extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession();
            int codigoReserva = Integer.parseInt(request.getParameter("txtCodigoReserva"));
            String action = request.getParameter("action");
            if(action.equals("confirmar")){
                if(new ReservaController().confirmarReserva(codigoReserva)){
                    sesion.setAttribute("mensajeConfirmarAnular", "La reserva N°"+codigoReserva+" ha sido Confirmada");
                }
            }
            if(action.equals("anular")){
                if(new ReservaController().anularReserva(codigoReserva)){
                    if(new ReservaController().borrarDeTiempoReserva(codigoReserva)){
                        sesion.setAttribute("mensajeConfirmarAnular", "La reserva N°"+codigoReserva+" ha sido Anulada");
                    }
                }
            }
            ArrayList<Reserva> listaReservasPorConfirmar = new ReservaController().buscarReservasPorConfirmar();
            sesion.setAttribute("listaReservasPorConfirmar", listaReservasPorConfirmar);
            response.sendRedirect("/LindaSonrisa/pages/reservasPorConfirmar.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
