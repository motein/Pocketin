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
<node CREATED="1512922087625" ID="ID_1156584433" MODIFIED="1512922092682" POSITION="right" TEXT="Preface">
<node CREATED="1512922110780" ID="ID_471386001" MODIFIED="1512922151419" TEXT="0MQ looks like an embeddable networking library but acts like a concurrency framework."/>
<node CREATED="1512922163547" ID="ID_1677517595" MODIFIED="1512922231308" TEXT="It gives you sockets that carry atomic messages across various transports like in-process, inter-process, TCP, and multicast."/>
<node CREATED="1512922255835" ID="ID_752796737" MODIFIED="1512922317760" TEXT="Can connect sockets N-to-N with patterns like fan-out, pub-sub, task distribution, and request-reply."/>
</node>
<node CREATED="1512922093229" ID="ID_131248346" MODIFIED="1512956070871" POSITION="right" TEXT="Basics">
<node CREATED="1512955835004" ID="ID_1672462866" MODIFIED="1512955835004">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <img src="LearningZeroMQ_483166959022450673.jpeg" />
  </body>
</html>
</richcontent>
<node CREATED="1512955853320" ID="ID_1722996814" MODIFIED="1512956105218" TEXT="Demo">
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
</node>
</node>
</map>
