<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>

<script type="text/javascript">


function go() {
	
  var url = document.getElementById("url").value;
  
  document.getElementById("form").action = "http://" + url + "/member/user/createBySimple/qa";
  document.getElementById("form").submit();  

}

</script>

<body>
* 서버 (http://<b><font color="red">{URL}</font></b>/member/user/createBySimple/qa) 호출 합니다.
    <form name="form" id="form" method="get">
        <table border="1">
            <tr>
                <td>URL</td>
                <td><input type="text" id="url" value="211.188.207.142/sp_sac" /></td>
            </tr>
            <tr>
                <td>userId</td>
                <td><input type="text" name="userId" value="" /></td>
            </tr>
            <tr>
                <td>userPw</td>
                <td><input type="text" name="userPw" value="" /></td>
            </tr>
            <tr>
                <td>userEmail</td>
                <td><input type="text" name="userEmail" value="" /></td>
            </tr>
            <tr style="text-align: center;">
                <td colspan="2"><input type="button" value="SimpleJoin" onclick="go();" /></td>
            </tr>
        </table>
    </form>

</body>
</html>
