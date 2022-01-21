
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>영문 이름 변환 서비스</title>
    </head>

    <body>
        <h2> 한글 이름을 입력하세요 </h2>
        
        <form name="search">
            <input type="text" name="k_name">
			<input type="button" value="검색" onclick="fn_search();">
        </form>
        
        <form action="UpdateServlet" name="update" onsubmit="return false">
        	<p> 검색한 이름 :  <%=request.getAttribute("k_name") %></p><br>
  			<input type="radio" id="html" name="e_name" value='<%=request.getAttribute("redis_name") %>'>
				<%=request.getAttribute("redis_name") %><br>
  			<input type="radio" id="css" name="e_name" value='<%=request.getAttribute("naver_name") %>'>
  				<%=request.getAttribute("naver_name") %><br>
			<input type="hidden" name="k_name" value='<%=request.getAttribute("k_name") %>'>
			<input type="button" value="저장" onclick="fn_update();">
		</form>
    </body>
</html>

<script type="text/javascript">
	var k_name;
	
    function fn_search(){
		var search = document.search;
		k_name = search.k_name.value;
		
		if((k_name.length == 0 || k_name == "")){
			alert("성함을 입력해주세요.");
		}else{
			search.method = "get";
			search.action = "SearchServlet";
			search.submit();				<!-- 자바스크립트에서 서블릿으로 전송-->
		}
    }
    
    function fn_update(){
		var update = document.update;
		e_name = update.e_name.value;
		k_name = update.k_name.value;
		
		if((e_name == 'null' || k_name == null)){
			alert("null값은 입력할 수 없습니다.");
		}else{
			update.method = "get";
			update.action = "UpdateServlet";
			update.submit();	
			
			alert("저장되었습니다.");		<!-- 자바스크립트에서 서블릿으로 전송-->
		}
    }


</script>