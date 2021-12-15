<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>更新界面</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
	</head>

	<body>
		<div id="addAll">
			<div id="nav">
				<p>商品管理>更新商品</p>
			</div>
			<script type="text/javascript">
				$(function (){
					$.ajax({
						url:"${pageContext.request.contextPath}/type/ajaxType.action",
						type:"get",
						dataType:"json",
						success:function (resp){
							$.each(resp,function (n,i){
								$("#typeSelect").append("<option value='"+i.typeId+"'>"+i.typeName+"</option>");
							})
							$("#typeSelect option").each(function (){
								if($(this).val() == ${prod.typeId}){
									$(this).attr("selected","selected")
								}
							})
						}
					})
				})

				function fileChange(){
					$.ajaxFileUpload({
						url:"${pageContext.request.contextPath}/prod/ajaxImag.action",
						secureuri:false,
						fileElementId:"pimage",
						dataType:"text/plain",
						success:function (resp){
							var imgObj = $("<img>");
							imgObj.attr("src","${pageContext.request.contextPath}/image_big/"+resp);
							imgObj.attr("height","100px");
							imgObj.attr("weight","100px");
							$("#imgDiv").empty().append(imgObj);
							$("#imageName").val(resp);
						}
					});
				}
			</script>

			<div id="table">
				<form action="${pageContext.request.contextPath}/prod/update.action" enctype="multipart/form-data" method="post" id="myform">
					<input type="hidden" value="${prod.pId}" name="pId">
					<input type="hidden" value="${prod.pImage}" name="pImage" id="imageName">
					<input type="hidden" value="${prod.pDate}" name="pDate">

					<table>
						<tr>
							<td class="one">商品名称</td>
							<td><input type="text" name="pName" class="two" value="${prod.pName}"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="pnameerr"></span></td>
						</tr>
						<tr>
							<td class="one">商品介绍</td>
							<td><input type="text" name="pContent" class="two" value="${prod.pContent}"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="pcontenterr"></span></td>
						</tr>
						<tr>
							<td class="one">定价</td>
							<td><input type="number" name="pPrice" class="two" value="${prod.pPrice}"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="priceerr"></span></td>
						</tr>
						
						<tr>
							<td class="one">图片介绍</td>
							<td> <br><div id="imgDiv" style="display:block; width: 40px; height: 50px;"><img src="${pageContext.request.contextPath}/image_big/${prod.pImage}" width="100px" height="100px" ></div><br><br><br><br>
								<input type="file" id="pimage" name="pimage" onchange="fileChange()">
								<%--<span id="imgName"></span><br>--%>
							</td>
						</tr>
						<tr class="three">
							<td class="four"></td>
							<td><span></span></td>
						</tr>
						
						<tr>
							<td class="one">总数量</td>
							<td><input type="number" name="pNumber" class="two"  value="${prod.pNumber}"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="numerr"></span></td>
						</tr>

						<tr>
							<td class="one">类别</td>
							<td>
								<select name="typeId" id="typeSelect">

								</select>
							</td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span></span></td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="提交" class="btn btn-success">
							</td>
							<td>
								<input type="reset" value="取消" class="btn btn-default" onclick="myclose()">
								<script type="text/javascript">
									function myclose() {
										window.location="${pageContext.request.contextPath}/prod/split.action";
									}
								</script>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>