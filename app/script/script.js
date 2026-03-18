const SHEET_NAME = "Pandemics";
const API_KEY = PropertiesService.getScriptProperties().getProperty("API_KEY");
const SPREADSHEET_ID = PropertiesService.getScriptProperties().getProperty("SPREADSHEET_ID");
const CACHE_TTL = 300;

function isValidApiKey(key) {
  const storedKey = PropertiesService.getScriptProperties().getProperty("API_KEY");
  return key === storedKey;
}

// GET
function doGet(e) {
  try {
    const apiKey = (e.parameter.apiKey || "").trim();
    if (!isValidApiKey(apiKey)) throw new Error("Unauthorized: API key incorrecta.");

    const tipus = (e.parameter.type || "all").trim().toLowerCase();
    const ss = SpreadsheetApp.openById(SPREADSHEET_ID);
    const cache = CacheService.getScriptCache();

    const sheet = ss.getSheetByName(SHEET_NAME);
    if (!sheet) throw new Error("No s'ha trobat el full " + SHEET_NAME);

    const data = sheet.getDataRange().getValues();
    const headers = data[0];
    const rows = data.slice(1);

    // cache clau segons tipus
    const cacheKey = "pandemics_" + tipus + "_" + (e.parameter.century || "") + "_" + (e.parameter.pathogenType || "");
    const cached = cache.get(cacheKey);
    if (cached) return jsonResponse(JSON.parse(cached));

    let result = [];

    if (tipus === "all") {
      result = rows.map(row => objectFromRow(headers, row));

   } else if (tipus === "bycentury") {

      const centuryParam = parseInt(e.parameter.century, 10);
      if (isNaN(centuryParam)) throw new Error("Paràmetre 'century' invàlid");

      result = rows
        .filter(r => {
          const value = parseInt((r[14] || "").toString().trim(), 10);
          return !isNaN(value) && value === centuryParam;
        })
        .map(r => objectFromRow(headers, r));
    } else if (tipus === "bytype") {
      const pathogenType = (e.parameter.pathogenType || "").trim();
      result = rows
        .filter(r => (r[2] || "").toString().trim().toLowerCase() === pathogenType.trim().toLowerCase()) // Pathogen_Type = columna 3
        .map(r => objectFromRow(headers, r));
    } else {
      return jsonResponse({ error: "Invalid endpoint" });
    }

    const resposta = { status: "ok", type: tipus, data: result };
    cache.put(cacheKey, JSON.stringify(resposta), CACHE_TTL);

    return jsonResponse(resposta);

  } catch (err) {
    return jsonResponse({ status: "error", error: err.message });
  }
}

// Convertir fila en objecte
function objectFromRow(headers, row) {
  const obj = {};
  headers.forEach((h, i) => obj[h] = row[i]);
  return obj;
}

// Utilitat JSON
function jsonResponse(data) {
  return ContentService.createTextOutput(JSON.stringify(data))
    .setMimeType(ContentService.MimeType.JSON);
}

function doPost(e) {
  try {
    const body = JSON.parse(e.postData.contents || "{}");

    const apiKey = (body.apiKey || "").trim();
    const email = (body.email || "").trim().toLowerCase();
    const accio = body.accio; // aquest és un objecte Pandemic

    // Validacions
    if (apiKey !== API_KEY) throw new Error("Unauthorized: API key incorrecta.");
    if (email !== "arnau.garcia.work@gmail.com" && !email.endsWith("@itb.cat")) throw new Error("Email invàlid.");

    const ss = SpreadsheetApp.openById(SPREADSHEET_ID);
    const sheet = ss.getSheetByName(SHEET_NAME);
    if (!sheet) throw new Error("No s'ha trobat el full Pandemics.");

    console.log("Body rebut:", JSON.stringify(body));
    console.log("Accio rebut:", JSON.stringify(accio));
    
    // Map Pandemic -> fila full
    const newRow = [
      accio.eventName,
      accio.pathogenName,
      accio.pathogenType,
      accio.startYear,
      accio.endYear,
      accio.originRegion,
      accio.geographicSpread,
      accio.continentsAffected,
      accio.estimatedCases,
      accio.estimatedDeaths,
      accio.caseFatalityRatePct,
      accio.primaryTransmission,
      accio.containmentMethod,
      accio.mortalityScale,
      accio.century
    ];

    sheet.appendRow(newRow);

    return ContentService.createTextOutput(JSON.stringify({
      success: true,
      message: "Pandèmia afegida correctament"
    })).setMimeType(ContentService.MimeType.JSON);

  } catch (err) {
    return ContentService.createTextOutput(JSON.stringify({
      success: false,
      message: err.message
    })).setMimeType(ContentService.MimeType.JSON);
  }
}

function afegirPandemiaTest() {
  const ss = SpreadsheetApp.openById(SPREADSHEET_ID);
  const sheet = ss.getSheetByName(SHEET_NAME);
  if (!sheet) throw new Error("No s'ha trobat el full Pandemics.");

  // Nova fila a afegir
  const novaFila = [
    "Test Pandemic",             // eventName
    "Test Pathogen",             // pathogenName
    "Virus",                     // pathogenType
    2025,                        // startYear
    2026,                        // endYear
    "Test Region",               // originRegion
    "Global",                    // geographicSpread
    7,                           // continentsAffected
    100000,                      // estimatedCases
    5000,                        // estimatedDeaths
    5.0,                         // caseFatalityRatePct
    "Airborne",                  // primaryTransmission
    "Quarantine",                // containmentMethod
    "Moderate",                  // mortalityScale
    21                           // century
  ];

  sheet.appendRow(novaFila);

}