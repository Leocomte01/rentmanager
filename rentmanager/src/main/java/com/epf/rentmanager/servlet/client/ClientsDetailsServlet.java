package com.epf.rentmanager.servlet.client;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/details")
public class ClientsDetailsServlet extends HttpServlet {

    @Autowired
    ClientService clientService;

    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(Long.parseLong(request.getParameter("id")));

        try {
            System.out.println(reservationService.findResaByClientId(Long.parseLong(request.getParameter("id"))));
            System.out.println(clientService.findById(Long.parseLong(request.getParameter("id"))));
            request.setAttribute("rents", reservationService.findResaByClientId(Long.parseLong(request.getParameter("id"))));
            request.setAttribute("client", clientService.findById(Long.parseLong(request.getParameter("id"))));
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);

    }
}
