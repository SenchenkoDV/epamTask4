package com.senchenko.devices.servlet;

import com.senchenko.devices.parser.AbstractDeviceParser;
import com.senchenko.devices.parser.DeviceBuilderFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet{
    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIR = "input";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileUrl = "";
        String applicationPath = req.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        String typeParser = req.getParameter("parser");
        File fileSaveDir = new File(uploadFilePath);
        if(!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        try {
            for(Part part : req.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    fileUrl = uploadFilePath + File.separator + part.getSubmittedFileName();
                    part.write(fileUrl);
                    req.setAttribute("result", part.getSubmittedFileName() + " upload successfully");
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error in I/O" + e);
        }
        AbstractDeviceParser deviceParser = new DeviceBuilderFactory().createDeviceParser(typeParser);
        deviceParser.createListDevices(fileUrl);
        req.setAttribute("devices", deviceParser.getDevices().getDevice());
        doGet(req, resp);
    }
}