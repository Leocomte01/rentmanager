package com.epf.rentmanager.servlet.vehicle;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/modif")
public class VehicleModifServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Vehicle modifVehicle = vehicleService.findById(Long.parseLong(request.getParameter("id")));
            request.setAttribute("vehicle", modifVehicle);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/modif.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("manufacturer");
        int nbPlaces = Integer.parseInt(request.getParameter("seats"));
        try {
            Vehicle modifVehicle = vehicleService.findById(Long.parseLong(request.getParameter("id")));
            modifVehicle.setConstructeur(constructeur);
            modifVehicle.setNbPlaces(nbPlaces);
            System.out.println(modifVehicle);
            vehicleService.modif(modifVehicle);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }

        response.sendRedirect("/rentmanager/vehicles");
    }
}
