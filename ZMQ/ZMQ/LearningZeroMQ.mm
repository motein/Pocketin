<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1512921907608" ID="ID_1729681743" MODIFIED="1512921998800">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font size="4" color="#ff0000">Learning ZeroMQ</font>
    </p>
  </body>
</html></richcontent>
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1512922087625" FOLDED="true" ID="ID_1156584433" MODIFIED="1514364466011" POSITION="right" TEXT="Preface">
<icon BUILTIN="full-0"/>
<node CREATED="1512922110780" ID="ID_471386001" MODIFIED="1512922151419" TEXT="0MQ looks like an embeddable networking library but acts like a concurrency framework."/>
<node CREATED="1512922163547" ID="ID_1677517595" MODIFIED="1512922231308" TEXT="It gives you sockets that carry atomic messages across various transports like in-process, inter-process, TCP, and multicast."/>
<node CREATED="1512922255835" ID="ID_752796737" MODIFIED="1512922317760" TEXT="Can connect sockets N-to-N with patterns like fan-out, pub-sub, task distribution, and request-reply."/>
</node>
<node CREATED="1512922093229" ID="ID_131248346" MODIFIED="1514364470416" POSITION="right" TEXT="Basics">
<icon BUILTIN="full-1"/>
<node CREATED="1512955835004" ID="ID_1672462866" MODIFIED="1514364639943">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <img src="LearningZeroMQ_483166959022450673.jpeg" width="200" height="300" />
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1512955853320" FOLDED="true" ID="ID_1722996814" MODIFIED="1514364675809" TEXT="Demo">
<node CREATED="1512955867375" ID="ID_199990953" MODIFIED="1512956073027" TEXT="Sever">
<node CREATED="1512955912414" ID="ID_1239230693" MODIFIED="1512955932805">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">int main() { </font>
    </p>
    <p>
      <font face="Courier New">// Prepare our context and socket </font>
    </p>
    <p>
      <font face="Courier New">zmq::context_t context(1); </font>
    </p>
    <p>
      <font face="Courier New">zmq::socket_t socket(context, ZMQ_REP); </font>
    </p>
    <p>
      <font face="Courier New">socket.bind(&quot;tcp://*:5555&quot;); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">while (true) { </font>
    </p>
    <p>
      <font face="Courier New">zmq::message_t&#160;&#160;request; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">// Wait for next request from client </font>
    </p>
    <p>
      <font face="Courier New">socket.recv(&amp;request); </font>
    </p>
    <p>
      <font face="Courier New">std::cout &lt;&lt; &quot;Received Hello&quot; &lt;&lt; std::endl; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">// Do some 'work' </font>
    </p>
    <p>
      <font face="Courier New">Sleep(1); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">zmq::message_t reply(5); </font>
    </p>
    <p>
      <font face="Courier New">memcpy((void*)reply.data(), &quot;World&quot;, 5); </font>
    </p>
    <p>
      <font face="Courier New">socket.send(reply); </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">return 0; </font>
    </p>
    <p>
      <font face="Courier New">}</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1512955870589" ID="ID_172029694" MODIFIED="1512956074777" TEXT="Client">
<node CREATED="1512955963077" ID="ID_1431778528" MODIFIED="1512955974870">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">int main() { </font>
    </p>
    <p>
      <font face="Courier New">std::cout &lt;&lt; &quot;Connecting to hello world server...&quot; &lt;&lt; std::endl; </font>
    </p>
    <p>
      <font face="Courier New">zmq::context_t context(1); </font>
    </p>
    <p>
      <font face="Courier New">zmq::socket_t requester(context, ZMQ_REQ); </font>
    </p>
    <p>
      <font face="Courier New">requester.connect(&quot;tcp://localhost:5555&quot;); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">for (int request_nbr = 0; request_nbr != 10; request_nbr++) { </font>
    </p>
    <p>
      <font face="Courier New">char buffer[10]; </font>
    </p>
    <p>
      <font face="Courier New">std::cout &lt;&lt; &quot;Sending Hello &quot; &lt;&lt; request_nbr &lt;&lt; &quot;...&quot; &lt;&lt; std::endl; </font>
    </p>
    <p>
      <font face="Courier New">requester.send(&quot;Hello&quot;, 5, 0); </font>
    </p>
    <p>
      <font face="Courier New">requester.recv(buffer, 10, 0); </font>
    </p>
    <p>
      <font face="Courier New">buffer[5] = '\0'; </font>
    </p>
    <p>
      <font face="Courier New">std::cout &lt;&lt; &quot;Received World&quot; &lt;&lt; request_nbr &lt;&lt;&#160;&#160;buffer &lt;&lt; std::endl; </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">requester.close(); </font>
    </p>
    <p>
      <font face="Courier New">getchar(); </font>
    </p>
    <p>
      <font face="Courier New">return 0; </font>
    </p>
    <p>
      <font face="Courier New">}</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
