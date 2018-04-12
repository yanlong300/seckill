//js交互逻辑代码

//js的模块化

var secKill = {
    //封装秒杀相关ajax的地址
    URL : {
        now : function () {
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution : function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }

    },
    //验证手机号
    validatePhone:function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            console.log(true+phone);
            return true;
        }else{
            console.log(false+phone);
            return false
        }
    },
    //秒杀开始逻辑
    handleSeckillKill:function(seckillId,$node){
        $node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn" >开始秒杀</button>');
        $.post(secKill.URL.exposer(seckillId),{},function (result) {
            //回调函数中执行交互流程
            if(result && result['success']){
                var exposer  = result['data'];
                if(exposer['exposed']){
                    var md5 = exposer['md5'];
                    var killUrl = secKill.URL.execution(seckillId,md5);
                    $node.show();
                    //给按钮注册秒杀事件
                    //一次点击事件
                    $('#killBtn').one('click',function () {
                        //执行秒杀操作
                        $(this).addClass('disabled');
                        //发送秒杀请求
                        $.post(killUrl,{},function (result) {
                            console.log(result['data']);
                            if(result){
                                if(result['success']){
                                    var seckillResult = result['data'];
                                    var stateInfo = seckillResult['stateInfo'];
                                    //显示秒杀哦结果
                                    $node.html('<span class="label label-success">'+stateInfo+'</span>');
                                }else {
                                    var seckillResult = result['data'];
                                    var stateInfo = seckillResult['stateInfo'];
                                    console.log(seckillResult);
                                    //显示秒杀哦结果
                                    $node.html('<span class="label label-danger">'+stateInfo+'</span>');
                                }
                            }else{
                                console.log('result='+result);
                            }
                        });
                    })

                }else{
                    //未开启秒杀 客户端与服务器端时间差异
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end =exposer['end'];
                    secKill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log('result='+result);
            }
        });

    },
    //秒杀冷却计时
    countdown: function (seckillId,nowTime,startTime,endTime){
        var $seckillBox = $('#seckill-box');
        console.log($seckillBox);
        //时间的判断
        if(nowTime > endTime){
            //秒杀结束
            $seckillBox.html('秒杀结束');
        }else if(nowTime < startTime){
            //秒杀未开始
            var killTime = new Date(startTime + 1000);
            $seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                $seckillBox.html(format);
            }).on('finish.countdown',function(){
                //获取秒杀地址
                secKill.handleSeckillKill(seckillId,$seckillBox);
            });
        }else{
            //秒杀开始
            secKill.handleSeckillKill(seckillId,$seckillBox);
        }
    },


    //详情页秒杀逻辑
    detail : {
        //详情页初始化
        init : function(params){
            //用户手机验证和登录、计时交互
            var killPhone = $.cookie('killPhone');
            //验证手机号
            if(!secKill.validatePhone(killPhone)){
                //绑定手机号
                //控制输出
                var $killPhoneModel = $("#killPhoneModel");
                $killPhoneModel.modal({
                    //显示弹出层
                    show:true,
                    //禁止点击背景关闭
                    backdrop:'static',
                    //禁止键盘关闭
                    keyboard:false
                });
                $("#killPhoneBtn").click(function(){
                    var killPhone = $('#killPhoneKey').val();
                    if(secKill.validatePhone(killPhone)){
                        //添加cookie
                        $.cookie('killPhone',killPhone,{expires:7,path:'/seckill'})
                        //刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300)
                    }
                });
            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(secKill.URL.now(),{},function(result){
                if(result && result['success']){
                    var nowTime = result['data'];
                    //时间判断
                    secKill.countdown(seckillId,nowTime,startTime,endTime);

                }else{
                    console.log('result:'+result);
                }
            });
        }
    }
}