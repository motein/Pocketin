<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1512616200758" ID="ID_1682022655" MODIFIED="1512616241157">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font size="5" color="#ff0000"><b>Thinking in Java</b></font>
    </p>
  </body>
</html></richcontent>
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1512616320664" ID="ID_42401233" MODIFIED="1512616340774" POSITION="right" TEXT="1. Introduction to Objects">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616369085" ID="ID_1686816091" MODIFIED="1512616626315" POSITION="right" TEXT="2. Everything Is an Object">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616383318" ID="ID_1909306072" MODIFIED="1512616625531" POSITION="right" TEXT="3. Operators">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616397021" ID="ID_1646286885" MODIFIED="1512616624906" POSITION="right" TEXT="4. Controlling Execution">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616406798" ID="ID_1653864235" MODIFIED="1512616624448" POSITION="right" TEXT="5. Initialization &amp; Cleanup">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616419110" ID="ID_1556966401" MODIFIED="1512616623674" POSITION="right" TEXT="6. Access Control">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1512617027092" ID="ID_577259103" MODIFIED="1512617099346" TEXT="Access control(or implementation hiding) is about &quot;not  getting it right the first time&quot;. And that is why refactoring.">
<font ITALIC="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512617243208" ID="ID_1941069952" MODIFIED="1514872182342" TEXT="Package">
<node CREATED="1512617285439" ID="ID_117087346" MODIFIED="1512617290645" TEXT="The lib unit"/>
<node CREATED="1512617249408" ID="ID_31950822" MODIFIED="1512617279298" TEXT="A package contains a group of classes, organized together under a single namespace."/>
</node>
</node>
<node CREATED="1512616426909" ID="ID_140149792" MODIFIED="1512616623018" POSITION="right" TEXT="7. Reusing Classes">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616447421" ID="ID_1177167724" MODIFIED="1512616622364" POSITION="right" TEXT="8. Polymorphism">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616456420" ID="ID_638428458" MODIFIED="1512616621827" POSITION="right" TEXT="9. Interfaces">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616461724" ID="ID_1182003191" MODIFIED="1512616621187" POSITION="right" TEXT="10. Inner Classes">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616467812" ID="ID_499243642" MODIFIED="1512616620484" POSITION="right" TEXT="11. Holding Your Objects">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616479923" ID="ID_1087269291" MODIFIED="1512616619875" POSITION="right" TEXT="12. Error Handling with Exceptions">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616500405" ID="ID_1696802260" MODIFIED="1512616619267" POSITION="right" TEXT="13. Strings">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616505275" ID="ID_1136522669" MODIFIED="1512616618684" POSITION="right" TEXT="14. Type Information">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616511955" ID="ID_1084709605" MODIFIED="1512616617923" POSITION="right" TEXT="15. Generics">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616517883" ID="ID_203051683" MODIFIED="1512616617186" POSITION="right" TEXT="16. Arrays">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616523475" ID="ID_682235566" MODIFIED="1512616616571" POSITION="right" TEXT="17. Containers in Depth">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616539866" ID="ID_1265919434" MODIFIED="1512616615923" POSITION="right" TEXT="18. I/O">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616544203" ID="ID_1859493146" MODIFIED="1512616615221" POSITION="right" TEXT="19. Enumerated Types">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616564161" ID="ID_1954453877" MODIFIED="1514876448758" POSITION="right" TEXT="20. Annotaions">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1514873422536" ID="ID_1947348838" MODIFIED="1514873708892">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Annotations (also known as <font color="#ff0000">metadata</font>) provide a formalized way to add info to your code so that you can easily use that data at some later point.
    </p>
  </body>
</html></richcontent>
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1514876450174" ID="ID_1793701639" MODIFIED="1514876622490">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      @Target defines where you can apply this annotation<font color="#ff0000">(a method or a field)</font>.
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1514876497796" ID="ID_834293363" MODIFIED="1514876687298">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      @Retention defines whether the annotations are available in the source code(<b><font color="#ff0000">SOURCE</font></b>), in the class files(<b><font color="#ff0000">CLASS</font></b>), or at the run time(<b><font color="#ff0000">RUNTIME</font></b>).
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1514948166364" ID="ID_573579167" MODIFIED="1514948182758" TEXT="Annotation Elements">
<node CREATED="1514948185090" ID="ID_815429993" MODIFIED="1514948193980" TEXT="All primitives"/>
<node CREATED="1514948194233" ID="ID_81620018" MODIFIED="1514948198684" TEXT="String"/>
<node CREATED="1514948958587" ID="ID_957078140" MODIFIED="1514948967175" TEXT="Class"/>
<node CREATED="1514948967874" ID="ID_279031968" MODIFIED="1514948970126" TEXT="enums"/>
<node CREATED="1514948970381" ID="ID_1937144534" MODIFIED="1514948974487" TEXT="Annotations"/>
<node CREATED="1514948976042" ID="ID_868677336" MODIFIED="1514948986134" TEXT="Arrays of any of the above"/>
</node>
<node CREATED="1514965093516" ID="ID_1790374435" MODIFIED="1514965106468" TEXT="Annotations don&apos;t support inheritance"/>
</node>
<node CREATED="1512616577482" ID="ID_612605284" MODIFIED="1512616613205" POSITION="right" TEXT="21. Concurrency">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1512616582961" ID="ID_1208643260" MODIFIED="1512616609148" POSITION="right" TEXT="22. Graphical User Interfaces">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
</node>
</node>
</map>
