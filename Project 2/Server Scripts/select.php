<?php
	$host='mysql1.000webhost.com';
	$uname='a6178447_test';
	$pwd='abc123';
	$db="a6178447_dbtest";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");

	$array = array();

	$r=mysql_query("select * from db_item_list",$con);

        $i = 0;
	while($row=mysql_fetch_array($r))
	{
		 $array[$i++]=$row;
            
	}
        echo json_encode($array);
	
	mysql_close($con);
?>
