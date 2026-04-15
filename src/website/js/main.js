// XSLT 1: Table transformation 
var xslTabla = `<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8"/>
  <xsl:template match="/">
    <div class="xsl-result">
      <h2>Available Cruises</h2>
      <table>
        <thead>
          <tr><th>Code</th><th>Type</th><th>Name</th><th>Cabins</th><th>Capacity</th><th>Status</th><th>Routes</th><th>Action</th></tr>
        </thead>
        <tbody>
          <xsl:for-each select="cruiseManagement/cruises/cruise">
            <xsl:variable name="currentCode" select="@code"/>
            <tr>
              <td><strong><xsl:value-of select="$currentCode"/></strong></td>
              <td><xsl:value-of select="@type"/></td>
              <td><xsl:value-of select="name"/></td>
              <td><xsl:value-of select="rooms"/></td>
              <td><xsl:value-of select="capacity"/></td>
              <td><xsl:value-of select="@status"/></td>
              <td><xsl:value-of select="count(/cruiseManagement/bookings/booking[cruise=$currentCode])"/> bookings</td>
              <td><button class="btn-detalle" onclick="mostrarDetalle('{$currentCode}')">View detail</button></td>
            </tr>
          </xsl:for-each>
        </tbody>
      </table>

      <h2 style="margin-top:28px;">Registered Clients</h2>
      <table>
        <thead>
          <tr><th>ID</th><th>Name</th><th>Surname</th><th>Age</th><th>Phone</th><th>Email</th><th>Bookings</th></tr>
        </thead>
        <tbody>
          <xsl:for-each select="cruiseManagement/clients/client">
            <xsl:variable name="clientId" select="@id"/>
            <tr>
              <td><strong><xsl:value-of select="$clientId"/></strong></td>
              <td><xsl:value-of select="name"/></td>
              <td><xsl:value-of select="surname"/></td>
              <td><xsl:value-of select="age"/></td>
              <td><xsl:value-of select="phone"/></td>
              <td><xsl:value-of select="email"/></td>
              <td><xsl:value-of select="count(/cruiseManagement/bookings/booking[client=$clientId])"/> booking(s)</td>
            </tr>
          </xsl:for-each>
        </tbody>
      </table>
    </div>
  </xsl:template>
</xsl:stylesheet>`;

