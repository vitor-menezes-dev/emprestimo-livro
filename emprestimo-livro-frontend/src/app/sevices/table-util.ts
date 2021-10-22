import * as XLSX from "xlsx";

const getFileName = (name: string = "ExportResult") => {
  let timeSpan = new Date().toISOString();
  let sheetName = name.trim();
  let fileName = `${sheetName}-${timeSpan}`;
  return {
    sheetName,
    fileName
  };
};
export class TableUtil {
  static exportTableToExcel(tableId: string, name?: string) {
    let { sheetName, fileName } = getFileName(name);
    let targetTableElm = document.getElementById(tableId);
    let wb = XLSX.utils.table_to_book(targetTableElm, <XLSX.Table2SheetOpts>{
      sheet: sheetName
    });
    XLSX.writeFile(wb, `${fileName}.xlsx`);
  }

  static exportArrayToExcel(arr: any[], name?: string) {
    let { sheetName, fileName } = getFileName(name);

    var wb = XLSX.utils.book_new();
    var ws = XLSX.utils.json_to_sheet(arr);
    XLSX.utils.book_append_sheet(wb, ws, sheetName);
    XLSX.writeFile(wb, `${fileName}.xlsx`);
  }

  static export(data: any[], filtro: any[], name?: string) {
    let { sheetName, fileName } = getFileName(name);

    var wb = XLSX.utils.book_new();
    var wsData = XLSX.utils.json_to_sheet(data);
    XLSX.utils.book_append_sheet(wb, wsData, sheetName);

    if (filtro != []) {
      var wsFiltro = XLSX.utils.json_to_sheet(filtro);
      XLSX.utils.book_append_sheet(wb, wsFiltro, "Filtros");
    }
    XLSX.writeFile(wb, `${fileName}.xlsx`);
  }
}