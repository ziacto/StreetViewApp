<html>
<head>
<title>Random StreetView picture app</title>
</head>
<?php
$location = "55.7097657,12.5613489";
$fov = "60";
$heading = "235";
$pitch = "10";
if(isset($_GET['location']))
{
	$location = $_GET["location"];
}
if(isset($_GET['fov']))
{
	$fov = $_GET["fov"];
}
if(isset($_GET['heading']))
{
	$heading = $_GET["heading"];
}
if(isset($_GET['pitch']))
{
	$pitch = $_GET["pitch"];
}


?>
<body>
<h1>Random StreetView picture app</h1>
<img src="http://maps.googleapis.com/maps/api/streetview?size=600x400&location=<?php echo $location ;?>&fov=<?php echo $fov ;?>&heading=<?php echo $heading ;?>&pitch=<?php echo $pitch ;?>&sensor=false";?>"/>
<form>
Location: <input type="text" name="location" value="<?php echo $location ;?>"><br/>
Field of View: <input type="text" name="fov" value="<?php echo $fov ;?>"><br/>
Heading: <input type="text" name="heading" value="<?php echo $heading ;?>"><br/>
Pitch: <input type="text" name="pitch" value="<?php echo $pitch ;?>"><br/>
<input type="submit" value="Submit">
</form>
<body>
</html>