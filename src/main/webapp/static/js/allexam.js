var page=1;
var cur=1;
var totalpage;
var flag;
var size=8;
var showpage=5;
var currentexam;
	$(function(){
		function fenye3(){
			$("#b").append("<button class='bs btn btn-default' id='first'>" + "首页" + "</button>");
			$("#b").append("<button class='bs btn btn-default' id='pre'>" + "上一页" + "</button>");
			if(showpage>totalpage){
				for (var i = 1; i < totalpage+1; i++) {
					if (cur==i) {
						$("#b").append("<button class='bs btn btn-default' disabled='disabled'>" + cur + "</button>");
					}else{
						$("#b").append("<button class='bs btn btn-default'>" + i + "</button>");
					}
				}
			}else{
				if (cur<=Math.round(showpage/2)) {
					for (var j = 1; j < showpage+1; j++) {
						if (cur==j) {
							$("#b").append("<button class='bs btn btn-default' disabled='disabled'>" + cur + "</button>");
						}else{
							$("#b").append("<button class='bs btn btn-default'>" + j + "</button>");
						}
					}
				}else if(cur>(totalpage-Math.round(showpage/2))){
					for (var k = totalpage-showpage+1; k < totalpage+1; k++) {
						if (cur==k) {
							$("#b").append("<button class='bs btn btn-default' disabled='disabled'>" + cur + "</button>");
						}else{
							$("#b").append("<button class='bs btn btn-default'>" + k + "</button>");
						}
					}
				}else{
					for (var l = cur-parseInt(showpage/2); l < cur+parseInt(showpage/2)+1; l++) {
						if (cur==l) {
							$("#b").append("<button class='bs btn btn-default' disabled='disabled'>" + cur + "</button>");
						}else{
							$("#b").append("<button class='bs btn btn-default'>" + l + "</button>");
						}
					}
				}
			}
			$("#b").append("<button class='bs btn btn-default' id='next'>" + "下一页" + "</button>");
			$("#b").append("<button class='bs btn btn-default' id='last'>" + "尾页" + "</button>");
			if (cur==1) {
				$("#first").attr("disabled",true);
				$("#pre").attr("disabled",true);
			}
			if(cur==totalpage){
				$("#last").attr("disabled",true);
				$("#next").attr("disabled",true);
			}
		}
		$.ajax({
			url : 'queryallexam',
			type : 'post',
			dataType : 'json',
			data : {
				'start' : 1,
				'size' : 8
			},
			success : function(data) {
				totalpage = data.total;
				var exams = data.exams;
				fenye3();
				$.each(exams, function(i, v) {
					$("#a").append('<div class="col-md-3" style="margin: 5px 0px 5px 0px; height: 100%; border: 1px solid"><div style="width: 100%; height: 140px; margin: 20px">'+v.ename+'<br>'+'考试开始时间：'+v.e_starttime+'<br>'+'考试结束时间：'+v.e_endtime+'<br>'+'考试总分：'+v.e_total+'<br>'+'<button class="aa btn btn-primary" data-toggle="modal" data-target="#myModal" id='+v.ename+'>报名</button>'+'</div></div>')
				})
			}
		})
		$('#b').on(
				'click',
				'.bs',
				function() {
					if ($(this).text()=="上一页") {
						page = cur-1;
					}else if ($(this).text()=="下一页") {
						page = cur+1;
					}else if ($(this).text()=="首页") {
						page = 1;
					}else if ($(this).text()=="尾页") {
						page = totalpage;
					}else{
						page = $(this).text();
					}
					$.ajax({
						url : 'queryallexam',
						type : 'post',
						data : {
							'start' : page,
							'size' : size
						},
						dataType : 'json',
						success : function(data) {
							cur = page;
							var exams = data.exams;
							$("#a").empty();
							$("#b").empty();
							$.each(exams, function(i, v) {
								$("#a").append('<div class="col-md-3" style="margin: 5px 0px 5px 0px; height: 100%; border: 1px solid"><div style="width: 100%; height: 140px; margin: 20px">'+v.ename+'<br>'+'考试开始时间：'+v.e_starttime+'<br>'+'考试结束时间：'+v.e_endtime+'<br>'+'考试总分：'+v.e_total+'<br>'+'<button class="aa btn btn-primary" data-toggle="modal" data-target="#myModal" id='+v.ename+'>报名</button>'+'</div></div>')
							})
							fenye3();
						}
					})
				})
		$('#a').on(
				'click',
				'.aa',
				function() {
					$("#major").append(this.id+"考试");
					currentexam = this.id;
				})
		$("#cancel").click(function(){
				 $("#major").empty();
		});
		$("#confirm").click(function(){
			$.ajax({
				url : 'adduserexam',
				type : 'post',
				data : {
					'exam' : currentexam,
					'uname' : 'bbb'
				},
				dataType : 'json',
				success : function(data) {
					var flag = data.flag;
					if (flag) {
						alert('成功报名')
					}else{
						alert('之前已报名过')
					}
				}
			})
			$("#major").empty();
		});
	})