</node>
<node CREATED="1512957066760" FOLDED="true" ID="ID_555851160" MODIFIED="1514364671738" TEXT="Version Reporting">
<node CREATED="1512957084322" ID="ID_1607994547" MODIFIED="1512957096894">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">int main() { </font>
    </p>
    <p>
      <font face="Courier New">int major, minor, patch; </font>
    </p>
    <p>
      <font face="Courier New">zmq::version(&amp;major, &amp;minor, &amp;patch); </font>
    </p>
    <p>
      <font face="Courier New">std::cout &lt;&lt; &quot;Current 0MQ version is: &quot; &lt;&lt; major &lt;&lt; &quot;.&quot; &lt;&lt; minor &lt;&lt; &quot;.&quot; &lt;&lt; patch &lt;&lt; std::endl; </font>
    </p>
    <p>
      <font face="Courier New">getchar(); </font>
    </p>
    <p>
      <font face="Courier New">return 0; </font>
    </p>
    <p>
      <font face="Courier New">}</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1513010799231" ID="ID_1029437258" MODIFIED="1514364666694">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <img src="LearningZeroMQ_4455043492294881194.jpeg" width="300" height="300" />
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1513010814925" ID="ID_1631274621" MODIFIED="1513010818028" TEXT="Demo">
<node CREATED="1513010822333" FOLDED="true" ID="ID_1573552824" MODIFIED="1514364723810" TEXT="Server">
<node CREATED="1513010861240" ID="ID_1686899880" MODIFIED="1513010893354">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">int main() { </font>
    </p>
    <p>
      <font face="Courier New">void *context = zmq_ctx_new(); </font>
    </p>
    <p>
      <font face="Courier New">void *publisher = zmq_socket(context, ZMQ_PUB); </font>
    </p>
    <p>
      <font face="Courier New">int rc = zmq_bind(publisher, &quot;tcp://*:5556&quot;); </font>
    </p>
    <p>
      <font face="Courier New">assert(rc == 0); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">srand((unsigned)time(NULL)); </font>
    </p>
    <p>
      <font face="Courier New">while (true) { </font>
    </p>
    <p>
      <font face="Courier New">int zipcode = rand() * 1000; </font>
    </p>
    <p>
      <font face="Courier New">int temperature = rand() * 215 - 80; </font>
    </p>
    <p>
      <font face="Courier New">int relhumidity = rand() * 50 + 10; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">char update[200]; </font>
    </p>
    <p>
      <font face="Courier New">sprintf_s(update, &quot;%05d %d %d&quot;, zipcode, temperature, relhumidity); </font>
    </p>
    <p>
      <font face="Courier New">s_send(publisher, update, 200); </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">zmq_close(publisher); </font>
    </p>
    <p>
      <font face="Courier New">zmq_ctx_destroy(context); </font>
    </p>
    <p>
      <font face="Courier New">return 0; </font>
    </p>
    <p>
      <font face="Courier New">}</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1513010825542" FOLDED="true" ID="ID_1530356331" MODIFIED="1514364717458" TEXT="Client">
<node CREATED="1513010912781" ID="ID_1267676050" MODIFIED="1513010926673">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font face="Courier New">int main(int argc, char *argv[]) { </font>
    </p>
    <p>
      <font face="Courier New">printf(&quot;Collecting updates from weather server...\n&quot;); </font>
    </p>
    <p>
      <font face="Courier New">void *context = zmq_ctx_new(); </font>
    </p>
    <p>
      <font face="Courier New">void *subscriber = zmq_socket(context, ZMQ_SUB); </font>
    </p>
    <p>
      <font face="Courier New">int rc = zmq_connect(subscriber, &quot;tcp://localhost:5556&quot;); </font>
    </p>
    <p>
      <font face="Courier New">assert(rc == 0); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">char* filter = (argc &gt; 1) ? argv[1] : &quot;10001&quot;; </font>
    </p>
    <p>
      <font face="Courier New">rc = zmq_setsockopt(subscriber, ZMQ_SUBSCRIBE, filter, strlen(filter)); </font>
    </p>
    <p>
      <font face="Courier New">assert(rc == 0); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">int update_nbr; </font>
    </p>
    <p>
      <font face="Courier New">long total_temp = 0; </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">for (update_nbr = 0; update_nbr &lt; 100; update_nbr++) { </font>
    </p>
    <p>
      <font face="Courier New">char *string = s_recv(subscriber); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">int zipcode, temperature, relhumidity; </font>
    </p>
    <p>
      <font face="Courier New">sscanf_s(string, &quot;%d %d %d&quot;, &amp;zipcode, &amp;temperature, &amp;relhumidity); </font>
    </p>
    <p>
      <font face="Courier New">total_temp += temperature; </font>
    </p>
    <p>
      <font face="Courier New">free(string); </font>
    </p>
    <p>
      <font face="Courier New">} </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">printf(&quot;Average temperature for zipcode '%s' was %dF\n&quot;, filter, (int)(total_temp / update_nbr)); </font>
    </p>
    <p>
      
    </p>
    <p>
      <font face="Courier New">zmq_close(subscriber); </font>
    </p>
    <p>
      <font face="Courier New">zmq_ctx_destroy(context); </font>
    </p>
    <p>
      <font face="Courier New">getchar(); </font>
    </p>
    <p>
      <font face="Courier New">return 0; </font>
    </p>
    <p>
      <font face="Courier New">}</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
</node>
<node CREATED="1514364801381" ID="ID_801206523" MODIFIED="1514365025040" TEXT="Parallel Pipeline">
<node CREATED="1514364805442" ID="ID_1125840137" MODIFIED="1514364906855">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <img src="LearningZeroMQ_1895528256595019579.jpeg" width="300" height="350" />
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
<node CREATED="1512957062074" ID="ID_386357702" MODIFIED="1512957062074" POSITION="right" TEXT=""/>
</node>
</map>
