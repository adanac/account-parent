package com.adanac.module.account.web;

import com.adanac.module.account.service.AccountService;
import com.adanac.module.account.service.AccountServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by song on 2016/9/19.
 */
public class ActivateServlet extends HttpServlet {

    private ApplicationContext context;

    @Override
    public void init()
            throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException,
            IOException {
        String key = req.getParameter("key");

        if (key == null || key.length() == 0) {
            resp.sendError(400, "No activation key provided.");
            return;
        }

        AccountService service = (AccountService) context.getBean("accountService");

        try {
            service.activate(key);
            resp.getWriter().write("Account is activated, now you can login.");
        } catch (AccountServiceException e) {
            resp.sendError(400, "Unable to activate account");
            return;
        }
    }
}
