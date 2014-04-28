<html>
<head>
<title>Random StreetView picture app</title>
</head>
<?php
$location = $_GET["location"]
$fov = $_GET["fov"]
$heading = $_GET["heading"]
$pitch = $_GET["pitch"]
if($location == null || $location == "")
{
	$location = "55.7097657,12.5613489"
}
if($fov == null || $fov == "")
{
	$fov = "60"
}
if($heading == null || $heading == "")
{
	$heading = "235"
}
if($pitch == null || $pitch == "")
{
	$pitch = "10"
}


?>
<body>
<h1>Random StreetView picture app</h1>
<img src="<?php echo "http://maps.googleapis.com/maps/api/streetview?size=600x400&location=" + $location + " &fov=" + $fov + "&heading=" + $heading + "&pitch=" + $pitch + "&sensor=false"?>"/>
<form>
Location: <input type="text" name="location" value="55.7086089,12.5610837"><br/>
Field of View: <input type="text" name="fov" value="60"><br/>
Heading: <input type="text" name="heading" value="235"><br/>
Pitch: <input type="text" name="pitch" value="10"><br/>
<input type="submit" value="Submit">
</form>
<body>
</html>