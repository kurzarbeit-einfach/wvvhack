var progressTotalSteps = 4;
var progressCurrentSteps = 0;

$().ready(function() {

    initProgressBar();
    $('.container button.next').click(scrollToContent);

    $('.tooltip').click(showTooltip);
    $('.tooltipContent .close').click(hideTooltip);

    runBot();


});

function scrollToContent(e) {

    var targetElement = $(this).attr('data-target');
    var targetPosition = $('.container.'+targetElement).offset().top;
    $('html,body').stop(true).animate({scrollTop:targetPosition}, 1000);
    increaseProgess(); 
}

function showTooltip(e) {

    var textContent = $(this).attr('data');

    if( !$('.tooltipContent').hasClass('show') ) {

        $('.tooltipContent').addClass('show');
        $('.tooltipContent .text').html(textContent);

        if( $(window).width() > 950 ) {
            var targetPositionX = $(this).offset().left + 22;
            var targetPositionY = $(this).offset().top - 2; 
        } else {
            var targetPositionY = $(this).offset().top - 25;
            //if(targetPositionY < 20) targetPositionY = 20;
        }
       

        $('.tooltipContent').css({ left: targetPositionX+'px', top: targetPositionY+'px'});

    } else {

        $('.tooltipContent').removeClass('show');
        $('.tooltipContent .text').html('');
        $('.tooltipContent').removeAttr('style');

    }

}

function hideTooltip(e) {

        $('.tooltipContent').removeClass('show');
        $('.tooltipContent .text').html('');
        $('.tooltipContent').removeAttr('style');

}

function initProgressBar() {
    for (i = 0; i < progressTotalSteps; i++) {

        var stepLeft = 100 / (progressTotalSteps-1) * i;
        var newStep = $('<div class="step step-'+i+'"></div>');
        newStep.css('left',stepLeft+'%');
        $('body > .progress .line').append(newStep);
    
    }
}

function increaseProgess() {

    if(progressCurrentSteps < (progressTotalSteps-1)) {

        progressCurrentSteps++;
        var newProgressWidth = 100 / (progressTotalSteps-1) * progressCurrentSteps;
        console.log('Progress', progressCurrentSteps);
        $('body > .progress .line > div.blueline').css('width',newProgressWidth+'%');
    
        setTimeout(function(){ 
            $('.step.step-'+progressCurrentSteps).addClass('active');
        }, 1100);
    }

}

$(window).bind('mousewheel DOMMouseScroll', function(event){
    $('html,body').stop(true);
});

$(window).scroll(function() {
    
    calculateLogoPosition(); 

});

var scrollPosition = 0;
var logoPosition = 0;
var logoStartPosition = 0;
var visualPosition = 0;

function calculateLogoPosition() {
    
    // get logo position
    scrollPosition = $(window).scrollTop();
    logoStartPosition = ($(window).height() / 2) - ( $('section.side h1').height() / 2 );

    if( $(window).width() > 950 ) {
        if( scrollPosition > (logoStartPosition - 60) ) {
            if( !$('body').hasClass('fixedLogo') ) {
                $('body').addClass('fixedLogo');
                var newLogoPos = logoStartPosition - 60;
                $('section.side .inner').css('top', '-'+newLogoPos+'px');
            } 
        } else {
            if( $('body').hasClass('fixedLogo') ) {
                $('body').removeClass('fixedLogo');
                $('section.side .inner').removeAttr('style');
            }
        } 
    } else if( $(window).width() > 600 ) {

        if( scrollPosition > 162 ) {
            if( !$('body').hasClass('fixedLogo') ) $('body').addClass('fixedLogo');
        } else {
            if( $('body').hasClass('fixedLogo') ) $('body').removeClass('fixedLogo');
        } 

    } else {

        if( scrollPosition > 135 ) {
            if( !$('body').hasClass('fixedLogo') ) $('body').addClass('fixedLogo');
        } else {
            if( $('body').hasClass('fixedLogo') ) $('body').removeClass('fixedLogo');
        } 

    }

    
}

function showLastPageWithPdfDownload() {

    $('.container.thanks').addClass('show');

    var targetPosition = $('.container.thanks').offset().top;
    $('html,body').stop(true).animate({scrollTop:targetPosition}, 1000);

    $('.container.thanks button.download').click(function(e){
        downloadPdf();
    });
    

}