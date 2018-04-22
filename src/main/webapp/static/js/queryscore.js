	var page=1;
	var page2=1;
	var cur=1;
	var cur2=1;
	var totalpage;
	var totalpage2;
	var flag;
	var flag2;
	var size=3;
	var showpage=5;
	$(function() {
		//绑定回车事件
		$("#qstu").keydown(function(event){ 
			if(event.keyCode==13){ 
			$("#query1").click(); 
			} 
		})
		$("#qexam").keydown(function(event){ 
			if(event.keyCode==13){ 
			$("#query2").click(); 
			} 
		})
		//分页功能
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
		function fenye4(){
			$("#d").append("<button class='bs2 btn btn-default' id='first2'>" + "首页" + "</button>");
			$("#d").append("<button class='bs2 btn btn-default' id='pre2'>" + "上一页" + "</button>");
			if(showpage>totalpage2){
				for (var i = 1; i < totalpage2+1; i++) {
					if (cur2==i) {
						$("#d").append("<button class='bs2 btn btn-default' disabled='disabled'>" + cur2 + "</button>");
					}else{
						$("#d").append("<button class='bs2 btn btn-default'>" + i + "</button>");
					}
				}
			}else{
				if (cur2<=Math.round(showpage/2)) {
					for (var j = 1; j < showpage+1; j++) {
						if (cur2==j) {
							$("#d").append("<button class='bs2 btn btn-default' disabled='disabled'>" + cur2 + "</button>");
						}else{
							$("#d").append("<button class='bs2 btn btn-default'>" + j + "</button>");
						}
					}
				}else if(cur2>(totalpage2-Math.round(showpage/2))){
					for (var k = totalpage2-showpage+1; k < totalpage2+1; k++) {
						if (cur2==k) {
							$("#d").append("<button class='bs2 btn btn-default' disabled='disabled'>" + cur2 + "</button>");
						}else{
							$("#d").append("<button class='bs2 btn btn-default'>" + k + "</button>");
						}
					}
				}else{
					for (var l = cur2-parseInt(showpage/2); l < cur2+parseInt(showpage/2)+1; l++) {
						if (cur2==l) {
							$("#d").append("<button class='bs2 btn btn-default' disabled='disabled'>" + cur2 + "</button>");
						}else{
							$("#d").append("<button class='bs2 btn btn-default'>" + l + "</button>");
						}
					}
				}
			}
			$("#d").append("<button class='bs2 btn btn-default' id='next2'>" + "下一页" + "</button>");
			$("#d").append("<button class='bs2 btn btn-default' id='last2'>" + "尾页" + "</button>");
			if (cur2==1) {
				$("#first2").attr("disabled",true);
				$("#pre2").attr("disabled",true);
			}
			if(cur2==totalpage2){
				$("#last2").attr("disabled",true);
				$("#next2").attr("disabled",true);
			}
		}
		$('#d1').hide();
		$('#d2').hide();
		$('#myTab li:eq(0) a').tab('show');
		//查询一个学生所有成绩
		$('#query1').click(function() {
			$("#a").empty();
			$("#b").empty();
			$("#chdiv1").empty();
			$("#chdiv1").append("<canvas id='myChart' width='300' height='300'></canvas>");
			$.ajax({
				url : 'queryscore',
				type : 'post',
				data : {
					'sname' : $('#in1').val(),
					'query' : 1,
					'start' : 1,
					'size' : size
				},
				dataType : 'json',
				success : function(data) {
					$('#d1').hide();
					flag = data.flag;
					if(flag){
					cur = data.current;
					$("#a").append("<tr><td>用户姓名</td><td>考试名</td><td>分数</td></tr>");
					totalpage = data.total;
					var stus = data.students;
					var all1 = data.allscore;
					fenye3();
					var lab = new Array();
					var sco = new Array();
					$.each(all1, function(i, v) {
						lab[i] = v.ename;
						sco[i] = v.score; 
					})
					$.each(stus, function(i, v) {
						$("#a").append(
								"<tr><td>" + v.uname + "</td><td>" + v.ename
										+ "</td><td>" + v.score + "</td></tr>")
					})
					var chart1 = {
						labels :lab,
						datasets : [
						            {
						            	label: $('#in1').val()+'成绩',
						            	backgroundColor : "rgba(57,233,246,0.5)",
						            	borderColor : "rgba(220,220,220,1)",
						    			data : sco
						            }
						            ]
					}
					var ctx = document.getElementById("myChart").getContext("2d");
				    var myBarChart = new Chart(ctx, {
				                                        type: 'bar',
				                                        data: chart1,
				                                        options : {
				                                        	scales: {
				                                                yAxes: [{
				                                                    ticks: {
				                                                        beginAtZero:true
				                                                    }
				                                                }]
				                                            }
				                                        	}
				                                });
				}else{
					$('#d1').show();
				}
				}
			})
		});
		//点击分页触发
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
						url : 'queryscore',
						type : 'post',
						data : {
							'sname' : $('#in1').val(),
							'query' : 1,
							'start' : page,
							'size' : size
						},
						dataType : 'json',
						success : function(data) {
							cur = data.current;
							var stu = data.students;
							$("#a").empty();
							$("#b").empty();
							$("#a").append("<tr><td>用户姓名</td><td>考试名</td><td>分数</td></tr>");
							$.each(stu, function(i, v) {
								$("#a").append(
										"<tr><td>" + v.uname + "</td><td>" + v.ename
												+ "</td><td>" + v.score
												+ "</td></tr>")
							})
							fenye3();
						}
					})
				})
		//查询一门考试所有学生成绩
		$('#query2').click(function() {
			$("#c").empty();
			$("#d").empty();
			$("#chdiv2").empty();
			$("#chdiv2").append("<canvas id='myChart2' width='300' height='300'></canvas>");
			$.ajax({
				url : 'queryscore',
				type : 'post',
				data : {
					'ename' : $('#in2').val(),
					'query' : 2,
					'start' : 1,
					'size' : size
				},
				dataType : 'json',
				success : function(data) {
					$('#d2').hide();
					flag2 = data.flag2;
					if(flag2){
					cur2 = data.current;
					$("#c").append("<tr><td>考试名</td><td>用户姓名</td><td>分数</td></tr>");
					totalpage2 = data.total;
					var stus = data.students;
					var all2 = data.allscore2;
					fenye4();
					$.each(stus, function(i, v) {
						$("#c").append(
								"<tr><td>" + v.ename + "</td><td>" + v.uname
										+ "</td><td>" + v.score + "</td></tr>")
					})
					var s59=0;
					var s79=0;
					var s80=0;
					$.each(all2,function(i,v){
						if (v.score<60) {
							s59++;
						}else if(v.score<80&&v.score>=60){
							s79++;
						}else{
							s80++;
						}
					})
					var chart2 = {
						labels: [
						         '60分以下',
						         '60~79',
						         '80及以上'
						     ],
						datasets: [{
							backgroundColor:["#FF1CFD","#FD1414","#0A41F4"],
					        data: [s59, s79, s80]
					    }],
					}
					var ctx = document.getElementById("myChart2").getContext("2d");
				    var myBarChart = new Chart(ctx, {
				                                        type: 'pie',
				                                        data: chart2,
				                                });
				}else{
					$('#d2').show();
				}
				}
			})
		});
		//点击分页触发
		$('#d').on(
				'click',
				'.bs2',
				function() {
					if ($(this).text()=="上一页") {
						page2 = cur2-1;
					}else if ($(this).text()=="下一页") {
						page2 = cur2+1;
					}else if ($(this).text()=="首页") {
						page2 = 1;
					}else if ($(this).text()=="尾页") {
						page2 = totalpage2;
					}else{
						page2 = $(this).text();
					}
					$.ajax({
						url : 'queryscore',
						type : 'post',
						data : {
							'ename' : $('#in2').val(),
							'query' : 2,
							'start' : page2,
							'size' : size
						},
						dataType : 'json',
						success : function(data) {
							cur2 = data.current;
							var stu = data.students;
							$("#c").empty();
							$("#d").empty();
							$("#c").append("<tr><td>考试名</td><td>用户姓名</td><td>分数</td></tr>");
							$.each(stu, function(i, v) {
								//console.log(v);
								$("#c").append(
										"<tr><td>" + v.ename + "</td><td>" + v.uname
												+ "</td><td>" + v.score
												+ "</td></tr>")
							})
							fenye4();
						}
					})
				})
	})