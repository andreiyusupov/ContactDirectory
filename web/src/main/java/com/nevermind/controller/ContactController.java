package com.nevermind.controller;

import com.nevermind.dto.ContactDTO;
import com.nevermind.facade.ContactFacade;
import com.nevermind.parser.CriteriaComposer;
import com.nevermind.parser.JsonComposer;
import com.nevermind.parser.JsonParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet("/contacts/*")
public class ContactController extends HttpServlet {

    private final ContactFacade contactFacade = new ContactFacade();
    private final int pageLimit = 20;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            long id = contactFacade.create(
                    JsonParser.parseJsonToDTO(
                            request.getReader()
                                    .lines()
                                    .collect(Collectors.joining())));
            if (id > 0) {
                response.setStatus(201);
            } else {
                response.setStatus(500);
            }
        } else {
            response.setStatus(404);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            PrintWriter out = response.getWriter();
            response.setStatus(200);
            if (request.getQueryString() == null) {
                out.print(
                        JsonComposer.composeJson(
                                contactFacade.getPage(1, pageLimit)));
            } else {
                int currentPage = Integer.parseInt(request.getParameter("current-page"));
                int pageLimit = Integer.parseInt(request.getParameter("page-limit"));

                if (request.getParameterMap().size() > 2) {
                    out.print(
                            JsonComposer.composeJson(
                                    contactFacade.findPage(currentPage, pageLimit, CriteriaComposer.generateCriteriaDTO(request.getParameterMap()))));
                } else {
                    out.print(
                            JsonComposer.composeJson(
                                    contactFacade.getPage(currentPage, pageLimit)));
                }
            }
            out.flush();
        } else {
            String[] splits = pathInfo.split("/");

            if (splits.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                response.setStatus(200);
                long id = Long.parseLong(splits[1]);
                PrintWriter out = response.getWriter();
                out.print(
                        JsonComposer.composeJson(
                                contactFacade.get(id)));
                out.flush();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            String[] splits = pathInfo.split("/");
            if (splits.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                long id = Long.parseLong(splits[1]);
                ContactDTO contactDTO = JsonParser.parseJsonToDTO(request.getReader()
                        .lines()
                        .collect(Collectors.joining()));
                contactDTO.setId(id);
                if (contactFacade.update(contactDTO)) {
                    response.setStatus(200);
                } else {
                    response.setStatus(404);
                }
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            long[] ids = JsonParser.getIdArray(request.getReader()
                    .lines()
                    .collect(Collectors.joining()));
            if (contactFacade.delete(ids)) {
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
        } else {
            String[] splits = pathInfo.split("/");
            if (splits.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                response.setStatus(200);
                long id = Long.parseLong(splits[1]);
                if (contactFacade.delete(id)) {
                    response.setStatus(200);
                } else {
                    response.setStatus(404);
                }
            }
        }
    }
}
