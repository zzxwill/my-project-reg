<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 12-1-9
  Time: 下午4:30
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" %>
 <script type="text/javascript">
        function search()
        {
           var searchValue=document.getElementById("BlurSearch");
            if(searchValue.value=="")
            return;
            else
            window.location.href="/registerinfo/blurSearch.do?value="+encodeURI(encodeURI(searchValue.value));
        }
    </script>
<div id="header">
<div id="header_word">
<img src="${pageContext.request.contextPath}/images/header_word.jpg" />
<div id="search">
  <input type="text" id="BlurSearch"  style=" margin-right:3px;"/>
    <a href="javascript:search();">搜索</a>
    <%--<input type="button" onclick="search()" value="Go" />--%>
</div>
    <div id="search_picture">
<a href="${pageContext.request.contextPath}/registerinfo/searchRegisterInfor.do"><img src="${pageContext.request.contextPath}/images/search.png"/></a></div>
</div>
</div>
