const pieChart = Highcharts.chart('container', {
    chart: {
        type: 'pie'
    },
    title: {
        text: 'Device Usage Distribution'
    },
    series: [{
        name: 'Devices',
        colorByPoint: true,
        data: [] 
    }],
    tooltip: {
        pointFormat: '{point.name}: <b>{point.y}</b>'
    }
});

fetch('/api/graficos/dispositivos')
    .then((response) => {
        if (!response.ok) {
            throw new Error('Error al obtener los datos de dispositivo');
        }
        return response.json();
    })
    .then((data) => {
        deviceTypes = data.map((device) => ({
            name: device.tipo,
            y: device.totalDispositivos
        }));
        pieChart.update({
            series: [{
                data: deviceTypes
            }]
        });
    })
    .catch((error) => console.error('Error:', error));
