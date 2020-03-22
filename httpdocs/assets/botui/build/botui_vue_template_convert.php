<?PHP

    $str = file_get_contents('botui_vue_template.html');
    $str = str_replace(["\"","'","\n"],["\\\"","\\'"," "],$str);
    echo "        template: '".$str."',";