<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<div id="content">
    <div id="form">
        <div id="form-title">
            <span><a href="<%=request.getContextPath()%>/account/signup" style="color: red;">SIGN UP</a></span>
            <span><a href="<%=request.getContextPath()%>/account/signin">SIGN IN</a></span>
        </div>
        <div id="form-content">
            <form action="signup" method="post">
                <label>Company name<span style="color: red;">*</span></label><br/>
                <input type="text" name="txtCopName" value="${CompanyName}"/><br/>
                <c:if test="${msgCPN!=null}">
                    <div class="msg-error">
                        <c:out value="${msgCPN}"/>
                    </div>
                </c:if>
                <label>Contact name<span style="color: red;">*</span></label><br/>
                <input type="text" name="txtName" value="${ContactName}"/><br>
                <c:if test="${msgCN!=null}">
                    <div class="msg-error">
                        <c:out value="${msgCN}"/>
                    </div>
                </c:if>
                <label>Contact title<span style="color: red;">*</span></label><br/>
                <input type="text" name="txtTitle" value="${ContactTitle}"/><br/>
                <c:if test="${msgCT!=null}">
                    <div class="msg-error">
                        <c:out value="${msgCT}"/>
                    </div>
                </c:if>
                <label>Address<span style="color: red;">*</span></label><br/>
                <input type="text" name="txtAddress" value="${Address}"/><br/>
                <c:if test="${msgA!=null}">
                    <div class="msg-error">
                        <c:out value="${msgA}"/>
                    </div>
                </c:if>
                <label>Email<span style="color: red;">*</span></label><br/>
                <input type="text" name="txtEmail" value="${Email}"/><br/>
                <c:if test="${msgE!=null}">
                    <div class="msg-error">
                        <c:out value="${msgE}"/>
                    </div>
                </c:if>
                <label>Password<span style="color: red;">*</span></label><br/>
                <input type="password" name="txtPass" value="${Password}"/><br/>
                <c:if test="${msgP!=null}">
                    <div class="msg-error">
                        <c:out value="${msgP}"/>
                    </div>
                </c:if>
                <label>Re-Password<span style="color: red;">*</span></label><br/>
                <input type="password" name="txtRePass" "/><br/>
                <c:if test="${msgRP!=null}">
                    <div class="msg-error">
                        <c:out value="${msgRP}"/>
                    </div>
                </c:if>
                <c:if test="${err!=null}">
                    <div class="msg-error">
                        Can not create account!
                    </div>
                </c:if>
                <div></div>
                <input type="submit" value="SIGN UP" style="margin-bottom: 30px;"/>
            </form>
        </div>
    </div>
</div>
<%@include file="template/footer.jsp" %>