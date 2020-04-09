

    jQuery(function(){
        runAutocomplete();
    });

    var debounceTimer = null;

    function runAutocomplete()
    {
        jQuery('#autocomplete_input').on('keyup',function(){
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(completeIt,500);
        });
    }

    function completeIt()
    {
        var val = jQuery('#autocomplete_input').val();

        jQuery.get('/assets/gmaps/gmaps_complete.php?q='+encodeURIComponent(val),function(data){
            jQuery('#autocomplete_result_list li').remove();
            jQuery(data).each((i,v) => {
                jQuery('#autocomplete_result_list').append('<li data-placeid="'+v.place_id+'">'+v.description+'</li>');
            });
            jQuery('#autocomplete_result_list li').on('click',handleAddress);
        });
    }

    function handleAddress()
    {
        jQuery.get('/assets/gmaps/gmaps_place.php?pid='+encodeURIComponent(jQuery(this).attr('data-placeid')),function(data){
            parent.postMessage(data,'https://kurzarbeit-einfach.de');
        });
    }


