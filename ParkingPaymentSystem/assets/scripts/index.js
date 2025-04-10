const $dialog = document.getElementById('dialog');
const $content = $dialog.querySelector(':scope > .content');
const $text = $dialog.querySelector(':scope > .content > .text');
const $close = $dialog.querySelector(':scope > .content > .button-container > .close');
const showDialog = (message) => {
    $text.innerText = message;
    $dialog.classList.add('visible');
    $content.classList.add('visible');
};
const hideDialog = () => {
    $dialog.classList.remove('visible');
    $content.classList.remove('visible');
};

const $header = document.getElementById('header');
const $backMenu = $header.querySelector(':scope > .menu > .item > .back-icon');
const $homeMenu = $header.querySelector(':scope > .menu > .item > .home-icon');

const $main = document.getElementById('main');
const $home = $main.querySelector(':scope > .home');
const $input = $main.querySelector(':scope > .input');
const $info = $main.querySelector(':scope > .info');
const $output = $main.querySelector(':scope > .output');

const $homePayment = $home.querySelector(':scope > .button-container > .payment');
const $homeJoin = $home.querySelector(':scope > .button-container > .join');

//사용자가 차량번호 입력하면, 표시될 박스
const $numberBox_1 = $input.querySelector(':scope > .input-container > .number > .box-container > .box.first');
const $numberBox_2 = $input.querySelector(':scope > .input-container > .number > .box-container > .box.second');
const $numberBox_3 = $input.querySelector(':scope > .input-container > .number > .box-container > .box.third');
const $numberBox_4 = $input.querySelector(':scope > .input-container > .number > .box-container > .box.fourth');

//차량번호 입력할 번호 키패드
const $keypad = $input.querySelectorAll(':scope > .input-container > .keypad > .key-row > .button');

//현재 입차되어있는 차량 리스트 (정상작동여부 확인 위한 화면표시용)
$carList = $input.querySelector(':scope > .car-list');

//주차한 차량의 정보 및 비용 표시
const $picture = $info.querySelector(':scope > .select-container > .car-container > .car > .picture');
const $carNumber = $info.querySelector(':scope > .select-container > .car-container > .car > .car-number');
const $inputTime = $info.querySelector(':scope > .select-container > .car-container > .parking > .table .input-time');
const $parkingTime = $info.querySelector(':scope > .select-container > .car-container > .parking > .table .parking-time');
const $amount = $info.querySelector(':scope > .select-container > .car-container > .parking > .table .amount');
const $kakaoPay = $info.querySelector(':scope > .mobile > .payment');

//결제 결과 화면 표시
const $cost = $output.querySelector(':scope > .amount > .cost');
const $paymentDateTime = $output.querySelector(':scope > .table .payment.datetime');
const $paymentName = $output.querySelector(':scope > .table .payment.name');

//최종 결제 완료 후의 버튼
const $returnHome = $output.querySelector(':scope > .button-container > .home');
const $print = $output.querySelector(':scope > .button-container > .print');

// 현재 보이는 화면을 index 로 저장.
// 0: home
// 1: input
// 2: info
// 3: output
let positionIndex;

// 입차된 차량 리스트
// 6개의 데이타 고정 값이고, 이후의 데이타는 api불러와서 다음 인덱스에 붙여서 입차 차량 리스트를 만들어냄.
// 테스트 및 정상작동 여부 확인하기 위해서 강제로 만든데이타임.
let carList = [{
    number: '38수2603',
    inputTime: '2025-03-01 07:21:22',
    picture: './assets/images/car.2603.JPG'
}, {
    number: '65호3563',
    inputTime: '2025-03-08 11:54:11',
    picture: './assets/images/car.3563.JPG'
}, {
    number: '25가4464',
    inputTime: '2025-03-11 13:10:05',
    picture: './assets/images/car.4464.JPG'
}, {
    number: '34서4946',
    inputTime: '2025-03-17 22:31:00',
    picture: './assets/images/car.4946.JPG'
}, {
    number: '45어5733',
    inputTime: '2025-03-18 01:20:04',
    picture: './assets/images/car.5733.JPG'
}, {
    number: '50조9550',
    inputTime: '2025-03-18 10:27:44',
    picture: './assets/images/car.9550.JPG'
}];

