<?PHP

    $data = file_get_contents("php://input");

    if (empty($data)) {
        exit(1);
    }

    $decoded = json_decode($data,1);
    
    $path = null;
    if (isset($decoded['path'])) {
        $path = $decoded['path'];
        unset($decoded['path']);
    }

    // basic logging
    $jsonLog = [
        "datetime" => gmdate("Y-m-d H:i:s"),
        "action" => "pdf_download",
        "decisionPath" => isset($path) ? $path : null
    ];
    $fp = fopen("logs/pdf.log", "a+");
    fwrite($fp, json_encode($jsonLog)."\n");
    fclose($fp);

    $ch = curl_init();
    //set the url, number of POST vars, POST data
    curl_setopt($ch, CURLOPT_URL,            "http://127.0.0.1:8080" );
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1 );
    curl_setopt($ch, CURLOPT_POST,           1 );
    curl_setopt($ch, CURLOPT_POSTFIELDS,     json_encode($decoded) ); 
    curl_setopt($ch, CURLOPT_HTTPHEADER,     array('Content-Type: application/json')); 
    
    //execute post
    $result = curl_exec($ch);
    
    //close connection
    curl_close($ch);

    header('Content-type: application/pdf');
    echo $result;

    