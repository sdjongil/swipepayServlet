function getLastDayOfMonth(year, month) {
    return new Date(year, month + 1, 0).getDate();
}

function formatDate(year, month, day) {
    let formattedMonth = (month + 1).toString().padStart(2, '0');
    let formattedDay = day.toString().padStart(2, '0');
    return year+formattedMonth+formattedDay;
}
function formatDateToYYYYMMDD(dateString) {
    var date = new Date(dateString);
    var year = date.getFullYear();
    var month = ('0' + (date.getMonth() + 1)).slice(-2);
    var day = ('0' + date.getDate()).slice(-2);
    return year + month + day;
}
function splitIntoMonthlyIntervals(startDT, endDT) {
    const startYear = parseInt(startDT.slice(0, 4), 10);
    const startMonth = parseInt(startDT.slice(4, 6), 10) - 1;
    const endYear = parseInt(endDT.slice(0, 4), 10);
    const endMonth = parseInt(endDT.slice(4, 6), 10) - 1;

    const dateIntervals = [];
    let currentYear = startYear;
    let currentMonth = startMonth;

    while (currentYear < endYear || (currentYear === endYear && currentMonth <= endMonth)) {
        const startOfMonth = formatDate(currentYear, currentMonth, 1);
        const lastDay = getLastDayOfMonth(currentYear, currentMonth);
        const endOfMonth = formatDate(currentYear, currentMonth, lastDay);
        dateIntervals.push({
            startDT: startOfMonth,
            endDT: endOfMonth,
            limit: null,
            offSet: null
        });

        // 다음 달로 이동
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
    }
    return dateIntervals;
}
let downloadButton;
let loadingIndicator;
let overlay;
let progressText;
document.addEventListener("DOMContentLoaded", function() {
    downloadButton = document.getElementById('downloadButton');
    loadingIndicator = document.getElementById('loadingIndicator');
    overlay = document.getElementById('overlay');
    progressText = document.getElementById('progressText');

    downloadButton.addEventListener('click', async () => {
        // 로딩 인디케이터와 오버레이 표시
        loadingIndicator.style.display = 'block';
        overlay.style.display = 'block';

        var endDt = document.getElementById("endDt").value;
        var startDt = document.getElementById("startDt").value;
        const startDT = formatDateToYYYYMMDD(startDt);
        const endDT = formatDateToYYYYMMDD(endDt);

        const dateIntervals = splitIntoMonthlyIntervals(startDT, endDT);
        console.log(dateIntervals);

        for (let i = 0; i < dateIntervals.length; i++) {
            const dto = dateIntervals[i];
            let int = i + 1;
            progressText.innerText = int + ' / ' + dateIntervals.length +'번째 파일 다운중';
            let startTime = performance.now();

            try {
                const totalChunks = await getTotalChunks(dto);
                console.log(totalChunks)
                const chunks = [];
                for (let i = 0; i < totalChunks; i++) {
                    const chunk = await fetchChunk(i, dto);
                    chunks.push(chunk);
                }
                const mergedCsv = mergeCsvChunks(chunks);
                downloadFile(mergedCsv, dto.startDT + '-' + dto.endDT + '.csv', 'text/csv; charset=UTF-8');
            } catch (error) {
                console.error('Error during download:', error);
            } finally {
                let endTime = performance.now();
                let elapsedTime = (endTime - startTime) / 1000;
                console.log(elapsedTime + "초 ...")
            }
        }

        // 로딩 인디케이터와 오버레이 숨김
        progressText.innerText = '';
        loadingIndicator.style.display = 'none';
        overlay.style.display = 'none';
    });

// 이벤트를 막아 사용자가 다른 작업을 못하게 함
    overlay.addEventListener('click', (e) => {
        e.stopPropagation();
        e.preventDefault();
    });
});


// let url = 'http://ec2-3-35-242-174.ap-northeast-2.compute.amazonaws.com:8080'
let url = 'http://localhost:8080';
async function getTotalChunks(dto) {
    const response = await fetch(url+'/total-chunks',{
        method: 'POST', // Use POST method
        headers: {
            'Content-Type': 'application/json' // Set content type to JSON
        },
        body: JSON.stringify(dto) // Send the JSON data
    });

    if (!response.ok) {
        throw new Error('Failed to fetch total chunks');
    }
    const totalChunks = await response.json();
    return totalChunks;
}

async function fetchChunk(chunkIndex, dto) {
    const response = await fetch(url+`/test?chunkIndex=`+chunkIndex,{
        method: 'POST', // Use POST method
        headers: {
            'Content-Type': 'application/json' // Set content type to JSON
        },
        body: JSON.stringify(dto) // Send the JSON data
    });
    if (!response.ok) {
        console.error(`Error fetching chunk:` + chunkIndex, response.status, response.statusText);
        throw new Error(`Failed to fetch chunk` + chunkIndex);
    }
    return await response.text(); // CSV 데이터를 텍스트로 수신
}

function mergeCsvChunks(chunks) {
    let mergedCsv = '\uFEFF'; // UTF-8 BOM 추가
    for (let i = 0; i < chunks.length; i++) {
        if (i === 0) {
            mergedCsv += chunks[i]; // 첫 번째 청크는 헤더 포함
        } else {
            mergedCsv += chunks[i].split('\n').slice(1).join('\n'); // 이후 청크는 헤더 제외
        }
    }
    return mergedCsv;
}

function downloadFile(content, filename, mimeType) {
    const blob = new Blob([content], { type: mimeType });
    const downloadUrl = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = downloadUrl;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(downloadUrl);
}
