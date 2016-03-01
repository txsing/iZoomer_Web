<%-- 
    Document   : result
    Created on : 2013-5-7, 12:43:58
    Author     : 兴
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>zoommed</title>
        <link rel="stylesheet" type="text/css" href="izoomer.css" />
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1    
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0    
            response.setDateHeader("Expires", 0); //prevents caching at the proxy server    
%>
        <%String imageLocation = request.getParameter("image");%>
        <%String imageSrc = request.getParameter("imagesrc");%>
        <%String savedir = request.getParameter("savedir");%>
        <h1>Now, You can get the perfect photo zoomed by iZoomer!</h1> 
        <div id="form">
            <form action="FeedBack" method=post>
                <p>After using iZoomer, How do you feel with it?
                    <br/>
                    <input type="radio" name="isContented" value="A">Could'nt be better!
                    <br/>
                    <input type="radio" name="isContented" value="B">Amazing
                    <br/>
                    <input type="radio" name="isContented" value="C">Just-so-so
                    <br/>
                    <input type="radio" name="isContented" value="D">Worse
                    <br/>
                    <input type="radio" name="isContented" value="F">I won't use it again!
                    <br/><br/>
                    Inform us if you have any detailed comments (suggestions or complaints),<br/>
                    Such will help us make iZoomer better</br>
                    <textarea name="Remarks" rows="5" cols="50"></textarea>
                </p>
                <p>
                    <input type="submit" value="Submit">
                </p>
            </form>
        </div>


        <div id="present"> 
             <img src="<%=savedir+imageSrc%>" alt="img" />
             <img src="<%=savedir+imageLocation%>" alt="img_zoomed" />
            <p>Right click to use save-as</p> 
        </div>

    </body>
</html>