makeDataByAPI(refreshCarList);

$close.onclick = hideDialog;
$homeMenu.onclick = goHome;
$backMenu.onclick = goBack;
$homePayment.onclick = goInput;
$homeJoin.onclick = () => {
    showDialog('현재 회원가입은 지원되지 않습니다.\n관리소에 문의해주세요');
};
$kakaoPay.onclick = payCharge;
$returnHome.onclick = goHome;
$print.onclick = () => {
    showDialog('영수증이 출력됩니다.\n잡아당기지 마세요.')
};

// 키패드의 버튼들 이벤트 연결
for (const button of $keypad) {
    const buttonValue = button.innerText;
    if (buttonValue.length === 1) {
        button.onclick = () => {
            setCarNumber(buttonValue);
        }
    } else if (buttonValue === '취소') {
        button.onclick = setCancel;
    } else if (buttonValue === '확인') {
        button.onclick = findCarNo;
    }
}

// 홈화면으로 이동
function goHome() {
    if (!$home.classList.contains('visible')) {
        $home.classList.add('visible');
    }
    if ($header.classList.contains('visible')) {
        $header.classList.remove('visible')
    }
    if ($input.classList.contains('visible')) {
        $input.classList.remove('visible');
    }
    if ($info.classList.contains('visible')) {
        $info.classList.remove('visible');
    }
    if ($output.classList.contains('visible')) {
        $output.classList.remove('visible');
    }

    $numberBox_1.innerText = '';
    $numberBox_2.innerText = '';
    $numberBox_3.innerText = '';
    $numberBox_4.innerText = '';

    positionIndex = 0;
}

// 차량번호입력 화면으로 이동
function goInput() {
    if (!$input.classList.contains('visible')) {
        $input.classList.add('visible');
    }
    if (!$header.classList.contains('visible')) {
        $header.classList.add('visible')
    }
    if ($home.classList.contains('visible')) {
        $home.classList.remove('visible');
    }
    if ($info.classList.contains('visible')) {
        $info.classList.remove('visible');
    }
    if ($output.classList.contains('visible')) {
        $output.classList.remove('visible');
    }

    refreshCarList();

    positionIndex = 1;
}

// 차량번호 입력 후 금액안내 화면으로 이동
function goInfo() {
    if (!$info.classList.contains('visible')) {
        $info.classList.add('visible');
    }
    if (!$header.classList.contains('visible')) {
        $header.classList.add('visible')
    }
    if ($home.classList.contains('visible')) {
        $home.classList.remove('visible');
    }
    if ($input.classList.contains('visible')) {
        $input.classList.remove('visible');
    }
    if ($output.classList.contains('visible')) {
        $output.classList.remove('visible');
    }

    positionIndex = 2;
}

// 결제완료 결과 화면으로 이동
function goOutPut() {
    if (!$output.classList.contains('visible')) {
        $output.classList.add('visible');
    }
    if (!$header.classList.contains('visible')) {
        $header.classList.add('visible')
    }
    if ($home.classList.contains('visible')) {
        $home.classList.remove('visible');
    }
    if ($info.classList.contains('visible')) {
        $info.classList.remove('visible');
    }
    if ($input.classList.contains('visible')) {
        $input.classList.remove('visible');
    }

    const date = new Date();
    const year = date.getFullYear().toString();
    const month = (date.getMonth() + 1).toString().padStart(2, 0);
    const day = date.getDate().toString().padStart(2, 0);
    const hour = date.getHours().toString().padStart(2, 0);
    const minute = date.getMinutes().toString().padStart(2, 0);
    const second = date.getSeconds().toString().padStart(2, 0);
    const nowDateTime = `${year}-${month}-${day} ${hour}:${minute}:${second}`;

    $cost.innerText = $amount.innerText;
    $paymentDateTime.innerText = nowDateTime;
    $paymentName.innerText = $carNumber.innerText;

    positionIndex = 3;
}

