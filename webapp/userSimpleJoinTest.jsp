<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<b><font color="red">* 무조건 개발서버 (http://211.188.207.142/sp_sac/member/user/createBySimple/qa) 호출 합니다.</font></b>
    <form action="http://211.188.207.142/sp_sac/member/user/createBySimple/qa" method="get">
        <table border="1">
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
                <td colspan="2"><input type="submit" value="SimpleJoin" /></td>
            </tr>
        </table>
    </form>

</body>
</html>