// XSLT 2: Detail transformation 
var xslDetalle = `<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8"/>
  <xsl:param name="codeCruise"/>
  <xsl:template match="/">
    <xsl:for-each select="cruiseManagement/cruises/cruise[@code=$codeCruise]">
      <div class="detalle-crucero">
        <button class="btn-volver" onclick="volverTabla()">← Back to table</button>
        <div class="detalle-header">
          <div class="detalle-info">
            <h2><xsl:value-of select="name"/></h2>
            <p class="detalle-code">Code: <strong><xsl:value-of select="@code"/></strong></p>
            <span><xsl:attribute name="class">badge badge-<xsl:value-of select="@type"/></xsl:attribute><xsl:value-of select="@type"/></span>
            <span class="detalle-estado">Status: <xsl:value-of select="@status"/></span>
          </div>
        </div>

        <div class="detalle-stats">
          <div class="stat"><span class="stat-num"><xsl:value-of select="rooms"/></span><span class="stat-label">Cabins</span></div>
          <div class="stat"><span class="stat-num"><xsl:value-of select="capacity"/></span><span class="stat-label">Capacity</span></div>
          <div class="stat"><span class="stat-num"><xsl:value-of select="count(/cruiseManagement/bookings/booking[cruise=$codeCruise])"/></span><span class="stat-label">Bookings</span></div>
          <div class="stat"><span class="stat-num"><xsl:value-of select="count(/cruiseManagement/workers/worker[cruiseAssigned=$codeCruise])"/></span><span class="stat-label">Workers</span></div>
        </div>

        <div class="detalle-section">
          <h3>Active Routes</h3>
          <table>
            <thead><tr><th>Origin</th><th>Destination</th><th>Price</th></tr></thead>
            <tbody>
              <xsl:for-each select="/cruiseManagement/bookings/booking[cruise=$codeCruise]">
                <tr>
                  <td><xsl:value-of select="originCity"/></td>
                  <td><xsl:value-of select="destinationCity"/></td>
                  <td>€<xsl:value-of select="finalPrice"/></td>
                </tr>
              </xsl:for-each>
            </tbody>
          </table>
        </div>

        <div class="detalle-section">
          <h3>Assigned Workers</h3>
          <table>
            <thead><tr><th>ID</th><th>Service</th><th>Name</th><th>Surname</th><th>Contact</th></tr></thead>
            <tbody>
              <xsl:for-each select="/cruiseManagement/workers/worker[cruiseAssigned=$codeCruise]">
                <tr>
                  <td><xsl:value-of select="@id"/></td>
                  <td><xsl:value-of select="service"/></td>
                  <td><xsl:value-of select="name"/></td>
                  <td><xsl:value-of select="surname"/></td>
                  <td><xsl:value-of select="contact/email"/></td>
                </tr>
              </xsl:for-each>
            </tbody>
          </table>
        </div>

        <div class="detalle-section">
          <h3>Current Passengers</h3>
          <table>
            <thead><tr><th>Client ID</th><th>Full Name</th><th>Start Date</th><th>End Date</th></tr></thead>
            <tbody>
              <xsl:for-each select="/cruiseManagement/bookings/booking[cruise=$codeCruise]">
                <xsl:variable name="cid" select="client"/>
                <xsl:variable name="clientData" select="/cruiseManagement/clients/client[@id=$cid]"/>
                <tr>
                  <td><xsl:value-of select="$cid"/></td>
                  <td><xsl:value-of select="$clientData/name"/>&#160;<xsl:value-of select="$clientData/surname"/></td>
                  <td><xsl:value-of select="startDate"/></td>
                  <td><xsl:value-of select="endDate"/></td>
                </tr>
              </xsl:for-each>
            </tbody>
          </table>
        </div>
      </div>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>`;

var xmlDoc = null;

document.getElementById('fileInput').addEventListener('change', function(e) {
    var reader = new FileReader();
    reader.onload = function(ev) { document.getElementById('xmlInput').value = ev.target.result; };
    reader.readAsText(e.target.files[0]);
});

function procesarXML() {
    var raw = document.getElementById('xmlInput').value.trim();
    var st  = document.getElementById('status');

    if (!raw) {
        st.className = 'status error';
        st.textContent = 'Please enter XML content.';
        return;
    }

    var parser = new DOMParser();
    xmlDoc = parser.parseFromString(raw, 'application/xml');

    if (xmlDoc.querySelector('parsererror')) {
        st.className = 'status error';
        st.textContent = 'Error parsing XML. Check the format.';
        return;
    }

    st.className = 'status success';
    st.textContent = 'XML processed — XSL transformation applied correctly.';

    aplicarXSLTabla();
}

function aplicarXSLTabla() {
    var xslParser = new DOMParser();
    var xslDoc    = xslParser.parseFromString(xslTabla, 'application/xml');
    var processor = new XSLTProcessor();
    processor.importStylesheet(xslDoc);
    var result    = processor.transformToFragment(xmlDoc, document);
    var container = document.getElementById('resultado');
    container.innerHTML = '';
    container.appendChild(result);
}

function mostrarDetalle(code) {
    var xslParser = new DOMParser();
    var xslDoc    = xslParser.parseFromString(xslDetalle, 'application/xml');
    var processor = new XSLTProcessor();
    processor.importStylesheet(xslDoc);
 
    processor.setParameter(null, 'codeCruise', code);
    var result    = processor.transformToFragment(xmlDoc, document);
    var container = document.getElementById('resultado');
    container.innerHTML = '';
    container.appendChild(result);
}

function volverTabla() {
    aplicarXSLTabla();
}