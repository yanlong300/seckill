<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="common/head.jsp"%>
    <title>秒杀详情页</title>
</head>
<body>
<%--页面显示部分--%>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center"><h1>${seckill.name}</h1></div>
        <div class="panel-body">
            <h2 class="text-danger text-center">
                <span class="glyphicon glyphicon-time"></span>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>

    </div>
</div>
<div id="killPhoneModel" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone">填写电话</span>
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sx-8 col-sx-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="请填写手机号" class="form-control"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span class="glyphicon" id="killPhoneMessage"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-info">
                    <span class="glyphicon glyphicon-phone"></span>提交
                </button>
            </div>
        </div>
    </div>
</div>

</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 交互逻辑 -->
<script type="text/javascript" src="${ctx}/resources/script/seckill.js"></script>
<script type="text/javascript">
    $(function(){
        secKill.detail.init({
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},
            endTime : ${seckill.endTime.time},

        });
    });

</script>
</body>
</html>