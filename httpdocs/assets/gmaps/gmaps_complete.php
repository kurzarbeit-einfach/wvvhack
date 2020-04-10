<?PHP

    
    function suggest_location($query){
          $apikey = file_get_contents('apikey.txt'); // Will not be pushed to public GITHUB
          $url = 'https://maps.googleapis.com/maps/api/place/autocomplete/json?key='.$apikey.'&types=address&components=country:de&sensor=false&language=de&input='.urlencode($query);
          $ch = curl_init();
          curl_setopt($ch, CURLOPT_URL, $url);
          curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
          $data = curl_exec($ch);
          curl_close($ch);
          return json_decode($data, true);
    }

    $q = $_GET['q'] ?? "";
    $result = suggest_location(urldecode($q));
    $predictions = $result['predictions'] ?? [];

    $data = [];
    foreach($predictions as $p)
        $data[] = [
            'description'=> $p['description'],
            'place_id'=> $p['place_id']
        ];

    header('Content-type: application/json');
    echo json_encode($data,1);