// 현재 화면에서 이전화면으로 이동
function goBack() {
    switch (positionIndex) {
        case 1: // Input 화면일 경우
            goHome();
            break;
        case 2: // Info 화면일 경우
            goInput();
            break;
        case 3: // OutPut 화면일 경우
            goInfo();
            break;
    }
}

// 차량번호 입력 시, 첫 박스부터 끝 박스까지 순차적으로 값을 채우도록 세팅
function setCarNumber(number) {
    if ($numberBox_1.innerText === '') {
        $numberBox_1.innerText = number;
    } else if ($numberBox_2.innerText === '') {
        $numberBox_2.innerText = number;
    } else if ($numberBox_3.innerText === '') {
        $numberBox_3.innerText = number;
    } else if ($numberBox_4.innerText === '') {
        $numberBox_4.innerText = number;
    }
}

// 차량번호 끝자리부터 한자리씩 취소시키는 로직
function setCancel() {
    if ($numberBox_4.innerText !== '') {
        $numberBox_4.innerText = '';
    } else if ($numberBox_3.innerText !== '') {
        $numberBox_3.innerText = '';
    } else if ($numberBox_2.innerText !== '') {
        $numberBox_2.innerText = '';
    } else if ($numberBox_1.innerText !== '') {
        $numberBox_1.innerText = '';
    }
}

function findCarNo() {
    const carNo = $numberBox_1.innerText + $numberBox_2.innerText + $numberBox_3.innerText + $numberBox_4.innerText;

    if (carNo.length === 4) {
        for (let loop = 0; loop < carList.length; loop++) {
            const onlyNumber = carList[loop]['number'].split('').reverse().join('').substring(0, 4).split('').reverse().join('');

            if (onlyNumber === carNo) {
                //입차된 차량을 찾으면, 사진&차량번호&입차시간 등의 각종 정보를 표시한다
                $carNumber.innerText = carList[loop]['number'];
                $picture.src = carList[loop]['picture'];
                $inputTime.innerText = carList[loop]['inputTime'];
                $parkingTime.innerText = getParkingHourMinute(carList[loop]['inputTime']);
                $amount.innerText = getAmount(getParkingMinute(carList[loop]['inputTime']));
                goInfo();
                break;
            } else {
                if (loop === carList.length - 1) {
                    showDialog('해당 차량이 존재하지 않습니다.\n차량번호를 다시 확인해주세요.');
                }
            }
        }
    } else {
        showDialog('차량번호를 다시 확인해 주세요.');
    }
}

// 입차시간부터 현재시간까지를 계산하여, 스크린에 표시할 주차시간을 리턴한다.
function getParkingHourMinute(startTime) {
    const startDate = new Date(startTime);
    const endDate = new Date();

    const totalMinute = (endDate - startDate) / (1000 * 60);
    const hour = Math.trunc(totalMinute / 60);
    const minute = Math.ceil(totalMinute - (hour * 60));

    return `${hour}시간 ${minute}분`;
}

// 입차시간부터 현재시간까지를 계산하여, 분으로 리턴한다. (주차요금 계산하기 위함)
function getParkingMinute(startTime) {
    const startDate = new Date(startTime);
    const endDate = new Date();

    return Math.ceil((endDate - startDate) / (1000 * 60));
}

// 주차 금액을 계산하여 리턴한다.
function getAmount(minute) {
    const originHalfMinute = 2000; // 최초 30분 2000원
    const tenMinute = 200; // 10분당 200원 추가
    const allDay = 10000; // 24시간 주차 시 10000원 추가
    let calcData, calcData2;

    if (minute <= 30) {
        //최초30분 이하면 2000원
        return originHalfMinute;
    } else if (minute <= 1440) {
        calcData = minute - 30;
        calcData = Math.ceil(calcData / 10);
        //최초 30분 이상이면 기본요금 2000원 + 10분당 200원
        return (originHalfMinute + (calcData * tenMinute)).toLocaleString() + ' 원';
    } else {
        //24시간 이상 주차일 경우 24시간당 10000원 + 10분당 200원
        calcData =  Math.trunc(minute / 1440); // 24시간 주차횟수
        calcData2 = minute - (calcData * 1440); // 24시간 이내의 시간을 구함.
        calcData2 = Math.ceil(calcData2 / 10); //10 분당 주차횟수
        return ((calcData * allDay) + (calcData2 * tenMinute)).toLocaleString() + ' 원';
    }
}

