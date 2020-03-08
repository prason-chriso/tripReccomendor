<%@ page import="com.example.modal.TripSchedule" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.example.services.RouteManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.services.ReportManager" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.regex.Pattern" %>
<!DOCTYPE html>
<html>

<head>
    <!-- BASICS -->
    <meta charset="utf-8">
    <title>Trip Reccommender</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="./../res/js/rs-plugin/css/settings.css" media="screen">
    <link rel="stylesheet" href="./../res/css/flexslider.css" type="text/css">
    <link rel="stylesheet" href="./../res/js/fancybox/jquery.fancybox.css" type="text/css" media="screen">
    <link rel="stylesheet" href="./../res/css/bootstrap.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Serif:400,400italic,700|Open+Sans:300,400,600,700">

    <link rel="stylesheet" href="./../res/css/style.css">
    <!-- skin -->
    <link rel="stylesheet" href="./../res/css/default.css">
</head>

<body>
<%SimpleDateFormat dateNow = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat dateTimeNow = new SimpleDateFormat("yyyy/MM/dd h:mm a");
    SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a"); %>
<section id="header" class="appear"></section>
<div class="navbar navbar-fixed-top" role="navigation" data-0="line-height:100px; height:100px; background-color:rgba(0,0,0,0.3);" data-300="line-height:60px; height:60px; background-color:rgba(5, 42, 62, 1);">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="fa fa-bars color-white"></span>
            </button>
            <div class="navbar-logo">
                <a href="/"><img data-0="width:155px;" data-300=" width:120px;" src="./../res/img/logo.jpg" alt=""></a>
            </div>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav" data-0="margin-top:20px;" data-300="margin-top:5px;">
                <li class="active"><a href="/">Home</a></li>
                <li><a href="#section-about">Schedule</a></li>
                <li><a href="#section-report">Result</a></li>
                <li><a href="#section-contact">Contact</a></li>
            </ul>
        </div>
        <!--/.navbar-collapse -->
    </div>
</div>

<section id="intro">
    <div class="intro-content">
        <h2>Welcome to Nepal!</h2>
        <h3>Land of glory and the Natural Beauty</h3>
        <div>
            <a href="#section-services" class="btn-get-started scrollto">Get Started</a>
        </div>
    </div>
</section>
<!-- about -->
<section id="section-about" class="section appear clearfix">
    <div class="container">

        <div class="row mar-bot40">
            <div class="col-md-offset-3 col-md-6">
                <div class="section-header">
                    <h2 class="section-heading animated" data-animation="bounceInUp">Greetings Passenger</h2>
                    <p>Welcome to the Trip Selector. We Suggest the Best Ride for You From Destination "Kathmandu" to "Pokhara"
                        all round the year to make your travelling comfortable.</p>
                </div>
            </div>
        </div>

        <div class="row align-center mar-bot40">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-info">
                           Traveling schedule for <%= dateNow.format(new Date()) %></div>
                        <div class="alert alert-success" style="display:none;">
                            <span class="glyphicon glyphicon-ok"></span> Drag table row and cange Order</div>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>
                                   Bus Name
                                </th>
                                <th>
                                    Departure From 'Kathmandu'
                                </th>
                                <th>
                                    Arrival at 'Pokhara'
                                </th>
                                <th>
                                    Travel Time
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                RouteManager routeManager = new RouteManager();
                                ArrayList<TripSchedule> tripSchedules = routeManager.getSortedSchedule();
                                for(TripSchedule tripDetail : tripSchedules){
                                    try{
                            %>
                            <tr class="<% if (tripDetail.isComing(dateTimeNow.format(new Date()))>0) out.print("success"); else out.print("danger"); %>">

                                <%= "<td>"+tripDetail.getBusName()+"</td>"+
                                    "<td>"+timeOnly.format(dateTimeNow.parse(tripDetail.getDepartFromC()))+"</td>"+
                                        "<td>"+timeOnly.format(dateTimeNow.parse(tripDetail.getArrivalAtD()))+"</td>"+
                                        "<td>"+tripDetail.getTripLength()/60000+" Minute(s)</td>"   %>
                            </tr>
                                <% }catch (ParseException ex){
                                    System.out.println("exception on row creation ");
                                }
                                } // end of the for loop to generate the response of the table%>
                          </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- /about -->
<section id="section-report" class="section clearfix appear bg-white">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="section-header">
                        <h2 class="section-heading animated" data-animation="bounceInUp">Next Best Ride</h2>
                        <% ReportManager reportManager = routeManager.getReport();%>
                        <strong><h3><%= reportManager.getMsgToUser().replaceAll(Pattern.quote("\n"),"</br>") %></h3></strong>
                        <h5>Happy Journey !!! </h5>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- contact -->
