<%-- 
    Document   : index
    Created on : 2013-4-29, 10:37:03
    Author     : 兴
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>iZoomer</title>
        <link rel="stylesheet" type="text/css" href="izoomer.css" />
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1    
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0    
            response.setDateHeader("Expires", 0); //prevents caching at the proxy server    
        %>

        <div id="allcontent">
            <div id="header">
                <img src="images/header.bmp" alt="iZoomer header image" />
            </div>
            <div id="header"> 
            </div>

            <div id="description">
                <h1>Your Best Friend</h1>
                <p>
                    There're many fleeting beautiful moments in our life that worth memorizing. 
                    Taking a photo ,which you may later want to zoom and hang in 
                    your room ,is necessary. While carrying a 
                    single-lens reflex camera anytime anywhere is unpractical,and the photos 
                    taken with your phone may be too low-rated to zoom without much distorting; 
                </p>
                <p>Want to make a stunning poster? 
                    Want to set your favorite picture as desktop background? However
                    your raw photo is so low-rated.
                </p>  
                <p>A low quality movie can become a nightmare when you play it in 
                    full screen, the annoying mosaic pixels are unbearable.
                </p>
                <p>What to do?</p>
                <span id="iZoomer"><p>Now，Seek for the help of the iZoomer</p></span>
            </div>

            <div id="function">
                <form action="Upload" enctype="MULTIPART/FORM-DATA" method=post>
                    Input Your Photo <input type="file" name="filename" /> 
                    <br/>
                    Input ratio(INTEGER ONLY )<br/>
                    <input type="text" name="ratio"  style="width: 33px" /> 
                    <br/>
                    <br/>
                    <input type="submit" value="upload" /> 
                </form>
            </div>
            <div id="contrast">
                <div id="BUAA">
                    <span id="example"><h2>Aktuelle Beispiele</h2></span>
                    <p>The badge of BUAA, zoomed by iZoomer, is recognizable.The 
                        circle edge of the badge is smooth compared with the sawtooth
                        of the badge edge zoomed directly;
                    </p>
                    <img src="images/3.bmp" alt="buaa_zoommed" />
                    <br/>
                    <br/>
                </div>

                <p>After zooming，the eye socket of the tiger is smooth and the
                    stripes of the fur is very clear too. While the image zoomed
                    directly is fuzzy with slightly serrated border</p>
                <img src="images/1.bmp" alt="tiger_zoommed" />



            </div>
            <br/>
            <br/>
            <br/>
            <div id="footer">
                <center>
                    <p>
                        &copy; 2013, BUAA SCSE 11061196 Tian Xing<br />
                        All trademarks and registered trademarks appearing on this site are 
                        the property of their respective owners.
                    </p>
                </center>
            </div>
        </div>
    </body>
</html>
