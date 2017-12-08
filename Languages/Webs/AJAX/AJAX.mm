<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1512625114357" ID="ID_1886482826" MODIFIED="1512625508700">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font size="5" color="#ff0033">XML AJAX</font>
    </p>
  </body>
</html></richcontent>
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1512625204943" FOLDED="true" ID="ID_212729508" MODIFIED="1512696306563" POSITION="left" TEXT="Introduction">
<icon BUILTIN="full-0"/>
<node CREATED="1512625176362" ID="ID_238231898" MODIFIED="1512625193879" TEXT="AJAX = Asynchronous JavaScript And XML"/>
<node CREATED="1512625244726" ID="ID_1867857521" MODIFIED="1512625252688" TEXT="Not a programming language"/>
<node CREATED="1512625281348" ID="ID_1744336027" MODIFIED="1512625285256" TEXT="Composition">
<node CREATED="1512625286493" ID="ID_1579188654" MODIFIED="1512625323743" TEXT="A browser built-in XMLHttpRequest object (to request data from a web server)"/>
<node CREATED="1512625324403" ID="ID_1627396497" MODIFIED="1512625344375" TEXT="JavaScript and HTML DOM (do display or use the data)"/>
</node>
<node CREATED="1512625370061" ID="ID_340188302" MODIFIED="1512625395806" TEXT="Tansport data using XML, plain txt and JSON text"/>
<node CREATED="1512625436665" FOLDED="true" ID="ID_271505582" MODIFIED="1512625505970" TEXT="How it works">
<node CREATED="1512625440900" MODIFIED="1512625440900">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <img src="AJAX_8392805886788346437.jpeg" />
  </body>
