	var page=1;
	var cur=1;
	var totalpage;
	var flag;
	var size=3;
	var showpage=5;
	var username;
$(function(){
	$("#mo").hide();
	$("#btn").click(function(){
        $("#c").table2excel({
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "myFileName",
            exclude_img: true,
            exclude_links: true,
            exclude_inputs: true
        });
        $("#c").empty();
    });
	$("#pri").click(function(){
		 $('#c').jqprint({  
             debug: false,            
             importCSS: true,         
             printContainer: true,    
             operaSupport: false      
         })  
	 });
	$("#cancel").click(function(){
       $("#c").empty();
    });
	$("#show").click(function(){
		$.ajax({
			url : 'queryscore',
			type : 'post',
			data : {
				'sname' : $("h3").text(),
				'query' : 1,
				'start' : 1,
				'size' : size
			},
			dataType : 'json',
			success : function(data) {
				console.log(data);
				$("#c").append("<tr><td>用户姓名</td><td>考试名</td><td>分数</td></tr>");
				var all1 = data.allscore;
				console.log(all1);
				$.each(all1, function(i, v) {
					$("#c").append(
							"<tr><td>" + v.uname + "</td><td>" + v.ename
									+ "</td><td>" + v.score + "</td></tr>")
				})
			}
		})
    });
	function fenye(){
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
		url : 'personinfo',
		type : 'post',
		data : {
			'sname' : $("h3").text()
		},
		dataType : 'json',
		success : function(data) {
			$("#uname").append(data.uname);
			$("#uemail").append(data.uemail);
			$("#utelephone").append(data.utelephone);
			$("#uregistertime").append(data.uregistertime);
			username = data.uname;
			$("#h3name").html(data.uname);
		}
	})
	$('#query').click(function() {
		$("#mo").show();
		$("#a").empty();
		$("#b").empty();
		$("#chdiv1").empty();
		$("#chdiv1").append("<canvas id='myChart' width='300' height='300'></canvas>");
		$.ajax({
			url : 'queryscore',
			type : 'post',
			data : {
				'sname' : $("#h3name").text(),
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
				fenye();
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
					            	label: $("h3").text()+'成绩',
					            	backgroundColor : "rgba(57,233,246,0.5)",
					            	borderColor : "rgba(220,220,220,1)",
					    			data : sco
					            }
					            ]
				}
				var ctx = document.getElementById("myChart").getContext("2d");
			    var myBarChart = new Chart(ctx, {
			                                        type: 'radar',
			                                        data: chart1,
			                                        options : {
			                                        	scale: {
			                                                    ticks: {
			                                                    	beginAtZero:true,
			                                                    	max: 100
			                                                    }
			                                            	}
			                                        	}
			                                });
			}else{
				$('#d1').show();
			}
			}
		})
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
						url : 'queryscore',
						type : 'post',
						data : {
							'sname' : $("h3").text(),
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
							fenye();
						}
					})
				})
})