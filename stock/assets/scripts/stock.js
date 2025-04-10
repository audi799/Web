const apiKey = 'zLLw9vnuyP4OtHI2Ol7GNM2xx6%2FZ0ylj0Rl%2FjR8mBPHTbP0uGOFw4qcP5aF4C8fGjNS4PnhUPTd5bIMe8yBgtw%3D%3D';

const $loading = document.body.querySelector(':scope > .loading');
const $searchForm = document.body.querySelector(':scope > .header > .search-form');
const $chart = document.body.querySelector(':scope > .chart');

const hideLoading = () => $loading.classList.remove('visible');
const showLoading = () => $loading.classList.add('visible');

const closeChart = () => {
    $chart.classList.remove('visible');
}
const openChart = (code) => {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        hideLoading();
        if (xhr.status < 200 || xhr.status >= 400) {
            alert('차트 데이터를 불러오지 못하였습니다.');
            return;
        }
        // mkp 시가 | hipr 고가 | lopr 저가 \ dpr 종가 \ trqu 거래량
        const response = JSON.parse(xhr.responseText);
        const rawArray = response['response']['body']['items']['item'];
        const ohlcArray = [];
        const volumeArray = [];

        for (const rawObject of rawArray) {
            const dateString = rawObject['basDt']; // 20250307
            const year = parseInt(dateString.substring(0, 4)); // 2025
            const month = parseInt(dateString.substring(4, 6)); // 03
            const day = parseInt(dateString.substring(6, 8)); // 07
            const date = new Date(year, month, day);

            const open = parseFloat(rawObject['mkp']); // 6290
            const high = parseFloat(rawObject['hipr']);
            const low = parseFloat(rawObject['lopr']);
            const close = parseFloat(rawObject['clpr']);
            const volume = parseFloat(rawObject['trqu']);

            ohlcArray.push({
               x: date,
               y: [open, high, low, close]
            });
            volumeArray.push({
                x: date,
                y: volume
            });
        }
        const $ohlcRender = $chart.querySelector(':scope > .body > .chart.ohlc');
        const ohlcOption = {
            series: [{data: ohlcArray}],
            chart: {
                id: 'ohlc',
                type: 'candlestick',
                height: 400,
                toolbar: {
                    autoSelected: 'pan',
                    show: false
                },
                zoom: {
                    enabled: false
                }
            },
            plotOptions: {
                candlestick: {
                    colors: {
                        upward: '#e74c3c',
                        downward: '#2980b9'
                    }
                }
            },
            xaxis: {
                type: 'datetime'
            }
        };
        $ohlcRender.innerHTML = '';
        const ohlcChart = new ApexCharts($ohlcRender, ohlcOption);
        ohlcChart.render();

        const $volumeRender = $chart.querySelector(':scope > .body > .chart.volume');
        const volumeOption = {
            series: [{
                name: '거래량',
                data: volumeArray
            }],
            chart: {
                type: 'bar',
                height: 160,
                brush: {
                    enabled: true,
                    target: 'ohlc'
                },
                selection: {
                    enabled: true,
                    xaxis: {
                        max: volumeArray[0].x.getTime(),
                        min: volumeArray[volumeArray.length < 250 ? volumeArray.length - 1 : 250].x.getTime()
                    },
                    fill: {
                        color: '#cccccc',
                        opacity: 0.4
                    },
                    stroke: {
                        color: '#0d47a1'
                    }
                }
            },
            dataLabels: {
                enabled: false
            },
            plotOptions: {
                bar: {
                    columnWidth: '80%',
                    colors: {
                        upward: '#e74c3c',
                        downward: '#2980b9'
                    }
                }
            },
            stroke: {
                width: 0
            },
            xaxis: {
                type: 'datetime',
                axisBorder: {
                    offsetX: 13
                }
            },
            yaxis: {
                labels: {
                    show: false
                }
            }
        };

        $volumeRender.innerHTML = '';
        const volumeChart = new ApexCharts($volumeRender, volumeOption);
        volumeChart.render();

        $chart.classList.add('visible');
    };

    const date = new Date();
    const year = date.getFullYear(); // 2025
    const month = (date.getMonth() + 1).toString().padStart(2, '0') ; // 2 + 1 = '3'
    const day = date.getDate().toString().padStart(2, '0'); // '10' // padStart 2글자로 맞춰줌. 빈공간 0으로 채움
    const beginBasDt = `${year - 2}${month}${day}`; // 20200310
    const endBasDt = `${year}${month}${day}`; // 20250310
    const likeSrtnCd = code.replace('A', ''); // A005930 -> 005930

    xhr.open('GET', `https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo?serviceKey=${apiKey}&numOfRows=5000&pageNo=1&resultType=json&beginBasDt=${beginBasDt}&endBasDt=${endBasDt}&likeSrtnCd=${likeSrtnCd}`);
    xhr.send();
    showLoading();
}

