const $testButton = document.body.querySelector(':scope > .test');
$testButton.onclick = () => {
    const date = new Date(1741928400);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, 0);
    const day = date.getDate().toString().padStart(2, 0);
    const hour = date.getHours().toString().padStart(2, 0);
    const minute = date.getMinutes().toString().padStart(2, 0);
    const second = date.getSeconds().toString().padStart(2, 0);
    const dateTime = `${year}-${month}-${day} ${minute}:${hour}:${second}`;

    alert(dateTime);
};

const $addressDialog = document.getElementById('addressDialog');

const hideAddressDialog = () => $addressDialog.classList.remove('visible');

const showAddressDialog = (callback) => {
    const $content = $addressDialog.querySelector(':scope > .content');
    $content.innerHTML = '';
    new daum.Postcode({
        width: '100%',
        height: '100%',
        oncomplete: (data) => {
            // console.log(data);
            if (typeof callback === 'function') {
                callback(data);
            }
        }
    }).embed($content);
    $addressDialog.classList.add('visible');
}

const $payDialog = document.getElementById('payDialog');
const hidePayDialog = () => {
    $payDialog.classList.remove('visible')
};

let histories = [];
const showPayDialog = (args) => {
    $payDialog.querySelector(':scope > .content > .spec > .item > .value.depart').innerText = args['depart'];
    $payDialog.querySelector(':scope > .content > .spec > .item > .value.destination').innerText = args['destination'];
    $payDialog.querySelector(':scope > .content > .spec > .item > .value.car-type').innerText = args['carType'];
    $payDialog.querySelector(':scope > .content > .spec > .item > .value.charge').innerText = args['charge'].toLocaleString() + '원';
    $payDialog.querySelector(':scope > .content > .button-container > .button.cancel').onclick = () => hidePayDialog();
    $payDialog.querySelector(':scope > .content > .button-container > .button.confirm').onclick = () => {

        const imp = window.IMP;
        const date = new Date();

        imp.init('imp88338183');
        imp.request_pay({
            pg: 'kakaopay.TC0ONETIME',
            pay_method: 'card',
            merchant_uid: `IMP-${date.getTime()}`,
            name: 'TAJA',
            amount: args['charge'],
            buyer_email: 'truekim799@gmail.com',
            buyer_name: '김영롱'
        }, (resp) => {
            if (resp.success === true) {
                const history = {
                    timestamp: date.getTime(),
                    depart: args['depart'],
                    destination: args['destination'],
                    charge: args['charge']
                };
                // histories = JSON.parse(localStorage.getItem('history')) ?? [];
                histories.push(history);
                localStorage.setItem('histories', JSON.stringify(histories));
                alert('결제가 완료되었습니다. 택시가 곧 출발지에 도착합니다.');
            } else {
                alert('결제가 취소되었습니다.');
            }
        });
    };
    $payDialog.classList.add('visible');
};

const $historyDialog = document.getElementById('historyDialog');
const hideHistoryDialog = () => $historyDialog.classList.remove('visible');
const showHistoryDialog = () => {
    const $list = $historyDialog.querySelector(':scope > .content > .list');
    $list.querySelectorAll('.item').forEach(($item) => $item.remove());
    const histories = JSON.parse(localStorage.getItem('histories')) ?? [];
    const $empty = $list.querySelector(':scope > .message');

    $historyDialog.classList.add('visible');

    if (histories.length === 0) {
        $empty.classList.add('visible');
        return;
    }
    $empty.classList.remove('visible');
    $list.innerHTML = '';
    for (const history of histories) {
        const date = new Date(history['timestamp']);
        // const year = date.getFullYear();
        // const month = (date.getMonth() + 1).toString().padStart(2, 0);
        // const day = date.getDate().toString().padStart(2, 0);
        // const hour = date.getHours().toString().padStart(2, 0);
        // const minute = date.getMinutes().toString().padStart(2, 0);
        // const second = date.getSeconds().toString().padStart(2, 0);
        // const timeStamp = `${year}-${month}-${day} ${minute}:${hour}:${second}`;
        const timeStamp = date.toISOString().split('T').map((s) => s.split('.')[0]).join();

        $list.innerHTML += `
                            <li class="item">
                                <span class="timestamp">${timeStamp}</span>
                                <span class="detail">
                                    <span class="depart">${history['depart']}</span>
                                    <span class="arrow">&#x2192;</span>
                                    <span class="destination">${history['destination']}</span>
                                    <span class="stretch" role="none"></span>
                                    <span class="charge">${history['charge'].toLocaleString()} 원</span>
                                </span>
                            </li>`;
    }

    // 짧은 코드.
    // $list.innerHTML += histories.map((history) => `
    //                 <li class="item">
    //                     <span class="timestamp">${timeStamp}</span>
    //                     <span class="detail">
    //                         <span class="depart">${history['depart']}</span>
    //                         <span class="arrow">&#x2192;</span>
    //                         <span class="destination">${history['destination']}</span>
    //                         <span class="stretch" role="none"></span>
    //                         <span class="charge">${history['charge'].toLocaleString()} 원</span>
    //                     </span>
    //                 </li>`).reduce((acc, x) => acc + x, '');
};

const $nav = document.getElementById('nav');
$nav.querySelector(':scope > .history').onclick = () => showHistoryDialog();
$historyDialog.querySelector(':scope > .content > .button-container > .close').onclick = () => hideHistoryDialog();

const $console = document.getElementById('console');
const $locationForm = $console.querySelector(':scope > .location-form');

const departInfoWindow = new kakao.maps.InfoWindow({
    content: '<div style="width: 150px; padding: 0.5rem 0; text-align: center;">출발지</div>'
});
const destinationInfoWindow = new kakao.maps.InfoWindow({
    content: '<div style="width: 150px; padding: 0.5rem 0; text-align: center;">도착지</div>'
});
let departMarker;
let destinationMarker;

