const labelColor = getComputedStyle(document.documentElement).getPropertyValue("--color-label").trim();

let entityStats = parseInt($(".entityStats").html());
let availableStats = parseInt($(".availableStats").html());
let busyStats = parseInt($(".busyStats").html());
let previewStats = parseInt($(".previewStats").html());
let loggedOutStats = parseInt($(".loggedOutStats").html());
let afterStats = parseInt($(".afterStats").html());

var circleChart, circleOptions;
var barChart, barOptions;
var donutChart, donutOptions;

// Emitter handler for updates
$(document).ready(() => {
    setupOptions();

    circleChart = new ApexCharts(
        document.querySelector("#circle-chart"),
        circleOptions
    );
    barChart = new ApexCharts(document.querySelector("#bar-chart"), barOptions);
    donutChart = new ApexCharts(
        document.querySelector("#donut-chart"),
        donutOptions
    );
    barChart.render();
    circleChart.render();
    donutChart.render();

    let source = new EventSource("/api/v1/init");

    // listen for API calls on updates
    source.addEventListener("updateAgent", updateHandler);
    source.addEventListener("deleteAgent", deleteHandler);
});

/**
 * Method that updates the information on entity information row
 * @param {*} event 
 */
function updateHandler(event) {
    let agentData = JSON.parse(event.data);

    if (agentData.status == "available") {
        $(".availableStats").html(++availableStats);
    } else if (agentData.status == "busy") {
        $(".busyStats").html(++busyStats);
    } else if (agentData.status == "preview") {
        $(".previewStats").html(++previewStats);
    } else if (
        agentData.status == "loggedout" ||
        agentData.status == "logged-out"
    ) {
        $(".loggedOutStats").html(++loggedOutStats);
    } else if (agentData.status == "after") {
        $(".afterStats").html(++afterStats);
    }

    $(".entityStats").html(++entityStats);
    updateCharts();
}

/**
 * Function that updates information on row displaying entity population description.
 * @param {*} event 
 */
function deleteHandler(event) {
    let data = JSON.parse(event.data);

    if (data.status == "available") {
        $(".availableStats").html(--availableStats);
    } else if (data.status == "busy") {
        $(".busyStats").html(--busyStats);
    } else if (data.status == "preview") {
        $(".previewStats").html(--previewStats);
    } else if (data.status == "loggedout" || data.status == "logged-out") {
        $(".loggedOutStats").html(--loggedOutStats);
    } else if (data.status == "after") {
        $(".afterStats").html(--afterStats);
    }

    $(".entityStats").html(--entityStats);
    updateCharts();
}

