/**
 * 找到错误信息 循环遍历
 * 决定是否显示信息
 */

$(function(){
	$(".labelError").each(function(){
		showError($(this));
	});
	//切换注册按钮的图片
	$(".btn").hover(
		function(){
			$(".btn").attr("src","/goods/images/regist2.jpg");
		},
		function(){
			$(".btn").attr("src","/goods/images/regist1.jpg");
		});
	$(".verifyCodeError").blur(function(){
		
		$(".labelErrorCode").text("");
	});
	
	$(".input").focus(function(){
		var errorLabel = $(this).attr("id")+"Error";//找到对应的Error框
		$("#"+errorLabel).text(""); //清空内容
		showError($("#"+errorLabel));//不显示内容
	});
	
	/**
	 * 输入框失去焦点的js函数
	 */
	$(".input").blur(function(){
		var inputId = $(this).attr("id");
		var methodName = "check"+inputId.substring(0,1).toUpperCase()+inputId.substring(1)+"()";
		eval(methodName); //根据名称执行方法
	});
});

/**
 * 对输入的内容进行验证
 */
function checkLoginname(){
	//校验长度 非空 是否被注册
	var value = $("#loginname").val();
	if(!value){
		//获取对应label添加错误信息 显示label
		$("#loginnameError").text("用户名不能为空哦！");
		showError($("#loginnameError"));//一定不能忘了加#
		return false;
	}
	if(value.length<3||value.length>15){
		$("#loginnameError").text("用户名长度应在3~15字符之间！");
		showError($("#loginnameError"));
		return false;
	}
	$.ajax({
		url:"/goods/UserServlet?time="+new Date().getTime(),
		data:{method:"ajaxValidateLoginname",loginname:value},
		type:"POST",
		dataType:"json",
		asycn:false,//true是异步 不会等待相应就向下运行
		cache:false,
		success:function(res){
			if(!res){
				$("#loginnameError").text("这个用户名已经被注册了！");
				showError($("#loginnameError"));
				return false ; 
			}
		}
	});
	return true ;
}

function checkLoginpass(){
	var pass = $("#loginpass").val();
	if(!pass){
		$("#loginpassError").text("这里不能为空哦！");
		showError($("#loginpassError"));
		return false;
	}
	if(pass.length<8||pass.length>25){
		$("#loginpassError").text("密码长度要在8~25之间！");
		showError($("#loginpassError"));
		return false;
	}
	return true ;
}

function checkEmail(){
	var email = $("#email").val();
	if(!email){
		$("#emailError").text("请填写email！");
		showError($("#emailError"));
		return false;
	}
	//对格式的校验
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(email)){
		$("#emailError").text("email格式不正确！");
		showError($("#emailError"));
		return false;
	}
//	$.ajax({
//		url:"/goods/UserServlet?time="+new Date().getTime(),
//		data:{method:"ajaxValidateEmail",email:value},
//		type:"POST",
//		dataType:"json",
//		asycn:false,//true是异步 不会等待相应就向下运行
//		cache:false,
//		success:function(res){
//			if(!res){
//				$("#emailError").text("这个email已经被用过了！");
//				showError($("#emailError"));
//				return false ; 
//			}
//		}
//	});
	
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateEmail", email:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
		cache:false,
		success:function(result) {
			if(!result) {//如果校验失败
				$("#emailError").text("Email已被注册！");
				showError($("#emailError"));
				return false;
			}
		},
	});
	return true ;
}

function checkReloginpass(){
	var rePass = $("#reloginpass").val();
	if(!rePass){
		$("#reloginpassError").text("这里不能为空哦！");
		showError($("#reloginpassError"));
		return false ;
	}
	if(rePass!=$("#loginpass").val()){
		$("#reloginpassError").text("两次密码不一样 --.");
		showError($("#reloginpassError"));
		return false ;
	}
	return true ;
}

//验证码的校验
function checkVerifyCode(){
	var vercode = $("#verifyCode").val();
	if(!vercode){
		$("#verifyCodeError").text("验证码必须填写哦！");
		showError($("#verifyCodeError"));
		false ;
	}
//	$.ajax({
//		url:"/goods/UserServlet?time="+new Date().getTime(),
//		data:{method:"ajaxValidateCode",verifyCode:value},
//		type:"POST",
//		dataType:"json",
//		asycn:false,//true是异步 不会等待相应就向下运行
//		cache:false,
//		success:function(res){
//			if(!res){
//				$("#verifyCodeError").text("验证码不正确哦！");
//				showError($("#verifyCodeError"));
//				return false ; 
//			}
//		}
//	});
	
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateCode", verifyCode:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
		cache:false,
		success:function(result) {
			if(!result) {//如果校验失败
				$("#verifyCodeError").text("验证码错误！");
				showError($("#verifyCodeError"));
				false;
			}
		},
	});
	return true ;
}


function showError(ele){
	 var tex = ele.text(); //获取error信息
	 if(!tex){
		 ele.css("display","none");
	 }else{
		 ele.css("display","");
	 }
}

function _launchCode(){
	$("#vCode").attr("src","/goods/verCodeServlet?time="+new Date().getTime());
}

