
 <a name="title"/>传智播客JavaWeb网上书城项目(基于MVC模式)<a name="text"/>
 _________________________________________________________________________
 

教学视频及资料 链接: https://pan.baidu.com/s/11WA3oHW7LztNH4t4jnyDAA 提取码: wypw

-这个项目的邮箱验证功能不可用-

 ﻿主要内容

根据需求分析与系统功能设计目标，结合实际情况本系统功能模块设计分为如下几个模块：

网上书城主要功能如下：

(1)	前台（客户购买）部分：

①	用户管理：注册会员、登录、激活、退出、修改密码；

②	分类显示：显示所有1级和2级分类；

③	图书显示：按分类查询图书、通过关键字搜索图书、高级搜索图书、查看某本图书的详细；

④	购物车管理：向购物车中添加图书、修改购物车中图书数量、删除购物车中图书、我的购物车；

⑤	订单管理：通过购物车中图书生成订单、查看我的订单、查看某个订单的详细、订单支付、确认收货、取消未付款订单。

(2)	后台（管理员管理）部分：

①	分类管理：查看所有分类、添加1级分类、添加2级分类、修改1级分类、修改2级分类、删除1级分类、删除2级分类；

②	图书管理：按分类搜索图书、高级搜索图书、添加新图书、查看图书详细信息、编辑图书、删除图书；

③	订单管理：按状态搜索订单、查看订单详细信息、取消订单、发货；

—————————————————————————————
![Image text](https://github.com/asasooo/BookStore/blob/master/1.png)
![Image text](https://github.com/asasooo/BookStore/blob/master/2.png)
![Image text](https://github.com/asasooo/BookStore/blob/master/3.png)
![Image text](https://github.com/asasooo/BookStore/blob/master/4.png)
![Image text](https://github.com/asasooo/BookStore/blob/master/6.png)
![Image text](https://github.com/asasooo/BookStore/blob/master/5.png)
我的总结：
   
   	线程池 一定要归还！！！！！ 把你大爷坑惨了 找了半天 发现是线程没有归还 线程池不够用了

	MySql有外键的时候 不能直接删除 需要从表关系中进行修改		

	javaMail还没有解决 163的授权有问题一直没法发邮件 （所以新用户也就无法激活 - -，
	注册的异步请求好像也有问题。。（划掉）

	用get方法传方法名时 多打了一个 空格 坑死我了。。。

	连接url时 防止编码出错  使用<c:url > 标签进行url编码

错误1:
已经为元素 "web-app" 指定属性 "xmlns"

项目过程中难免会碰到奇葩的事情，本身一个项目运行好好的，突然有一天，报了个错，已经为元素 “web-app” 指定属性 “xmlns”。 
找来找去，找不到问题所在，最后发现问题很简单，却折磨死人。

<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns="http://java.sun.com/xml/ns/javaee"   xmlns="http://java.sun.com/xml/ns/javaee"
xmlns:web="http://java.sun.com/xml/ns/javaee"
xsi:schemaLocation="http://java.sun.com/xml/ns/javaeehttp://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
 id="WebApp_ID" version="2.5">

这个文件里的
xmlns="http://java.sun.com/xml/ns/javaee"
重复了，删掉就可以了。


