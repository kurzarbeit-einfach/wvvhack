<?PHP

    $data = file_get_contents("php://input");

    $ch = curl_init();

    //set the url, number of POST vars, POST data
    curl_setopt($ch, CURLOPT_URL,            "http://127.0.0.1:8080" );
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1 );
    curl_setopt($ch, CURLOPT_POST,           1 );
    curl_setopt($ch, CURLOPT_POSTFIELDS,     $data ); 
    curl_setopt($ch, CURLOPT_HTTPHEADER,     array('Content-Type: application/json')); 
    
    //execute post
    $result = curl_exec($ch);
    
    //close connection
    curl_close($ch);


    header('Content-type: application/pdf');
    echo $result;

    