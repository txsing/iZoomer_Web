package com.txsing.izoomer.web;

import com.txsing.izoomer.logic.ImageScaleTestStub;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.lxh.smart.Request;
import org.lxh.smart.SmartUpload;
import org.lxh.smart.SmartUploadException;

/**
 *
 * @author Simon
 */
public class Upload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //public final String SAVE_FOLDER = this.getServletContext().getRealPath("/");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException {
        PrintWriter out = response.getWriter();
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setForcePhysicalPath(true);
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        // set upload limitation
        // limit the maximal size of each uploaded file to 10M.
        mySmartUpload.setMaxFileSize(10000000);
        String SAVE_FOLDER = this.getServletContext().getRealPath("/")+"images/zoom/";
        try {
            mySmartUpload.upload();
        } catch (SmartUploadException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }

        //read parameters in
        org.lxh.smart.Request req = mySmartUpload.getRequest();

        String ratio = req.getParameter("ratio");
        int ratioNum = 3;
        if (ratio != null && !ratio.trim().equals("")) {
            ratioNum = Integer.parseInt(ratio);
        }

        //save uplodaed file to the server and sent it to zoom process.
        File outputFile = null;
        for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {

            org.lxh.smart.File file = mySmartUpload.getFiles().getFile(i);

            if (file.isMissing()) {
                continue;
            }
            try {
                file.saveAs(SAVE_FOLDER + file.getFileName());
                File oldFile = new File(SAVE_FOLDER + file.getFileName());
                BufferedImage bi = new ImageScaleTestStub().ImageZoom(oldFile, ratioNum);
                outputFile = new File(SAVE_FOLDER, file.getFileName() + "_zoomed.jpg");
             
                ImageIO.write(bi, "jpg", outputFile); 
            } catch (Exception e) {
                out.println(e.getMessage());
            }

            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1    
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0    
            response.setDateHeader("Expires", 0); //prevents caching at the proxy server    

            response.sendRedirect("result.jsp?image=" + outputFile.getName() 
                    + "&imagesrc=" + file.getFileName() + "&savedir="+SAVE_FOLDER);
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
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
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
