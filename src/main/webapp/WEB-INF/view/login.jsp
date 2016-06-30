<%--
  Created by IntelliJ IDEA.
  User: vat
  Date: 23.06.16
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:url var="authUrl" value="/j_spring_security_check" />

<div class="row">
    <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
    <div class="login-panel panel panel-default">
        <div class="panel-heading">Log in</div>
        <c:if test="${error}"><div><c:out value="${message}" /></div></c:if>

        <div class="panel-body">

            <form role="form" name='login' class="signin" action="${authUrl}" method='POST'>
                <fieldset>
                    <div class="form-group">
                        <input class="form-control" placeholder="User name" type='text' id="j_username" name="username" autofocus="">
                    </div>

                    <div class="form-group">
                        <input class="form-control" placeholder="Password" name="password" type="password" id="j_password" value="">
                    </div>

                    <div class="checkbox">
                        <label>
                            <input name="remember" type="checkbox" value="Remember Me">Remember Me
                        </label>
                    </div>
                    <input name="submit" type="submit" class="btn btn-primary" value="submit" />
                </fieldset>
            </form>
        </div>
    </div>
    </div><!-- /.col-->
</div>
<csrf disabled="true"/>