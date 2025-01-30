function calcularExportacion() {
    // Costos Fijos en Pesos Mexicanos
    const costosAduaneros = 3938;     
    const manipulacionOrigen = 65625; 
    const transportePrincipal = 57750;
    const seguroTransporte = 26250;   
    const margenPorcentaje = 0.20;    // Margen del 20%

    // Validación de valor de mercancía
    const valorMercancia = parseFloat(document.getElementById('valorMercancia').value);
    const arancel = parseFloat(document.getElementById('arancel').value) / 100 || 0;
    const impuestoImportacion = parseFloat(document.getElementById('impuestoImportacion').value) / 100 || 0;

    // Validaciones
    if (isNaN(valorMercancia) || valorMercancia <= 0) {
        alert('Por favor, ingrese un valor de mercancía válido');
        return;
    }

    // Cálculo de precios por Incoterm con margen
    const precioEXW = valorMercancia * (1 + margenPorcentaje);
    
    const precioFOB = (valorMercancia + 
                      costosAduaneros + 
                      manipulacionOrigen) * (1 + margenPorcentaje);
    
    const precioCIF = (valorMercancia + 
                      costosAduaneros + 
                      manipulacionOrigen +
                      transportePrincipal + 
                      seguroTransporte) * (1 + margenPorcentaje);
    
    const precioDDP = (valorMercancia + 
                      costosAduaneros + 
                      manipulacionOrigen +
                      transportePrincipal + 
                      seguroTransporte +
                      (precioCIF * arancel) + 
                      (precioCIF * impuestoImportacion)) * (1 + margenPorcentaje);

    // Generación de resultados
    const resultadosHTML = `
        <table>
            <tr>
                <th>Incoterm</th>
                <th>Precio Base</th>
                <th>Precio c/Margen 20%</th>
            </tr>
            <tr>
                <td>EXW (En Fábrica)</td>
                <td>${valorMercancia.toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
                <td>${precioEXW.toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
            </tr>
            <tr>
                <td>FOB (Franco a Bordo)</td>
                <td>${(valorMercancia + costosAduaneros + manipulacionOrigen).toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
                <td>${precioFOB.toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
            </tr>
            <tr>
                <td>CIF (Costo, Seguro y Flete)</td>
                <td>${(valorMercancia + costosAduaneros + manipulacionOrigen + transportePrincipal + seguroTransporte).toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
                <td>${precioCIF.toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
            </tr>
            <tr>
                <td>DDP (Entregada Derechos Pagados)</td>
                <td>${(valorMercancia + costosAduaneros + manipulacionOrigen + transportePrincipal + seguroTransporte + (precioCIF * arancel) + (precioCIF * impuestoImportacion)).toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
                <td>${precioDDP.toLocaleString('es-MX', {style: 'currency', currency: 'MXN'})}</td>
            </tr>
        </table>
    `;

    document.getElementById('resultados').innerHTML = resultadosHTML;
}