function payCharge() {
    const date = new Date();
    const imp = window.IMP;

    imp.init('imp88338183');
    imp.request_pay({
        pg: 'kakaopay.TC0ONETIME',
        pay_method: 'card',
        merchant_uid: `IMP-${date.getTime()}`,
        name: '(주)하이파킹 주차정산 시스템',
        amount: $amount.innerText,
        buyer_email: 'truekim799@gmail.com',
        buyer_name: '김영롱'
    }, (resp) => {
        if (resp.success === true) {
            //결제 후 출차하는 차량은 리스트에서 제거한다.
            for (let loop = 0; loop < carList.length; loop++) {
                if (carList[loop]['number'] === $carNumber.innerText) {
                    carList.splice(loop, 1);
                    break;
                }
            }
            goOutPut();
        } else {
            showDialog(`결제가 진행되지 않았습니다.\n재시도 해주세요.`);
            console.log(`payCharge()\n결제에 실패하였습니다. (${resp['error_msg']})`);
        }
    });
}

// 실제 입출차 차량에 관해서 제공해주는 API가 없어서, 광명시에서 국가관리각종 차량들의 리스트를 불러오는 API를 사용하여,
// 불러온 차량번호를가지고 입차된차로 강제로 만들어서, 해당 프로젝트의 데이타에 맞게 만듦.
// * 차량번호는 민감한 부분이라 그런지, 차량번호를 확인할 수 있는 API 자체도 거의 없었음. 보통 차종 정도..?
// http://data.gm.go.kr/portal/data/service/selectServicePage.do?infId=4E7AVFFJ00134343J752993
// 위 사이트에서 API 사용
// API URL: https://data.gm.go.kr/openapi/scarresult?KEY=75a27526caf241feb71c7659590b96bf&Type=json&pIndex=1&pSize=10
function makeDataByAPI(callback) {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 400) {
            showDialog('네트워크 통신 오류!/\n관리자에게 문의하세요!');
            return;
        }
        const response = Object.values(JSON.parse(xhr.responseText));
        const datas = response[0][1]['row'];
        for (const data of datas) {
            const onlyNumber = data['VEH_REG_NO'].split('').reverse().join('').substring(0, 4).split('').reverse().join('');

            if (!carList.includes(data['VEH_REG_NO'])) {
                carList.push({
                    number: data['VEH_REG_NO'],
                    inputTime: getRandomInputTime(),
                    picture: `./assets/images/car.${onlyNumber}.png`
                })
            }
        }
        callback();
    };
    const url = 'https://data.gm.go.kr/openapi/scarresult?KEY=75a27526caf241feb71c7659590b96bf&Type=json&pIndex=1&pSize=20';
    xhr.open('GET', url);
    xhr.send();
}

// 테스트할 차량리스트를 강제로 만들 때, 입력하게될 입차시간을 랜덤으로 만든다.
function getRandomInputTime() {
    const startDate = new Date(2025, 2, 1);
    const endDate = new Date(2025, 2, 19);
    const randomDateTime = startDate.getTime() + Math.random() * (endDate.getTime() - startDate.getTime());
    const date = new Date(randomDateTime);

    const year = date.getFullYear().toString();
    const month = (date.getMonth() + 1).toString().padStart(2, 0);
    const day = date.getDate().toString().padStart(2, 0);
    const hour = date.getHours().toString().padStart(2, 0);
    const minute = date.getMinutes().toString().padStart(2, 0);
    const second = date.getSeconds().toString().padStart(2, 0);

    return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
}

// 현재 입차되어있는 차량 리스트 (정상작동여부 확인 위한 화면표시용)
function refreshCarList() {
    let carListToText = '현재 입차된 차량번호: ';
    for (let loop = 0; loop < carList.length; loop++) {
        const onlyNumber = carList[loop]['number'].split('').reverse().join('').substring(0, 4).split('').reverse().join('');

        carListToText += onlyNumber + ' ';
    }
    $carList.innerText = carListToText;
}