<section id="section-contact" class="section appear clearfix">
    <div class="container">

        <div class="row mar-bot40">
            <div class="col-md-offset-3 col-md-6">
                <div class="section-header">
                    <h2 class="section-heading animated" data-animation="bounceInUp">Contact us</h2>
                    <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet consectetur, adipisci velit, sed quia non numquam.</p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="cform" id="contact-form">
                    <div id="sendmessage">Your message has been sent. Thank you!</div>
                    <div id="errormessage"></div>
                    <form action="" method="post" class="contactForm">

                        <div class="field your-name form-group">
                            <input type="text" name="name" placeholder="Your Name" class="cform-text" size="40" data-rule="minlen:4" data-msg="Please enter at least 4 chars">
                            <div class="validation"></div>
                        </div>
                        <div class="field your-email form-group">
                            <input type="text" name="email" placeholder="Your Email" class="cform-text" size="40" data-rule="email" data-msg="Please enter a valid email">
                            <div class="validation"></div>
                        </div>
                        <div class="field subject form-group">
                            <input type="text" name="subject" placeholder="Subject" class="cform-text" size="40" data-rule="minlen:4" data-msg="Please enter at least 8 chars of subject">
                            <div class="validation"></div>
                        </div>

                        <div class="field message form-group">
                            <textarea name="message" class="cform-textarea" cols="40" rows="10" data-rule="required" data-msg="Please write something for us"></textarea>
                            <div class="validation"></div>
                        </div>

                        <div class="send-btn">
                            <input type="submit" value="SEND MESSAGE" class="btn btn-theme">
                        </div>

                    </form>
                </div>
            </div>
            <!-- ./span12 -->
        </div>
    </div>
</section>

<section id="footer" class="section footer">
    <div class="container">
        <div class="row animated opacity mar-bot20" data-andown="fadeIn" data-animation="animation">
            <div class="col-sm-12 align-center">
                <ul class="social-network social-circle">
                    <li><a href="#" class="icoRss" title="Rss"><i class="fa fa-rss"></i></a></li>
                    <li><a href="#" class="icoFacebook" title="Facebook"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="#" class="icoTwitter" title="Twitter"><i class="fa fa-twitter"></i></a></li>
                    <li><a href="#" class="icoGoogle" title="Google +"><i class="fa fa-google-plus"></i></a></li>
                    <li><a href="#" class="icoLinkedin" title="Linkedin"><i class="fa fa-linkedin"></i></a></li>
                </ul>
            </div>
        </div>
        <div class="row align-center mar-bot20">
            <ul class="footer-menu">
                <li><a href="#">Home</a></li>
                <li><a href="#">About us</a></li>
                <li><a href="#">Privacy policy</a></li>
                <li><a href="#">Get in touch</a></li>
            </ul>
        </div>
        <div class="row align-center copyright">
            <div class="col-sm-12">
                <p>Copyright &copy; All rights reserved</p>
            </div>
        </div>
        <div class="credits">
            <!--
              All the links in the footer should remain intact.
              You can delete the links only if you purchased the pro version.
              Licensing information: https://bootstrapmade.com/license/
              Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/buy/?theme=Vlava
            -->
            Designed by <a href="https://bootstrapmade.com/">BootstrapMade.com</a>
        </div>
    </div>

</section>
<a href="#header" class="scrollup"><i class="fa fa-chevron-up"></i></a>

<!-- Javascript Library Files -->
<script src="./../res/js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script src="./../res/js/jquery.js"></script>
<script src="./../res/js/jquery.easing.1.3.js"></script>
<script src="./../res/js/bootstrap.min.js"></script>
<script src="./../res/js/jquery.nicescroll.min.js"></script>
<script src="./../res/js/fancybox/jquery.fancybox.pack.js"></script>
<script src="./../res/js/skrollr.min.js"></script>
<script src="./../res/js/jquery.scrollTo.min.js"></script>
<script src="./../res/js/jquery.localScroll.min.js"></script>
<script src="./../res/js/stellar.js"></script>
<script src="./../res/js/jquery.appear.js"></script>
<script src="./../res/js/jquery.flexslider-min.js"></script>

<!-- Contact Form JavaScript File -->
<script src="contactform/contactform.js"></script>

<!-- Template Main Javascript File -->
<script src="./../res/js/main.js"></script>

</body>

</html>
