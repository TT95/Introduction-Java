<%@page import="java.util.Random"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%!private void colorMethod(javax.servlet.jsp.JspWriter out)
			throws java.io.IOException {
		List<String> colors = Arrays.asList("blue", "red", "green", "pink");
		Random rand = new Random();
		String color = colors.get(rand.nextInt(4));
		out.print(color);
}%>
<%
String pickedColor = (String)request.getSession().getAttribute("pickedBgCol"); 
if(pickedColor == null) {
	pickedColor = "white";
}
%>
<html>
<body bgcolor="<%=pickedColor%>">
	<h1>Short funny story</h1>
	<font color=<%colorMethod(out);%>> A teacher asked a student,
		“Do you know the alphabet?”<br> The kid said no so the teacher
		said, “Well, tomorrow you gonna have to say the alphabet to me.”<br>
		The kid went home and asked his mom, “Mom, what’s the 1st letter of
		the alphabet?” His mom responded, “Sshhh I’m on the phone.”<br>
		The kid asked his dad, “Dad, what is the 2nd letter of the alphabet?”
		His dad said, “Yes!”<br> He then asked his sister, “What’s the
		3rd letter of the alphabet?” She said, “Michael Jackson. Michael
		Jackson.”<br> He then asked his little brother, “Bro, what’s the
		4th letter of the alphabet?” The little brother said, “Driving in my
		bruum bruum car. Driving in my broom broom car.”<br> The next
		day, the kid met the teacher, she asked, “What’s the 1st letter of the
		alphabet?”<br> The kid answered, “Sshhh, I’m on the phone.”<br>
		The teacher got angry and said, “Do you want to go to the principal
		office?<br> The kid responded, “Yes!”<br> The teacher said,
		“Who do you think you are?”<br> The kid said, “Michael Jackson.”<br>
		The teacher said, “How do you think you are going to get away with
		this…”<br> The kid said, “Driving in my bruum bruum car driving
		in my broom broom car.”<br> <br> <br> <br>
	</font>
	<a href='index.html'> &lt&lt Go back </a>
	<br>
</body>
</html>