let departCoordinates;
let destinationCoordinates;

let polyline;

$locationForm['findDepart'].addEventListener('click', () => {
    showAddressDialog((data) => {
        $locationForm['addressDepart'].value = data['roadAddress'];
        hideAddressDialog();
        convertAddressToCoordinates(data['roadAddress'], (Coordinates) => {
            departCoordinates = Coordinates;
            departMarker?.setMap(null);
            departMarker = new kakao.maps.Marker({
                map: mapInstance,
                position: departCoordinates
            });
            departInfoWindow.open(mapInstance, departMarker);
            if (departCoordinates == null || destinationCoordinates == null) {
                mapInstance.setCenter(departCoordinates);
                mapInstance.setLevel(3);
            } else {
                findPath();
            }
        });
    });
})

$locationForm['findDestination'].addEventListener('click', () => {
    showAddressDialog((data) => {
        $locationForm['addressDestination'].value = data['roadAddress'];
        hideAddressDialog();
        convertAddressToCoordinates(data['roadAddress'], (Coordinates) => {
            destinationCoordinates = Coordinates;
            destinationMarker?.setMap(null);
            destinationMarker = new kakao.maps.Marker({
                map: mapInstance,
                position: destinationCoordinates
            });
            destinationInfoWindow.open(mapInstance, destinationMarker);
            if (departCoordinates == null || destinationCoordinates == null) {
                mapInstance.setCenter(destinationCoordinates);
                mapInstance.setLevel(3);
            } else {
                findPath();
            }
        });
    });
})

const findPath = () => {
    const bounds = new kakao.maps.LatLngBounds();
    bounds.extend(departCoordinates);
    bounds.extend(destinationCoordinates);
    mapInstance.setBounds(bounds);

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 400) {
            alert('!');
            return;
        }
        const response = JSON.parse(xhr.responseText);
        const roads = response['routes'][0]['sections'][0]['roads'];
        let paths = [];
        for (const road of roads) {
            road['vertexes'].forEach((v, i, a) => {
                if (i % 2 === 0) {
                    paths.push(new kakao.maps.LatLng(a[i + 1], a[i]));
                }
            });
        }

        polyline?.setMap(null);
        polyline = new kakao.maps.Polyline({
            path: paths,
            strokeWeight: 5,
            strokeColor: '#000000',
            strokeOpacity: 0.7,
            strokeStyle: 'solid'
        });
        polyline.setMap(mapInstance);

        const guides = response['routes'][0]['sections'][0]['guides'];
        const $duration = $console.querySelector(':scope > .estimate > .text-container > .text > .value.duration');
        const $charge = $console.querySelector(':scope > .estimate > .text-container > .text > .value.charge');
        const $route = $console.querySelector(':scope > .route');

        let distance = 0;
        let duration = 0;

        $route.querySelectorAll(':scope > .item').forEach(($item) => $item.remove());
        $route.querySelector(':scope > .message.empty').classList.remove('visible');

        for (const guide of guides) {
            distance += guide['distance'];
            duration += guide['duration'];

            if (guide['name'].length > 0) {
                const $name = document.createElement('span');
                $name.classList.add('name');
                $name.innerText = guide['name'];
                const $guidance = document.createElement('span');
                $guidance.classList.add('guidance');
                $guidance.innerText = guide['guidance'];
                const $item = document.createElement('li');
                $item.classList.add('item');
                $item.append($name, $guidance);
                $item.addEventListener('click', () => {
                    const pos = new kakao.maps.LatLng(guide['y'], guide['x']);
                    mapInstance.setCenter(pos);
                    mapInstance.setLevel(3);
                });

                $route.append($item);
            }
        }

        const charge = 4500 + Math.trunc(distance / 131) * 100 + Math.trunc(duration / 30) * 100;
        const $estimate = $console.querySelector(':scope > .estimate');
        const $pay = $console.querySelector(':scope > .pay');
        $duration.innerText = Math.trunc(duration / 60) + '분';
        $charge.innerText = charge.toLocaleString() + '원'; // toLocaleString 1000단위로 쉼표
        $estimate.classList.add('visible');
        $console.querySelector(':scope > .estimate').classList.add('visible');
        $pay.innerText = `${charge.toLocaleString()}원 결제하기`;
        $pay.classList.add('visible');
        $pay.onclick = () => {
            showPayDialog({
                depart: $locationForm['addressDepart'].value,
                destination: $locationForm['addressDestination'].value,
                carType: $estimate.querySelector(':scope > .car')['type'].value,
                charge: charge
            });
        };

        const $car = $console.querySelector(':scope > .estimate > .car');
        $car['type'].value = 'basic';
    };
    const url = new URL('https://apis-navi.kakaomobility.com/v1/directions');
    url.searchParams.set('origin', `${departCoordinates.getLng()}, ${departCoordinates.getLat()}`);
    url.searchParams.set('destination', `${destinationCoordinates.getLng()}, ${destinationCoordinates.getLat()}`);
    xhr.open('GET', url.toString());
    xhr.setRequestHeader('Authorization', 'KakaoAK 481b07163c8df98408a6f8898c567235');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
}

const $map = document.getElementById('map');
const mapInstance = new kakao.maps.Map($map, {
    center: new kakao.maps.LatLng(35.8655753, 128.59339),
    level: 3
});

const convertAddressToCoordinates = (address, callback) => {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.addressSearch(address, (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
            const coordinates = new kakao.maps.LatLng(result[0].y, result[0].x);
            if (typeof callback === 'function') {
                callback(coordinates);
            }
        }
    });
};
