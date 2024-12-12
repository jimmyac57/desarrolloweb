

const chart = Highcharts.chart("container", {
  chart: {
    type: "column",
  },
  title: {
    text: "Total de contactos por comuna",
  },
  xAxis: {
    categories: [], // Dynamically set categories
    title: {
      text: "Comunas",
    },
  },
  yAxis: {
    title: {
      text: "Total de contactos",
    },
  },
  series: [
    {
      name: "Contactos",
      data: [], // Dynamically set data
    },
  ],
});

fetch("/api/graficos/contactos")
  .then((response) => {
    if(!response.ok) {
        throw new Error("Error al obtener los datos de contacto");
    }
    return response.json();
})
  .then((data) => {
    const comunas = data.map((item) => item.comuna);
    const valores = data.map((item) => item.totalContactos);

    chart.update({
      xAxis: {
        categories: comunas,
      },
      series: [
        {
          name: "Contactos",
          data: valores,
        },
      ],
    });
  })
  .catch((error) => console.error("Error:", error));
