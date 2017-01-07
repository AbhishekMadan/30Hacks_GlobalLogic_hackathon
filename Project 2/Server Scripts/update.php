<?php
	$host='mysql1.000webhost.com';
	$uname='a6178447_test';
	$pwd='abc123';
	$db="a6178447_dbtest";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$item_id=$_REQUEST['item_id'];
	$item_type=$_REQUEST['item_type'];
        $con_hei=$_REQUEST['con_hei'];
	$con_wei=$_REQUEST['con_wei'];
        $rdate=$_REQUEST['rdate'];

        $flag['code']=0;

        $type='solid';

        if($item_type==$type)
{
	if($r=mysql_query("UPDATE db_item_list SET con_wei= '$con_wei' WHERE item_id ='$item_id'",$con))
	{
		if($r=mysql_query("UPDATE db_item_list SET rdate= '$rdate' WHERE item_id ='$item_id'",$con))
         {
              $flag['code']=1;
         }
	}
}
else
{
      	if($r=mysql_query("UPDATE db_item_list SET con_hei= '$con_hei' WHERE item_id ='$item_id'",$con))
{
                if($r=mysql_query("UPDATE db_item_list SET rdate= '$rdate' WHERE item_id ='$item_id'",$con))
	{
		$flag['code']=1;
	}
}
}
	 
	print(json_encode($flag));
	mysql_close($con);
?>