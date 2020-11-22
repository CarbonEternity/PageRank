$(document).ready(() => {

    const container = $("#mynetwork")[0];
    const $pageRanksLabel = $("#pageRanks");
    const pageRanksLabel = $pageRanksLabel[0];
    const options = {
        edges: {
            arrows: {
                to: {
                    enabled: true
                }
            }
        },
        physics: {
            enabled: true,
            stabilization: {
                enabled: true,
                iterations: 10000,
                updateInterval: 100,
            },
        },
        interaction: {
            dragNodes: false,
        },
        nodes: {
            color: {
                border: '#000000',
                background: '#5aa92b',
                highlight: {
                    border: '#ea0a0a',
                    background: '#ffd2db'
                }
            }
        }
    }

    $('#process').on('click', _makeAjaxCall)

    function _makeAjaxCall() {
        $.ajax({
            url: "/pageRank",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                url: $("#url")[0].value,
                maxPages: $("#max_requests")[0].value,
            }),
        })
            .done(rawData => {
                _drawGraph(rawData.graphData);
                _printPageRank(rawData.pageRanks);
            })
            .fail(message => alert(JSON.stringify(message, null, 2)));
    }

    function _drawGraph(graphData) {
        const data = {
            nodes: new vis.DataSet(graphData.nodes),
            edges: new vis.DataSet(graphData.edges)
        }


        const network = new vis.Network(container, data, options);
        network.physics.hidden = true;
        setTimeout(() => network.physics.stopSimulation(), 10000)
    }

    function _printPageRank(pageRanks) {
        $pageRanksLabel.empty();
        for (const pageRank of pageRanks) {
            pageRanksLabel.append(pageRank.url + " => " + pageRank.score);
            pageRanksLabel.appendChild(document.createElement("br"));
        }
    }
})