function setupOptions() {
    if (localStorage.getItem("options") != null) {
        let opts = localStorage.getItem("options");
        opts = JSON.parse(opts);
        if (opts.available) agentAvailableColor = opts.available;
        if (opts.busy) agentBusyColor = opts.busy;
        if (opts.preview) agentPreviewColor = opts.preview;
        if (opts.loggedout) agentLoggedOutColor = opts.loggedout;
        if (opts.after) agentAfterColor = opts.after;
    } else {
        agentAvailableColor = getComputedStyle(document.documentElement).getPropertyValue("--agent-available-bright-color").trim();
        agentBusyColor = getComputedStyle(document.documentElement).getPropertyValue("--agent-busy-bright-color").trim();
        agentPreviewColor = getComputedStyle(document.documentElement).getPropertyValue("--agent-preview-bright-color").trim();
        agentLoggedOutColor = getComputedStyle(document.documentElement).getPropertyValue("--agent-loggedout-color").trim();
        agentAfterColor = getComputedStyle(document.documentElement).getPropertyValue("--agent-after-color").trim();
    }
    $("#available-color-option").val(agentAvailableColor);
    $("#busy-color-option").val(agentBusyColor);
    $("#preview-color-option").val(agentPreviewColor);
    $("#loggedout-color-option").val(agentLoggedOutColor);
    $("#after-color-option").val(agentAfterColor);
    // Default Chart Options
    const defaultOptions = {
        chart: {
            toolbar: {
                show: false,
            },
            selection: {
                enabled: false,
            },
            zoom: {
                enabled: false,
            },
            width: "100%",
            height: 380,
            offsetY: 8,
        },
        stroke: {
            linecap: "round",
        },
        dataLabels: {
            enabled: false,
        },
        legend: {
            show: false,
        },
        states: {
            hover: {
                filter: {
                    type: "none",
                },
            },
        },
    };

    // Bar Chart -- Credit @https://github.com/frontend-joe/es6-charts/tree/main/bar-chart
    barOptions = {
        ...defaultOptions,
        chart: {
            ...defaultOptions.chart,
            type: "bar",
        },
        tooltip: {
            enabled: true,
            fillSeriesColor: true,
            y: {
                formatter: (value) => {
                    return `${value}`;
                },
            },
        },
        series: [
            // data
            {
                name: "Status",
                data: [
                    {
                        x: "Available",
                        y: availableStats,
                        fillColor: agentAvailableColor,
                    },
                    {
                        x: "Busy",
                        y: busyStats,
                        fillColor: agentBusyColor,
                    },
                    {
                        x: "Preview",
                        y: previewStats,
                        fillColor: agentPreviewColor,
                    },
                    {
                        x: "Logged Out",
                        y: loggedOutStats,
                        fillColor: agentLoggedOutColor,
                    },
                    {
                        x: "After",
                        y: afterStats,
                        fillColor: agentAfterColor,
                    },
                ],
            },
        ],
        stroke: { show: false },
        grid: {
            borderColor: "rgba(0, 0, 0, 0)",
            padding: { left: 0, right: 0, top: -16, bottom: -8 },
        },
        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: "15%",
                barHeight: "100%",
                borderRadius: 6,
            },
        },
        yaxis: {
            show: false,
        },
        xaxis: {
            labels: {
                floating: true,
                show: true,
                style: {
                    colors: labelColor,
                },
            },
            axisBorder: { show: false },
            axisTicks: { show: false },
            crosshairs: { show: false },
            categories: ["Available", "Busy", "Preview", "Logged Out", "After"],
        },
        noData: {
            text: "No Data is Present",
            align: 'center',
            verticalAlign: 'middle',
            offsetX: 0,
            offsetY: 0,
            style: {
              color: [defaultTeamColor],
              fontSize: '14px',
            }
        },
    };

    circleOptions = {
        chart: {
            height: 380,
            type: "radialBar",
            animations: {
                speed: 900,
                animateGradually: {
                    enabled: true,
                    delay: 150,
                },
                dynamicAnimation: {
                    enabled: true,
                    speed: 350,
                },
            },
        },
        plotOptions: {
            radialBar: {
                dataLabels: {
                    showOn: "always",
                    value: {
                        color: "#fff",
                        formatter: (val) => {
                            let val2 = (val) * 100;
                            return val.toString().substring(0, 3) + "%";
                        },
                    },
                },
            },
        },
        series: [
            (availableStats / entityStats) * 100,
            (busyStats / entityStats) * 100,
            (previewStats / entityStats) * 100,
            (loggedOutStats / entityStats) * 100,
            (afterStats / entityStats) * 100,
        ],
        colors: [
            agentAvailableColor,
            agentBusyColor,
            agentPreviewColor,
            agentLoggedOutColor,
            agentAfterColor,
        ],
        labels: ["Available", "Busy", "Preview", "Logged Out", "After"],
        noData: {
            text: "No Data is Present",
            align: 'center',
            verticalAlign: 'middle',
            style: {
                color: [defaultTeamColor],
                fontSize: '22px',
              }
        },
    };

    donutOptions = {
        series: [
            availableStats / entityStats,
            busyStats / entityStats,
            previewStats / entityStats,
            loggedOutStats / entityStats,
            afterStats / entityStats,
        ],
        colors: [
            agentAvailableColor,
            agentBusyColor,
            agentPreviewColor,
            agentLoggedOutColor,
            agentAfterColor,
        ],
        labels: ["Available", "Busy", "Preview", "Logged Out", "After"],
        dataLabels: {
            enabled: true,
            formatter: (val, opts) => {
                return val.toString().substring(0, 3) + "%";
            },
        },
        legend: {
            labels: {
                colors: ["#fff"],
                // userSeriesColors: true,
            },
        },
        stroke: {
            show: true,
            curve: "smooth",
            width: 1,
            colors: ["#000"],
        },
        chart: {
            height: 380,
            type: "donut",
            animations: {
                speed: 800,
                animateGradually: {
                    enabled: true,
                    delay: 150,
                },
                dynamicAnimation: {
                    enabled: true,
                    speed: 350,
                },
            },
        },
        tooltip: {
            fillSeriesColor: false,
            y: {
                formatter: (val) => {
                    return (val * 100).toString().substring(0, 5) + "%";
                },
            },
        },
        plotOptions: {
            pie: {
                donut: {
                    show: true,
                    labels: {
                        value: {
                            show: true,
                            formatter: (val) => {
                                return val.toString().substring(0, 4) + "%";
                            },
                        },
                    },
                },
                expandOnClick: true,
            },
        },
        noData: {
            text: "No Data is Present",
            align: 'center',
            verticalAlign: 'middle',
        },
    };

}

/**
 * Function to update the charts based on new row information; based on emitter events
 */
function updateCharts() {
    barChart.updateSeries([
        {
            name: "Status",
            data: [
                {
                    x: "Available",
                    y: availableStats,
                    fillColor: agentAvailableColor,
                },
                {
                    x: "Busy",
                    y: busyStats,
                    fillColor: agentBusyColor,
                },
                {
                    x: "Preview",
                    y: previewStats,
                    fillColor: agentPreviewColor,
                },
                {
                    x: "Logged Out",
                    y: loggedOutStats,
                    fillColor: agentLoggedOutColor,
                },
                {
                    x: "After",
                    y: afterStats,
                    fillColor: agentAfterColor,
                },
            ],
        },
    ]);

    circleChart.updateSeries([
        (availableStats / entityStats) * 100,
        (busyStats / entityStats) * 100,
        (previewStats / entityStats) * 100,
        (loggedOutStats / entityStats) * 100,
        (afterStats / entityStats) * 100,
    ]);

    donutChart.updateSeries([
        availableStats / entityStats,
        busyStats / entityStats,
        previewStats / entityStats,
        loggedOutStats / entityStats,
        afterStats / entityStats,
    ]);
};