</html></richcontent>
</node>
</node>
</node>
<node CREATED="1512625510369" FOLDED="true" ID="ID_1747257774" MODIFIED="1512696308062" POSITION="right" TEXT="XMLHttp">
<icon BUILTIN="full-1"/>
<node CREATED="1512625558792" ID="ID_1231989198" MODIFIED="1512625587733" TEXT="All modern browsers support the XMLHttpRequest object."/>
<node CREATED="1512625604943" ID="ID_909105076" MODIFIED="1512625634379" TEXT="The XMLHttpRequest object can be used to exchange data with a server behind the scenes."/>
<node CREATED="1512626291850" FOLDED="true" ID="ID_1114211504" MODIFIED="1512632250340" TEXT="Demo">
<node CREATED="1512626294556" ID="ID_969198972" MODIFIED="1512626363304">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="SimSun">&lt;!DOCTYPE html&gt; </font>
    </p>
    <p>
      <font face="SimSun">&lt;html&gt; </font>
    </p>
    <p>
      <font face="SimSun">&lt;body&gt; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="SimSun">&lt;h1&gt;The XMLHttpRequest Object&lt;/h1&gt; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="SimSun">&lt;p id=&quot;demo&quot;&gt;Let AJAX change this text.&lt;/p&gt; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="SimSun">&lt;button type=&quot;button&quot; onclick=&quot;loadDoc()&quot;&gt;Change Content&lt;/button&gt; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="SimSun">&lt;script&gt; </font>
    </p>
    <p>
      <font face="SimSun">function loadDoc() { </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;var xhttp; </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;if (window.XMLHttpRequest) { </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;// code for modern browsers </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;xhttp = new XMLHttpRequest(); </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;} else { </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;// code for IE6, IE5 </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;xhttp = new ActiveXObject(&quot;Microsoft.XMLHTTP&quot;); </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;} </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;xhttp.onreadystatechange = function() { </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;if (this.readyState == 4 &amp;&amp; this.status == 200) { </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;&#160;&#160;document.getElementById(&quot;demo&quot;).innerHTML = this.responseText; </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;&#160;&#160;} </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;}; </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;xhttp.open(&quot;GET&quot;, &quot;ajax_info.txt&quot;, true); </font>
    </p>
    <p>
      <font face="SimSun">&#160;&#160;xhttp.send(); </font>
    </p>
    <p>
      <font face="SimSun">} </font>
    </p>
    <p>
      <font face="SimSun">&lt;/script&gt; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="SimSun">&lt;/body&gt; </font>
    </p>
    <p>
      <font face="SimSun">&lt;/html&gt;</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512626396286" FOLDED="true" ID="ID_1257716906" MODIFIED="1512632598927" TEXT="XMLHttpRequest Object Methods">
<node CREATED="1512626410086" FOLDED="true" ID="ID_360783404" MODIFIED="1512631698122" TEXT="new XMLHttpRequest()">
<node CREATED="1512631124181" ID="ID_288352328" MODIFIED="1512631134751" TEXT="Create a new XMLHttpReuqest object"/>
</node>
<node CREATED="1512626423445" ID="ID_1540136151" MODIFIED="1512626426825" TEXT="abort()">
<node CREATED="1512631138091" ID="ID_502928014" MODIFIED="1512631145519" TEXT="Cancels the current request"/>
</node>
<node CREATED="1512626427164" ID="ID_92585277" MODIFIED="1512626437617" TEXT="getAllResponseHeaders()">
<node CREATED="1512631147684" ID="ID_434373677" MODIFIED="1512631154111" TEXT="Retruns header info"/>
</node>
<node CREATED="1512626437901" ID="ID_195929332" MODIFIED="1512626447969" TEXT="getResponseHeader()">
<node CREATED="1512631156301" ID="ID_1572179766" MODIFIED="1512631169710" TEXT="Return specific header info"/>
</node>
<node CREATED="1512626448221" ID="ID_460515843" MODIFIED="1512626466433" TEXT="open(mehod, url, async, user, psw)">
<node CREATED="1512631454384" ID="ID_254232463" MODIFIED="1512631524012">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Specifies the request
    </p>
    <p>
      
    </p>
    <p>
      method: the request type GET or POST
    </p>
    <p>
      url: the file location
    </p>
    <p>
      async: true or false
    </p>
    <p>
      user: optional user name
    </p>
    <p>
      pwd: optional user password
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512631040056" ID="ID_961984001" MODIFIED="1512631047085" TEXT="send()">
<node CREATED="1512631175251" ID="ID_220467888" MODIFIED="1512631231770">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Sends the request to the server
    </p>
    <p>
      Used for GET request
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512631052838" ID="ID_927588274" MODIFIED="1512631057969" TEXT="send(string)">
<node CREATED="1512631240403" ID="ID_1653869593" MODIFIED="1512631252777">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Sends the request to the server
    </p>
    <p>
      Used for POST request
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512631058405" ID="ID_582639599" MODIFIED="1512631067569" TEXT="setRequestHeader()">
<node CREATED="1512631258804" ID="ID_1878815982" MODIFIED="1512631276205" TEXT="Adds a label / value pair to the header to be sent"/>
</node>
</node>
<node CREATED="1512631832654" ID="ID_370102616" MODIFIED="1512632599711" TEXT="XMLHttpRequest Object Properties">
<node CREATED="1512631846982" ID="ID_383761593" MODIFIED="1512631859442" TEXT="onreadystatechange">
<node CREATED="1512631897893" ID="ID_893067836" MODIFIED="1512631919249" TEXT="Defines a function to be called when the readyState property changes"/>
</node>
<node CREATED="1512631859949" ID="ID_1804506506" MODIFIED="1512631864650" TEXT="readyState">
<node CREATED="1512631921324" ID="ID_841440161" MODIFIED="1512632005766">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Holds the status of the XMLHttpRequest.
    </p>
    <p>
      0: request not initialized
    </p>
    <p>
      1: server connection established
    </p>
    <p>
      2: request received
    </p>
    <p>
      3: processsing request
    </p>
    <p>
      4: request finished and response is ready
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512631864894" ID="ID_1064728420" MODIFIED="1512631871913" TEXT="responseText">
<node CREATED="1512632012778" ID="ID_1715745160" MODIFIED="1512632022911" TEXT="Returns the response data as a string"/>
</node>
<node CREATED="1512631872230" ID="ID_1818550329" MODIFIED="1512631874273" TEXT="status">
<node CREATED="1512632024748" ID="ID_1251442147" MODIFIED="1512632034582" TEXT="Returns the response data as XML data"/>
</node>
<node CREATED="1512631874534" ID="ID_1753444540" MODIFIED="1512631878553" TEXT="status">
<node CREATED="1512632036107" ID="ID_1944309416" MODIFIED="1512632101488">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Returns the status-number of a request
    </p>
    <p>
      200: &quot;OK&quot;
    </p>
    <p>
      403: &quot;Forbidden&quot;
    </p>
    <p>
      404: &quot;Not Found&quot;
    </p>
    <p>
      For a complete list to to the Http Messages Reference
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512631880087" FOLDED="true" ID="ID_1081944907" MODIFIED="1512636082569" TEXT="statusText">
<node CREATED="1512632103833" ID="ID_1956331802" MODIFIED="1512632126658" TEXT="Returns the status-text(e.g. &quot;OK&quot; or &quot;NOT Found&quot;)"/>
</node>
</node>
</node>
<node CREATED="1512632259847" FOLDED="true" ID="ID_1793431133" MODIFIED="1512696309161" POSITION="right" TEXT="Request">
<icon BUILTIN="full-2"/>
<node CREATED="1512632281263" FOLDED="true" ID="ID_452493639" MODIFIED="1512632568255" TEXT="GET Request">
<node CREATED="1512632311974" ID="ID_97258957" MODIFIED="1512632412408">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Sitka Text">&#160;&#160;xhttp.open(&quot;GET&quot;, &quot;demo_get2.asp?fname=Henry&amp;lname=Ford&quot;, true); </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;xhttp.send();</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512632292222" ID="ID_1169471909" MODIFIED="1512635715854" TEXT="POST Request">
<node CREATED="1512632385085" ID="ID_1171150418" MODIFIED="1512635716707" TEXT="Demo1">
<node CREATED="1512632390404" ID="ID_1208415150" MODIFIED="1512632400665">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Sitka Text">&#160;&#160;xhttp.open(&quot;POST&quot;, &quot;demo_post.asp&quot;, true); </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;xhttp.send();</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512632453571" ID="ID_1453566260" MODIFIED="1512635719484" TEXT="Demo2">
<node CREATED="1512632457019" ID="ID_1220977641" MODIFIED="1512632467809">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Sitka Text">&#160;&#160;xhttp.open(&quot;POST&quot;, &quot;demo_post2.asp&quot;, true); </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;xhttp.setRequestHeader(&quot;Content-type&quot;, &quot;application/x-www-form-urlencoded&quot;); </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;xhttp.send(&quot;fname=Henry&amp;lname=Ford&quot;);</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
</node>
<node CREATED="1512632571978" FOLDED="true" ID="ID_407658498" MODIFIED="1512696311481" POSITION="left" TEXT="Response">
<icon BUILTIN="full-3"/>
<node CREATED="1512632966283" ID="ID_1415024641" MODIFIED="1512632978343" TEXT="Server Response Properties">
<node CREATED="1512632995403" ID="ID_1566039833" MODIFIED="1512633006158" TEXT="responseText">
<node CREATED="1512633007410" ID="ID_34667769" MODIFIED="1512633015428" TEXT="Returns the response data as a string"/>
</node>
<node CREATED="1512633017145" ID="ID_1356890872" MODIFIED="1512633021846" TEXT="responseXML">
<node CREATED="1512633023258" ID="ID_1309804637" MODIFIED="1512633031718" TEXT="Returns the response data as XML data"/>
</node>
</node>
<node CREATED="1512632983338" ID="ID_1184312354" MODIFIED="1512632990319" TEXT="Server Response Methods">
<node CREATED="1512633034513" ID="ID_1667423600" MODIFIED="1512633043742" TEXT="getResponseHeader()">
<node CREATED="1512633044554" ID="ID_497518437" MODIFIED="1512633065334" TEXT="Returns specific header information from the server resource"/>
</node>
<node CREATED="1512633067858" ID="ID_305554506" MODIFIED="1512633086326" TEXT="getAllResponseHeaders()">
<node CREATED="1512633087273" ID="ID_445946069" MODIFIED="1512633102084" TEXT="Returns all the header information from the server resource"/>
</node>
</node>
<node CREATED="1512632674783" FOLDED="true" ID="ID_934947645" MODIFIED="1512633418148" TEXT="Demo1">
<node CREATED="1512632678311" ID="ID_1740847038" MODIFIED="1512633416187">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.onreadystatechange = function() { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;if (this.readyState == 4 &amp;&amp; this.status == 200) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;&#160;&#160;document.getElementById(&quot;demo&quot;).innerHTML = </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;&#160;&#160;this.responseText; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;} </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;};</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512632784325" FOLDED="true" ID="ID_1841406660" MODIFIED="1512633402952" TEXT="Demo2 Callback">
<node CREATED="1512632789950" ID="ID_1833495552" MODIFIED="1512633397308">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">&lt;!DOCTYPE html&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;html&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;body&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;div id=&quot;demo&quot;&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;h2&gt;The XMLHttpRequest Object&lt;/h2&gt; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">&lt;button type=&quot;button&quot; </font>
    </p>
    <p>
      <font face="Courier New">onclick=&quot;loadDoc('ajax_info.txt', myFunction)&quot;&gt;Change Content </font>
    </p>
    <p>
      <font face="Courier New">&lt;/button&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;/div&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;script&gt; </font>
    </p>
    <p>
      <font face="Courier New">function loadDoc(url, cFunction) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;var xhttp; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp=new XMLHttpRequest(); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.onreadystatechange = function() { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;if (this.readyState == 4 &amp;&amp; this.status == 200) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;&#160;&#160;cFunction(this); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;} </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;}; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.open(&quot;GET&quot;, url, true); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.send(); </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      <font face="Courier New">function myFunction(xhttp) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;document.getElementById(&quot;demo&quot;).innerHTML = </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.responseText; </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      <font face="Courier New">&lt;/script&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;/body&gt; </font>
    </p>
    <p>
      <font face="Courier New">&lt;/html&gt;</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512633136072" FOLDED="true" ID="ID_834621595" MODIFIED="1512633258834" TEXT="Demo3 XML data">
<node CREATED="1512633143160" ID="ID_602938089" MODIFIED="1512633161076">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Sitka Text">&lt;!DOCTYPE html&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;html&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;body&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;h2&gt;The XMLHttpRequest Object&lt;/h2&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;p id=&quot;demo&quot;&gt;&lt;/p&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;script&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">var xhttp, xmlDoc, txt, x, i; </font>
    </p>
    <p>
      <font face="Sitka Text">xhttp = new XMLHttpRequest(); </font>
    </p>
    <p>
      <font face="Sitka Text">xhttp.onreadystatechange = function() { </font>
    </p>
    <p>
      <font face="Sitka Text">if (this.readyState == 4 &amp;&amp; this.status == 200) { </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;xmlDoc = this.responseXML; </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;txt = &quot;&quot;; </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;x = xmlDoc.getElementsByTagName(&quot;ARTIST&quot;); </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;for (i = 0; i &lt; x.length; i++) { </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;&#160;&#160;txt = txt + x[i].childNodes[0].nodeValue + &quot;&lt;br&gt;&quot;; </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;} </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;document.getElementById(&quot;demo&quot;).innerHTML = txt; </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;} </font>
    </p>
    <p>
      <font face="Sitka Text">}; </font>
    </p>
    <p>
      <font face="Sitka Text">xhttp.open(&quot;GET&quot;, &quot;cd_catalog.xml&quot;, true); </font>
    </p>
    <p>
      <font face="Sitka Text">xhttp.send(); </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;/script&gt; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Sitka Text">&lt;/body&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;/html&gt;</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512633260486" FOLDED="true" ID="ID_1466141333" MODIFIED="1512633368745" TEXT="Demo4 getAllResponseHeaders()">
<node CREATED="1512633280062" ID="ID_1523248706" MODIFIED="1512633296830">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Sitka Text">&lt;script&gt; </font>
    </p>
    <p>
      <font face="Sitka Text">var xhttp = new XMLHttpRequest(); </font>
    </p>
    <p>
      <font face="Sitka Text">xhttp.onreadystatechange = function() { </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;if (this.readyState == 4 &amp;&amp; this.status == 200) { </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;&#160;&#160;document.getElementById(&quot;demo&quot;).innerHTML = </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;&#160;&#160;this.getAllResponseHeaders(); </font>
    </p>
    <p>
      <font face="Sitka Text">&#160;&#160;} </font>
    </p>
    <p>
      <font face="Sitka Text">}; </font>
    </p>
    <p>
      <font face="Sitka Text">xhttp.open(&quot;GET&quot;, &quot;ajax_info.txt&quot;, true); </font>
    </p>
    <p>
      <font face="Sitka Text">xhttp.send(); </font>
    </p>
    <p>
      <font face="Sitka Text">&lt;/script&gt;</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512633314685" FOLDED="true" ID="ID_648265308" MODIFIED="1512633367488" TEXT="Demo5 getResponseHeader">
<node CREATED="1512633324949" ID="ID_567576397" MODIFIED="1512633358809">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">&lt;script&gt; </font>
    </p>
    <p>
      <font face="Courier New">var xhttp=new XMLHttpRequest(); </font>
    </p>
    <p>
      <font face="Courier New">xhttp.onreadystatechange = function() { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;if (this.readyState == 4 &amp;&amp; this.status == 200) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;document.getElementById(&quot;demo&quot;).innerHTML = </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;this.getResponseHeader(&quot;Last-Modified&quot;); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;} </font>
    </p>
    <p>
      <font face="Courier New">}; </font>
    </p>
    <p>
      <font face="Courier New">xhttp.open(&quot;GET&quot;, &quot;ajax_info.txt&quot;, true); </font>
    </p>
    <p>
      <font face="Courier New">xhttp.send(); </font>
    </p>
    <p>
      <font face="Courier New">&lt;/script&gt;</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
<node CREATED="1512633497825" FOLDED="true" ID="ID_441654630" MODIFIED="1512697058799" POSITION="left" TEXT="XML File">
<icon BUILTIN="full-4"/>
<node CREATED="1512633513681" ID="ID_620074773" MODIFIED="1512633525819">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">&lt;script&gt; </font>
    </p>
    <p>
      <font face="Courier New">function loadDoc() { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;var xhttp = new XMLHttpRequest(); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.onreadystatechange = function() { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;if (this.readyState == 4 &amp;&amp; this.status == 200) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;&#160;&#160;myFunction(this); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;} </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;}; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.open(&quot;GET&quot;, &quot;cd_catalog.xml&quot;, true); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;xhttp.send(); </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      <font face="Courier New">function myFunction(xml) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;var i; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;var xmlDoc = xml.responseXML; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;var table=&quot;&lt;tr&gt;&lt;th&gt;Artist&lt;/th&gt;&lt;th&gt;Title&lt;/th&gt;&lt;/tr&gt;&quot;; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;var x = xmlDoc.getElementsByTagName(&quot;CD&quot;); </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;for (i = 0; i &lt;x.length; i++) { </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;table += &quot;&lt;tr&gt;&lt;td&gt;&quot; + </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;x[i].getElementsByTagName(&quot;ARTIST&quot;)[0].childNodes[0].nodeValue + </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;&quot;&lt;/td&gt;&lt;td&gt;&quot; + </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;x[i].getElementsByTagName(&quot;TITLE&quot;)[0].childNodes[0].nodeValue + </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;&#160;&#160;&quot;&lt;/td&gt;&lt;/tr&gt;&quot;; </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;} </font>
    </p>
    <p>
      <font face="Courier New">&#160;&#160;document.getElementById(&quot;demo&quot;).innerHTML = table; </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      <font face="Courier New">&lt;/script&gt;</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
</map>
