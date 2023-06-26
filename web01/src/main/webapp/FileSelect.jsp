<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document Retrieval</title>
	<script type="text/javascript">
	function selectFolder() {
	    var fileChooser = document.getElementById("fileChooser");
	    fileChooser.click();
	}

	function loadFolders() {
   		var selectedDir = document.getElementById("fileChooser").files[0];
    	var xmlhttp = new XMLHttpRequest();
    	xmlhttp.onreadystatechange = function() {
        	if (this.readyState === 4 && this.status === 200) {
            	document.getElementById("folderList").innerHTML = this.responseText;
        	}
    	};
    	xmlhttp.open("POST", "DiskExplorerServlet?selectedDir=" + selectedDir, true);
    	xmlhttp.send();
	}

	function exploreFolder(folderPath) {
	    var xmlhttp = new XMLHttpRequest();
   	 	xmlhttp.onreadystatechange = function() {
  	      	if (this.readyState === 4 && this.status === 200) {
  	          	document.getElementById("folderList").innerHTML = this.responseText;
  	      	}
 	   	};
	    xmlhttp.open("GET", "DiskExplorerServlet?folderPath=" + folderPath, true);
	    xmlhttp.send();
	}

	function exploreFiles(filePath) {
    	var xmlhttp = new XMLHttpRequest();
    	xmlhttp.onreadystatechange = function() {
 	       if (this.readyState === 4 && this.status === 200) {
	            document.getElementById("result").innerHTML = this.responseText;
	        }
	    };
	    xmlhttp.open("GET", "DiskExplorerServlet?filePath=" + filePath, true);
 	   xmlhttp.send();
	}
	</script>
</head>
<body>
	<h1>Document Retrieval</h1>
    <input type="file" id="fileChooser" style="display:none;" onchange="loadFolders()">
	<button onclick="selectFolder()">Choose File</button>
	<br><br>
	<div id="folderList"></div>
    <br>
    <button onclick="window.history.back()">return last</button>
    <button onclick="window.location.href='DiskExplorerServlet?action=confirm'">confirm</button>
    <br><br>
    <div id="result"></div>
</body>
</html>
