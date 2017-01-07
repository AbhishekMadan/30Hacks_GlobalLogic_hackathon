<?php
	$host='mysql1.000webhost.com';
	$uname='a6178447_test';
	$pwd='abc123';
	$db="a6178447_dbtest";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$id=$_REQUEST['item_id'];
	$item_name=$_REQUEST['item_name'];
        $item_type=$_REQUEST['item_type'];
	$rdate=$_REQUEST['rdate'];
        $con_wei=$_REQUEST['con_wei'];
	$con_hei=$_REQUEST['con_hei'];
        $con_dai=$_REQUEST['con_dai'];


	$flag['code']=0;

	if($r=mysql_query("insert into db_item_list values('$id','$item_name','$item_type','$rdate','$con_wei','$con_hei','$con_dai')",$con))
	{
		$flag['code']=1;
	}

	print(json_encode($flag));
	mysql_close($con);
?>