$chart.querySelector(':scope > .header > [name="close"]').onclick = () => {
    closeChart();
};

$searchForm.onsubmit = (e) => {
    e.preventDefault(); /* 자동 새로고침 막는거 */
    const keyword = $searchForm['keyword'].value;
    const $table = document.body.querySelector(':scope > .table');
    const $trs = document.body.querySelectorAll(':scope > .table > tbody > tr');

    $table.style.display = 'none';

    for (const $tr of $trs) {
        $tr.style.display = $tr.innerText.includes(keyword) === true ? 'table-row' : 'none';
    }

    $table.style.display = 'table'; // $table.style.display = ''; 이래도됨.
};

document.body.querySelector(':scope > .header > .button[name="refresh"]').onclick = () => {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }

        hideLoading();

        if (xhr.status < 200 || xhr.status >= 400) {
            alert(`요청을 전송하는 도중 오류가 발생하였습니다. (${xhr.status})`);
            return;
        }

        const response = JSON.parse(xhr.responseText);
        const stocks = response['response']['body']['items']['item'];
        const $tbody = document.body.querySelector(':scope > .table > tbody');

        let tbodyHtml = '';

        for (const stock of stocks) {
            /*
            "basDt": "20250306",
            "srtnCd": "A000020",
            "isinCd": "KR7000020008",
            "mrktCtg": "KOSPI",
            "itmsNm": "동화약품",
            "crno": "1101110043870",
            "corpNm": "동화약품(주)"
             */
            const trHtml = `
                <tr data-code="${stock['srtnCd']}">
                    <th scope="row">${stock['srtnCd']}</th>
                    <td>${stock['isinCd']}</td>
                    <td>${stock['mrktCtg']}</td>
                    <td>${stock['itmsNm']}</td>
                    <td>${stock['crno']}</td>
                    <td>${stock['corpNm']}</td>
                    <td>
                        <div class="button-container">
                            <button class="button" type="button">재무제표</button>
                            <button class="button" name="chart" type="button">차트</button>
                        </div>
                    </td>
                </tr>`;
            tbodyHtml += trHtml;
        }
        $tbody.innerHTML = tbodyHtml;
        $tbody.querySelectorAll(':scope > tr').forEach($tr => {
            const $chartButton = $tr.querySelector('button[name="chart"]');
            $chartButton.onclick = () => {
                const code = $tr.dataset['code'];
                openChart(code); // openChart('A00660');
            }
        })
    };

    xhr.open('GET', 'https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo?serviceKey=zLLw9vnuyP4OtHI2Ol7GNM2xx6%2FZ0ylj0Rl%2FjR8mBPHTbP0uGOFw4qcP5aF4C8fGjNS4PnhUPTd5bIMe8yBgtw%3D%3D&numOfRows=100&pageNo=1&resultType=json');
    xhr.send();
    showLoading();
};
