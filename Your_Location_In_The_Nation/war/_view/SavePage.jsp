<!DOCTYPE html>
<html>
<head>
    <title>Saved</title>
    <style>
        .img-container{
            text-align: center;
        
        }
        body{
animation: transition 0.75s;
}
@keyframes transition {
    from{
opacity: 0;
transform: translate(-10px);

    }
  to{
    opacity: 1;
    transform: translate(0);
  }  
}

        </style>
</head>
<body>
    
 <a href="/Lab_1/welcomeG.html">
    <p>Home</p>
    </a>
<h1>Here are your saved locations: </h1>
<hr>
<c:if test="${not empty savedAreas}">
    <ul>
        <c:forEach var="area" items="${savedAreas}">
        
    (needs editing probably)        <li>${area}</li>
    
        </c:forEach>
    </ul>
</c:if>



</body>
</html>