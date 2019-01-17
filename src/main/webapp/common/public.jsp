<%@page import="com.zip.action.BaseAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("path", BaseAction.getBaseUrl());%>
<META http-equiv="pragma" content="no-cache"> 
<META http-equiv="Cache-Control" content="no-cache, must-revalidate">
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="ie-comp|ie-stand">
<meta name="full-screen" content="yes">
<META http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<style>
* {
	font-size:14px/1.5;
	font-family:Microsoft Yahei;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-box-sizing: border-box;
	padding:0;
	margin:0;
	list-style:none;
	box-sizing: border-box;
}
body {
	background-color: white;
}

</style>
<!-- jquery -->
<script type="text/javascript" src="${path}/common/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/common/js/jquery.base64.js"></script>
<script type="text/javascript" src="${path}/common/js/jquery.timer.js"></script>
<script type="text/javascript" src="${path}/common/js/json2.js"></script>
<script type="text/javascript">
// 添加js的基础url路径
var pathPageBaseUrl = '${path}';
if (window.top != window.self && '${loginUser.USER_NAME }' == '') {
	window.top.location.href="${path }/sys/index.shtml";
}
String.prototype.replaceAll = function(s1,s2){
	return this.replace(new RegExp(s1, "gm"),s2);
}
function Format(now,mask){
    var d = now;
    var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    };
 
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0) {
        switch ($0) {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
            // Return quoted strings with the surrounding quotes removed
            default: return $0.substr(1, $0.length - 2);
        }
    });
};

function makeLogJson(msg) {
	var log = {};
	log.time = Format(new Date, "yyyy-MM-dd HH:mm:ss");
	log.msg = msg;
	return log;
}

function getWhere(id, sort) {
	var where = {};
	temp = $('#'+id).serializeArray();
	$.each(temp, function() {
        if (where[this.name] !== undefined) {
            if (!where[this.name].push) {
            	where[this.name] = [where[this.name] ];
            }
            where[this.name].push(encodeURI(this.value || ''));
        } else {
        	where[this.name] = encodeURI(this.value || '');
        }
    });
	defaultSort = sort;
	if (sort) {
		where['sort']=sort.field;
		where['order']=sort.type;
	}
	// 添加随机数，防止缓存
	where['r']=Math.random();
	return where;
}
function checkPhone(phone) {
	if ((/^1[34578]\d{9}$/.test(phone))) {
		return true;
	} else {
		return false;
	}
}
function checkTel(tel) {
	if (/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(tel)) {
		return true;
	} else {
		return false;
	}
}

function checkPost(post) {
	var re = /^[1-9][0-9]{5}$/
	if (re.test(post))
		return true;
	else {
		return false;
	}
}
function checkemail(str) {
	var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
	if (reg.test(str)) {
		return true;
	} else {
		return false;
	}
}
function clearForm(formId){
	$("#"+formId)[0].reset();
	$("#"+formId).children('select').each(function(){
		// 有默认值
		if ($(this).attr('initVal') && $.trim($(this).attr('initVal')) != '') {
			$(this).next().find('dd').each(function(index,elm){
				if (index == 0) {
					$(elm).click();
				}
			});
		} else {
			$(this).val('');
		}
		$(this).change();
	});
}
$(function(){
	$('select').each(function(){
		if ($(this).attr('initVal') && $.trim($(this).attr('initVal')) != '') {
			var initVal = $(this).attr('initVal');
			// 找到默认值，并触发layui的select选中事件
			$(this).next().find('dd').each(function(index,elm){
				if ($(elm).attr('lay-value') == initVal) {
					$(elm).click();
				}
			});
		}
	})
})
</script>