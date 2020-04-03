#!/bin/bash
curl -s 'http://127.0.0.1:8080' -H 'Connection: keep-alive' -H 'Accept: */*' -H 'Sec-Fetch-Dest: empty' -H 'X-Requested-With: XMLHttpRequest' -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36' -H 'Content-Type: application/json' -H 'Origin: https://kurzarbeit-einfach.de' -H 'Referer: https://kurzarbeit-einfach.de/' -H 'Accept-Language: de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6,fr;q=0.5' --data-binary '@/git/pdf/testData.json' -o /dev/null
if [ $? -ne 0 ]; then
    echo "IST KAPUTT"
else
    echo "ALLES OK"
fi


