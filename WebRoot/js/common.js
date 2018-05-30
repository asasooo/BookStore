function _change() {
	$("#vCode").attr("src","/goods/verCodeServlet?time="+new Date().getTime());
}