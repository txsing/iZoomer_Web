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
                    There're many beautiful moments in life worth remembering. 
                    Taking a photo ,that you may later want to zoom and hang in 
                    your room ,whenever it happens is necessary. While carring a 
                    single-lens reflex camera anytime is unpractical,the photos 
                    taken with your phone may be low-rated when you zoom them; 
                </p>
                <p>Want to make a stunning poster? 
                    Want to set your favorite picture as desktop background? However
                    your raw photo is so low-rated.
                </p>  
                <p>A low quality movie becomes a nightmare when you play it in 
                    full screen, eh? What? You can’t bear the colorful icons as 
                    the background either?
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
                        edge of the badge circle is smooth compared with the sawtooth
                        of the badge zoomed directly;
                    </p>
                    <img src="images/3.bmp" alt="buaa_zoommed" />
                    <br/>
                    <br/>
                </div>

                <p>After zooming，the eye socket of the tiger is smooth and the
                    stripes of the fur is very clear too. While the image zoomed
                    directly is fuzzy with a slightly serrated border</p>
                <img src="images/1.bmp" alt="tiger_zoommed" />



            </div>
            <br/>
            <br/>
            <br/>
            <div id="footer">
                <center>
                    <p>
                        &copy; 2013, BUAA SCSE 11061196<br />
                        All trademarks and registered trademarks appearing on this site are 
                        the property of their respective owners.
                    </p>
                </center>
            </div>
        </div>
    </body>
</html>
