package com.nevermind.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AboutController", urlPatterns = "/about")
public class AboutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Hello World";
        request.setAttribute("message", message); // This will be available as ${message}
        request.getRequestDispatcher("/WEB-INF/views/about.html").forward(request, response);
    }
}


