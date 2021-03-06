<?PHP

    
    function getPlaceInfo($placeId){
          $apikey = file_get_contents('apikey.txt'); // Will not be pushed to public GITHUB
          $url = 'https://maps.googleapis.com/maps/api/place/details/json?key='.$apikey.'&language=de&placeid='.urlencode($placeId);
          $ch = curl_init();
          curl_setopt($ch, CURLOPT_URL, $url);
          curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
          $data = curl_exec($ch);
          curl_close($ch);
          return json_decode($data, true);
    }

    $pid = $_GET['pid'] ?? "";
    $result = getPlaceInfo($pid);
    $result = $result['result'] ?? [];

    header('Content-type: application/json');
    echo json_encode($result,1);
